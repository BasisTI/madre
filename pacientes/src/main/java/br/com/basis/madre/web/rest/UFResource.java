package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.UFService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.UFDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.UF}.
 */
@RestController
@RequestMapping("/api")
public class UFResource {

    private final Logger log = LoggerFactory.getLogger(UFResource.class);

    private static final String ENTITY_NAME = "pacientesUf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UFService uFService;

    public UFResource(UFService uFService) {
        this.uFService = uFService;
    }

    /**
     * {@code POST  /ufs} : Create a new uF.
     *
     * @param uFDTO the uFDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uFDTO, or with status {@code 400 (Bad Request)} if the uF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ufs")
    public ResponseEntity<UFDTO> createUF(@Valid @RequestBody UFDTO uFDTO) throws URISyntaxException {
        log.debug("REST request to save UF : {}", uFDTO);
        if (uFDTO.getId() != null) {
            throw new BadRequestAlertException("A new uF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UFDTO result = uFService.save(uFDTO);
        return ResponseEntity.created(new URI("/api/ufs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ufs} : Updates an existing uF.
     *
     * @param uFDTO the uFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uFDTO,
     * or with status {@code 400 (Bad Request)} if the uFDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uFDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ufs")
    public ResponseEntity<UFDTO> updateUF(@Valid @RequestBody UFDTO uFDTO) throws URISyntaxException {
        log.debug("REST request to update UF : {}", uFDTO);
        if (uFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UFDTO result = uFService.save(uFDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uFDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ufs} : get all the uFS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uFS in body.
     */
    @GetMapping("/ufs")
    public ResponseEntity<List<UFDTO>> getAllUFS(Pageable pageable) {
        log.debug("REST request to get a page of UFS");
        Page<UFDTO> page = uFService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ufs/:id} : get the "id" uF.
     *
     * @param id the id of the uFDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uFDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ufs/{id}")
    public ResponseEntity<UFDTO> getUF(@PathVariable Long id) {
        log.debug("REST request to get UF : {}", id);
        Optional<UFDTO> uFDTO = uFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uFDTO);
    }

    /**
     * {@code DELETE  /ufs/:id} : delete the "id" uF.
     *
     * @param id the id of the uFDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ufs/{id}")
    public ResponseEntity<Void> deleteUF(@PathVariable Long id) {
        log.debug("REST request to delete UF : {}", id);
        uFService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ufs?query=:query} : search for the uF corresponding
     * to the query.
     *
     * @param query the query of the uF search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ufs")
    public ResponseEntity<List<UFDTO>> searchUFS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UFS for query {}", query);
        Page<UFDTO> page = uFService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
