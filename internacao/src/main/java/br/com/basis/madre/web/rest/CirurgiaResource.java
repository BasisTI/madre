package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CirurgiaService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.CirurgiaDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Cirurgia}.
 */
@RestController
@RequestMapping("/api")
public class CirurgiaResource {

    private final Logger log = LoggerFactory.getLogger(CirurgiaResource.class);

    private static final String ENTITY_NAME = "internacaoCirurgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CirurgiaService cirurgiaService;

    public CirurgiaResource(CirurgiaService cirurgiaService) {
        this.cirurgiaService = cirurgiaService;
    }

    /**
     * {@code POST  /cirurgias} : Create a new cirurgia.
     *
     * @param cirurgiaDTO the cirurgiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cirurgiaDTO, or with status {@code 400 (Bad Request)} if the cirurgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cirurgias")
    public ResponseEntity<CirurgiaDTO> createCirurgia(@RequestBody CirurgiaDTO cirurgiaDTO) throws URISyntaxException {
        log.debug("REST request to save Cirurgia : {}", cirurgiaDTO);
        if (cirurgiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cirurgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CirurgiaDTO result = cirurgiaService.save(cirurgiaDTO);
        return ResponseEntity.created(new URI("/api/cirurgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cirurgias} : Updates an existing cirurgia.
     *
     * @param cirurgiaDTO the cirurgiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cirurgiaDTO,
     * or with status {@code 400 (Bad Request)} if the cirurgiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cirurgiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cirurgias")
    public ResponseEntity<CirurgiaDTO> updateCirurgia(@RequestBody CirurgiaDTO cirurgiaDTO) throws URISyntaxException {
        log.debug("REST request to update Cirurgia : {}", cirurgiaDTO);
        if (cirurgiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CirurgiaDTO result = cirurgiaService.save(cirurgiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cirurgiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cirurgias} : get all the cirurgias.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cirurgias in body.
     */
    @GetMapping("/cirurgias")
    public ResponseEntity<List<CirurgiaDTO>> getAllCirurgias(Pageable pageable) {
        log.debug("REST request to get a page of Cirurgias");
        Page<CirurgiaDTO> page = cirurgiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cirurgias/:id} : get the "id" cirurgia.
     *
     * @param id the id of the cirurgiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cirurgiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cirurgias/{id}")
    public ResponseEntity<CirurgiaDTO> getCirurgia(@PathVariable Long id) {
        log.debug("REST request to get Cirurgia : {}", id);
        Optional<CirurgiaDTO> cirurgiaDTO = cirurgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cirurgiaDTO);
    }

    /**
     * {@code DELETE  /cirurgias/:id} : delete the "id" cirurgia.
     *
     * @param id the id of the cirurgiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cirurgias/{id}")
    public ResponseEntity<Void> deleteCirurgia(@PathVariable Long id) {
        log.debug("REST request to delete Cirurgia : {}", id);
        cirurgiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cirurgias?query=:query} : search for the cirurgia corresponding
     * to the query.
     *
     * @param query the query of the cirurgia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cirurgias")
    public ResponseEntity<List<CirurgiaDTO>> searchCirurgias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cirurgias for query {}", query);
        Page<CirurgiaDTO> page = cirurgiaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
