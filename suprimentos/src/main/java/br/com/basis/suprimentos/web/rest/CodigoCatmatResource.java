package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.CodigoCatmatService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.CodigoCatmatDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.CodigoCatmat}.
 */
@RestController
@RequestMapping("/api")
public class CodigoCatmatResource {

    private final Logger log = LoggerFactory.getLogger(CodigoCatmatResource.class);

    private static final String ENTITY_NAME = "madresuprimentosCodigoCatmat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodigoCatmatService codigoCatmatService;

    public CodigoCatmatResource(CodigoCatmatService codigoCatmatService) {
        this.codigoCatmatService = codigoCatmatService;
    }

    /**
     * {@code POST  /codigo-catmats} : Create a new codigoCatmat.
     *
     * @param codigoCatmatDTO the codigoCatmatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codigoCatmatDTO, or with status {@code 400 (Bad Request)} if the codigoCatmat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/codigo-catmats")
    public ResponseEntity<CodigoCatmatDTO> createCodigoCatmat(@Valid @RequestBody CodigoCatmatDTO codigoCatmatDTO) throws URISyntaxException {
        log.debug("REST request to save CodigoCatmat : {}", codigoCatmatDTO);
        if (codigoCatmatDTO.getId() != null) {
            throw new BadRequestAlertException("A new codigoCatmat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodigoCatmatDTO result = codigoCatmatService.save(codigoCatmatDTO);
        return ResponseEntity.created(new URI("/api/codigo-catmats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /codigo-catmats} : Updates an existing codigoCatmat.
     *
     * @param codigoCatmatDTO the codigoCatmatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codigoCatmatDTO,
     * or with status {@code 400 (Bad Request)} if the codigoCatmatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codigoCatmatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/codigo-catmats")
    public ResponseEntity<CodigoCatmatDTO> updateCodigoCatmat(@Valid @RequestBody CodigoCatmatDTO codigoCatmatDTO) throws URISyntaxException {
        log.debug("REST request to update CodigoCatmat : {}", codigoCatmatDTO);
        if (codigoCatmatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodigoCatmatDTO result = codigoCatmatService.save(codigoCatmatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codigoCatmatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /codigo-catmats} : get all the codigoCatmats.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codigoCatmats in body.
     */
    @GetMapping("/codigo-catmats")
    public ResponseEntity<List<CodigoCatmatDTO>> getAllCodigoCatmats(Pageable pageable) {
        log.debug("REST request to get a page of CodigoCatmats");
        Page<CodigoCatmatDTO> page = codigoCatmatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /codigo-catmats/:id} : get the "id" codigoCatmat.
     *
     * @param id the id of the codigoCatmatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codigoCatmatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/codigo-catmats/{id}")
    public ResponseEntity<CodigoCatmatDTO> getCodigoCatmat(@PathVariable Long id) {
        log.debug("REST request to get CodigoCatmat : {}", id);
        Optional<CodigoCatmatDTO> codigoCatmatDTO = codigoCatmatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codigoCatmatDTO);
    }

    /**
     * {@code DELETE  /codigo-catmats/:id} : delete the "id" codigoCatmat.
     *
     * @param id the id of the codigoCatmatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/codigo-catmats/{id}")
    public ResponseEntity<Void> deleteCodigoCatmat(@PathVariable Long id) {
        log.debug("REST request to delete CodigoCatmat : {}", id);
        codigoCatmatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/codigo-catmats?query=:query} : search for the codigoCatmat corresponding
     * to the query.
     *
     * @param query the query of the codigoCatmat search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/codigo-catmats")
    public ResponseEntity<List<CodigoCatmatDTO>> searchCodigoCatmats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CodigoCatmats for query {}", query);
        Page<CodigoCatmatDTO> page = codigoCatmatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
