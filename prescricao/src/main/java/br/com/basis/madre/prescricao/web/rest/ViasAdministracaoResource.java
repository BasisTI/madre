package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.ViasAdministracaoService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.ViasAdministracaoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ViasAdministracao}.
 */
@RestController
@RequestMapping("/api")
public class ViasAdministracaoResource {

    private final Logger log = LoggerFactory.getLogger(ViasAdministracaoResource.class);

    private static final String ENTITY_NAME = "prescricaoViasAdministracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViasAdministracaoService viasAdministracaoService;

    public ViasAdministracaoResource(ViasAdministracaoService viasAdministracaoService) {
        this.viasAdministracaoService = viasAdministracaoService;
    }

    /**
     * {@code POST  /vias-administracaos} : Create a new viasAdministracao.
     *
     * @param viasAdministracaoDTO the viasAdministracaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viasAdministracaoDTO, or with status {@code 400 (Bad Request)} if the viasAdministracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vias-administracaos")
    public ResponseEntity<ViasAdministracaoDTO> createViasAdministracao(@Valid @RequestBody ViasAdministracaoDTO viasAdministracaoDTO) throws URISyntaxException {
        log.debug("REST request to save ViasAdministracao : {}", viasAdministracaoDTO);
        if (viasAdministracaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new viasAdministracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViasAdministracaoDTO result = viasAdministracaoService.save(viasAdministracaoDTO);
        return ResponseEntity.created(new URI("/api/vias-administracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vias-administracaos} : Updates an existing viasAdministracao.
     *
     * @param viasAdministracaoDTO the viasAdministracaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viasAdministracaoDTO,
     * or with status {@code 400 (Bad Request)} if the viasAdministracaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viasAdministracaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vias-administracaos")
    public ResponseEntity<ViasAdministracaoDTO> updateViasAdministracao(@Valid @RequestBody ViasAdministracaoDTO viasAdministracaoDTO) throws URISyntaxException {
        log.debug("REST request to update ViasAdministracao : {}", viasAdministracaoDTO);
        if (viasAdministracaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ViasAdministracaoDTO result = viasAdministracaoService.save(viasAdministracaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, viasAdministracaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vias-administracaos} : get all the viasAdministracaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viasAdministracaos in body.
     */
    @GetMapping("/vias-administracaos")
    public ResponseEntity<List<ViasAdministracaoDTO>> getAllViasAdministracaos(Pageable pageable) {
        log.debug("REST request to get a page of ViasAdministracaos");
        Page<ViasAdministracaoDTO> page = viasAdministracaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vias-administracaos/:id} : get the "id" viasAdministracao.
     *
     * @param id the id of the viasAdministracaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viasAdministracaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vias-administracaos/{id}")
    public ResponseEntity<ViasAdministracaoDTO> getViasAdministracao(@PathVariable Long id) {
        log.debug("REST request to get ViasAdministracao : {}", id);
        Optional<ViasAdministracaoDTO> viasAdministracaoDTO = viasAdministracaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viasAdministracaoDTO);
    }

    /**
     * {@code DELETE  /vias-administracaos/:id} : delete the "id" viasAdministracao.
     *
     * @param id the id of the viasAdministracaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vias-administracaos/{id}")
    public ResponseEntity<Void> deleteViasAdministracao(@PathVariable Long id) {
        log.debug("REST request to delete ViasAdministracao : {}", id);
        viasAdministracaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/vias-administracaos?query=:query} : search for the viasAdministracao corresponding
     * to the query.
     *
     * @param query the query of the viasAdministracao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/vias-administracaos")
    public ResponseEntity<List<ViasAdministracaoDTO>> searchViasAdministracaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ViasAdministracaos for query {}", query);
        Page<ViasAdministracaoDTO> page = viasAdministracaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
