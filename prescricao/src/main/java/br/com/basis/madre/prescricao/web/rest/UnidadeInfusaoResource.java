package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.UnidadeInfusaoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.UnidadeInfusaoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.UnidadeInfusao}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeInfusaoResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeInfusaoResource.class);

    private static final String ENTITY_NAME = "prescricaoUnidadeInfusao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeInfusaoService unidadeInfusaoService;

    public UnidadeInfusaoResource(UnidadeInfusaoService unidadeInfusaoService) {
        this.unidadeInfusaoService = unidadeInfusaoService;
    }

    /**
     * {@code POST  /unidade-infusaos} : Create a new unidadeInfusao.
     *
     * @param unidadeInfusaoDTO the unidadeInfusaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeInfusaoDTO, or with status {@code 400 (Bad Request)} if the unidadeInfusao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-infusaos")
    public ResponseEntity<UnidadeInfusaoDTO> createUnidadeInfusao(@Valid @RequestBody UnidadeInfusaoDTO unidadeInfusaoDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadeInfusao : {}", unidadeInfusaoDTO);
        if (unidadeInfusaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadeInfusao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeInfusaoDTO result = unidadeInfusaoService.save(unidadeInfusaoDTO);
        return ResponseEntity.created(new URI("/api/unidade-infusaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-infusaos} : Updates an existing unidadeInfusao.
     *
     * @param unidadeInfusaoDTO the unidadeInfusaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeInfusaoDTO,
     * or with status {@code 400 (Bad Request)} if the unidadeInfusaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeInfusaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-infusaos")
    public ResponseEntity<UnidadeInfusaoDTO> updateUnidadeInfusao(@Valid @RequestBody UnidadeInfusaoDTO unidadeInfusaoDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadeInfusao : {}", unidadeInfusaoDTO);
        if (unidadeInfusaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeInfusaoDTO result = unidadeInfusaoService.save(unidadeInfusaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeInfusaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-infusaos} : get all the unidadeInfusaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeInfusaos in body.
     */
    @GetMapping("/unidade-infusaos")
    public ResponseEntity<List<UnidadeInfusaoDTO>> getAllUnidadeInfusaos(Pageable pageable) {
        log.debug("REST request to get a page of UnidadeInfusaos");
        Page<UnidadeInfusaoDTO> page = unidadeInfusaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidade-infusaos/:id} : get the "id" unidadeInfusao.
     *
     * @param id the id of the unidadeInfusaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeInfusaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-infusaos/{id}")
    public ResponseEntity<UnidadeInfusaoDTO> getUnidadeInfusao(@PathVariable Long id) {
        log.debug("REST request to get UnidadeInfusao : {}", id);
        Optional<UnidadeInfusaoDTO> unidadeInfusaoDTO = unidadeInfusaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeInfusaoDTO);
    }

    /**
     * {@code DELETE  /unidade-infusaos/:id} : delete the "id" unidadeInfusao.
     *
     * @param id the id of the unidadeInfusaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-infusaos/{id}")
    public ResponseEntity<Void> deleteUnidadeInfusao(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeInfusao : {}", id);
        unidadeInfusaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-infusaos?query=:query} : search for the unidadeInfusao corresponding
     * to the query.
     *
     * @param query the query of the unidadeInfusao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-infusaos")
    public ResponseEntity<List<UnidadeInfusaoDTO>> searchUnidadeInfusaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeInfusaos for query {}", query);
        Page<UnidadeInfusaoDTO> page = unidadeInfusaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
