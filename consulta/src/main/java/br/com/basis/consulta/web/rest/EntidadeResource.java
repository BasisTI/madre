package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.service.EntidadeService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.consulta.service.dto.EntidadeDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.consulta.domain.Entidade}.
 */
@RestController
@RequestMapping("/api")
public class EntidadeResource {

    private final Logger log = LoggerFactory.getLogger(EntidadeResource.class);

    private static final String ENTITY_NAME = "madreconsultaEntidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadeService entidadeService;

    public EntidadeResource(EntidadeService entidadeService) {
        this.entidadeService = entidadeService;
    }

    /**
     * {@code POST  /entidades} : Create a new entidade.
     *
     * @param entidadeDTO the entidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidadeDTO, or with status {@code 400 (Bad Request)} if the entidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidades")
    public ResponseEntity<EntidadeDTO> createEntidade(@RequestBody EntidadeDTO entidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Entidade : {}", entidadeDTO);
        if (entidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new entidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntidadeDTO result = entidadeService.save(entidadeDTO);
        return ResponseEntity.created(new URI("/api/entidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidades} : Updates an existing entidade.
     *
     * @param entidadeDTO the entidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidadeDTO,
     * or with status {@code 400 (Bad Request)} if the entidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidades")
    public ResponseEntity<EntidadeDTO> updateEntidade(@RequestBody EntidadeDTO entidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Entidade : {}", entidadeDTO);
        if (entidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntidadeDTO result = entidadeService.save(entidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entidades} : get all the entidades.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidades in body.
     */
    @GetMapping("/entidades")
    public ResponseEntity<List<EntidadeDTO>> getAllEntidades(Pageable pageable) {
        log.debug("REST request to get a page of Entidades");
        Page<EntidadeDTO> page = entidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entidades/:id} : get the "id" entidade.
     *
     * @param id the id of the entidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidades/{id}")
    public ResponseEntity<EntidadeDTO> getEntidade(@PathVariable Long id) {
        log.debug("REST request to get Entidade : {}", id);
        Optional<EntidadeDTO> entidadeDTO = entidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidadeDTO);
    }

    /**
     * {@code DELETE  /entidades/:id} : delete the "id" entidade.
     *
     * @param id the id of the entidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidades/{id}")
    public ResponseEntity<Void> deleteEntidade(@PathVariable Long id) {
        log.debug("REST request to delete Entidade : {}", id);
        entidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/entidades?query=:query} : search for the entidade corresponding
     * to the query.
     *
     * @param query the query of the entidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/entidades")
    public ResponseEntity<List<EntidadeDTO>> searchEntidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Entidades for query {}", query);
        Page<EntidadeDTO> page = entidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
