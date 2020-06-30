package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.UnidadeDoseService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.UnidadeDoseDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.UnidadeDose}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeDoseResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeDoseResource.class);

    private static final String ENTITY_NAME = "prescricaoUnidadeDose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeDoseService unidadeDoseService;

    public UnidadeDoseResource(UnidadeDoseService unidadeDoseService) {
        this.unidadeDoseService = unidadeDoseService;
    }

    /**
     * {@code POST  /unidade-doses} : Create a new unidadeDose.
     *
     * @param unidadeDoseDTO the unidadeDoseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeDoseDTO, or with status {@code 400 (Bad Request)} if the unidadeDose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-doses")
    public ResponseEntity<UnidadeDoseDTO> createUnidadeDose(@Valid @RequestBody UnidadeDoseDTO unidadeDoseDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadeDose : {}", unidadeDoseDTO);
        if (unidadeDoseDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadeDose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeDoseDTO result = unidadeDoseService.save(unidadeDoseDTO);
        return ResponseEntity.created(new URI("/api/unidade-doses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-doses} : Updates an existing unidadeDose.
     *
     * @param unidadeDoseDTO the unidadeDoseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeDoseDTO,
     * or with status {@code 400 (Bad Request)} if the unidadeDoseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeDoseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-doses")
    public ResponseEntity<UnidadeDoseDTO> updateUnidadeDose(@Valid @RequestBody UnidadeDoseDTO unidadeDoseDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadeDose : {}", unidadeDoseDTO);
        if (unidadeDoseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeDoseDTO result = unidadeDoseService.save(unidadeDoseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeDoseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-doses} : get all the unidadeDoses.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeDoses in body.
     */
    @GetMapping("/unidade-doses")
    public ResponseEntity<List<UnidadeDoseDTO>> getAllUnidadeDoses(Pageable pageable) {
        log.debug("REST request to get a page of UnidadeDoses");
        Page<UnidadeDoseDTO> page = unidadeDoseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidade-doses/:id} : get the "id" unidadeDose.
     *
     * @param id the id of the unidadeDoseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeDoseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-doses/{id}")
    public ResponseEntity<UnidadeDoseDTO> getUnidadeDose(@PathVariable Long id) {
        log.debug("REST request to get UnidadeDose : {}", id);
        Optional<UnidadeDoseDTO> unidadeDoseDTO = unidadeDoseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeDoseDTO);
    }

    /**
     * {@code DELETE  /unidade-doses/:id} : delete the "id" unidadeDose.
     *
     * @param id the id of the unidadeDoseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-doses/{id}")
    public ResponseEntity<Void> deleteUnidadeDose(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeDose : {}", id);
        unidadeDoseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-doses?query=:query} : search for the unidadeDose corresponding
     * to the query.
     *
     * @param query the query of the unidadeDose search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-doses")
    public ResponseEntity<List<UnidadeDoseDTO>> searchUnidadeDoses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeDoses for query {}", query);
        Page<UnidadeDoseDTO> page = unidadeDoseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
