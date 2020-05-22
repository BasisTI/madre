package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.OrteseProteseService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.OrteseProteseDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.OrteseProtese}.
 */
@RestController
@RequestMapping("/api")
public class OrteseProteseResource {

    private final Logger log = LoggerFactory.getLogger(OrteseProteseResource.class);

    private static final String ENTITY_NAME = "prescricaoOrteseProtese";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrteseProteseService orteseProteseService;

    public OrteseProteseResource(OrteseProteseService orteseProteseService) {
        this.orteseProteseService = orteseProteseService;
    }

    /**
     * {@code POST  /ortese-protese} : Create a new orteseProtese.
     *
     * @param orteseProteseDTO the orteseProteseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orteseProteseDTO, or with status {@code 400 (Bad Request)} if the orteseProtese has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ortese-protese")
    public ResponseEntity<OrteseProteseDTO> createOrteseProtese(@Valid @RequestBody OrteseProteseDTO orteseProteseDTO) throws URISyntaxException {
        log.debug("REST request to save OrteseProtese : {}", orteseProteseDTO);
        if (orteseProteseDTO.getId() != null) {
            throw new BadRequestAlertException("A new orteseProtese cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrteseProteseDTO result = orteseProteseService.save(orteseProteseDTO);
        return ResponseEntity.created(new URI("/api/ortese-protese/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ortese-protese} : Updates an existing orteseProtese.
     *
     * @param orteseProteseDTO the orteseProteseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orteseProteseDTO,
     * or with status {@code 400 (Bad Request)} if the orteseProteseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orteseProteseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ortese-protese")
    public ResponseEntity<OrteseProteseDTO> updateOrteseProtese(@Valid @RequestBody OrteseProteseDTO orteseProteseDTO) throws URISyntaxException {
        log.debug("REST request to update OrteseProtese : {}", orteseProteseDTO);
        if (orteseProteseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrteseProteseDTO result = orteseProteseService.save(orteseProteseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orteseProteseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ortese-protese} : get all the orteseProtese.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orteseProtese in body.
     */
    @GetMapping("/ortese-protese")
    public ResponseEntity<List<OrteseProteseDTO>> getAllOrteseProtese(Pageable pageable) {
        log.debug("REST request to get a page of OrteseProtese");
        Page<OrteseProteseDTO> page = orteseProteseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ortese-protese/:id} : get the "id" orteseProtese.
     *
     * @param id the id of the orteseProteseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orteseProteseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ortese-protese/{id}")
    public ResponseEntity<OrteseProteseDTO> getOrteseProtese(@PathVariable Long id) {
        log.debug("REST request to get OrteseProtese : {}", id);
        Optional<OrteseProteseDTO> orteseProteseDTO = orteseProteseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orteseProteseDTO);
    }

    /**
     * {@code DELETE  /ortese-protese/:id} : delete the "id" orteseProtese.
     *
     * @param id the id of the orteseProteseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ortese-protese/{id}")
    public ResponseEntity<Void> deleteOrteseProtese(@PathVariable Long id) {
        log.debug("REST request to delete OrteseProtese : {}", id);
        orteseProteseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ortese-protese?query=:query} : search for the orteseProtese corresponding
     * to the query.
     *
     * @param query the query of the orteseProtese search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ortese-protese")
    public ResponseEntity<List<OrteseProteseDTO>> searchOrteseProtese(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrteseProtese for query {}", query);
        Page<OrteseProteseDTO> page = orteseProteseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
