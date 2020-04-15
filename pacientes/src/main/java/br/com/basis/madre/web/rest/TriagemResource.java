package br.com.basis.madre.web.rest;


import br.com.basis.madre.service.TriagemService;
import br.com.basis.madre.service.dto.TriagemDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Triagem.
 */
@RestController
@RequestMapping("/api")
public class TriagemResource {

    private final Logger log = LoggerFactory.getLogger(TriagemResource.class);

    private static final String ENTITY_NAME = "pacientesTriagem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TriagemService triagemService;

    public TriagemResource(TriagemService triagemService) {
        this.triagemService = triagemService;
    }

    /**
     * POST  /triagems : Create a new triagem.
     *
     * @param triagemDTO the triagemDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new triagemDTO, or with status 400 (Bad Request) if the triagem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/triagems")
    @Timed
    public ResponseEntity<TriagemDTO> createTriagem(@Valid @RequestBody TriagemDTO triagemDTO) throws URISyntaxException {

        log.debug("REST request to save Triagem : {}", triagemDTO);
        if (triagemDTO.getId() != null) {
            throw new BadRequestAlertException("A new triagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TriagemDTO result = triagemService.save(triagemDTO);
        return ResponseEntity.created(new URI("/api/triagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName,false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /triagems : Updates an existing triagem.
     *
     * @param triagemDTO the triagemDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated triagemDTO,
     * or with status 400 (Bad Request) if the triagemDTO is not valid,
     * or with status 500 (Internal Server Error) if the triagemDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/triagems")
    @Timed
    public ResponseEntity<TriagemDTO> updateTriagem(@Valid @RequestBody TriagemDTO triagemDTO) throws URISyntaxException {
        log.debug("REST request to update Triagem : {}", triagemDTO);
        if (triagemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TriagemDTO result = triagemService.save(triagemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, triagemDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /triagems : get all the triagems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of triagems in body
     */
    @GetMapping("/triagems")
    @Timed
    public ResponseEntity<List<TriagemDTO>> getAllTriagems(Pageable pageable) {
        log.debug("REST request to get a page of Triagems");
        Page<TriagemDTO> page = triagemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /triagems/:id : get the "id" triagem.
     *
     * @param id the id of the triagemDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the triagemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/triagems/{id}")
    @Timed
    public ResponseEntity<TriagemDTO> getTriagem(@PathVariable Long id) {
        log.debug("REST request to get Triagem : {}", id);
        Optional<TriagemDTO> triagemDTO = triagemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(triagemDTO);
    }

    /**
     * DELETE  /triagems/:id : delete the "id" triagem.
     *
     * @param id the id of the triagemDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/triagems/{id}")
    @Timed
    public ResponseEntity<Void> deleteTriagem(@PathVariable Long id) {
        log.debug("REST request to delete Triagem : {}", id);
        triagemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/triagems?query=:query : search for the triagem corresponding
     * to the query.
     *
     * @param query the query of the triagem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/triagems")
    @Timed
    public ResponseEntity<List<TriagemDTO>> searchTriagems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Triagems for query {}", query);
        Page<TriagemDTO> page = triagemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
