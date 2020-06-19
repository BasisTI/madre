package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.SazonalidadeService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.SazonalidadeDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.Sazonalidade}.
 */
@RestController
@RequestMapping("/api")
public class SazonalidadeResource {

    private final Logger log = LoggerFactory.getLogger(SazonalidadeResource.class);

    private static final String ENTITY_NAME = "madresuprimentosSazonalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SazonalidadeService sazonalidadeService;

    public SazonalidadeResource(SazonalidadeService sazonalidadeService) {
        this.sazonalidadeService = sazonalidadeService;
    }

    /**
     * {@code POST  /sazonalidades} : Create a new sazonalidade.
     *
     * @param sazonalidadeDTO the sazonalidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sazonalidadeDTO, or with status {@code 400 (Bad Request)} if the sazonalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sazonalidades")
    public ResponseEntity<SazonalidadeDTO> createSazonalidade(@Valid @RequestBody SazonalidadeDTO sazonalidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Sazonalidade : {}", sazonalidadeDTO);
        if (sazonalidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sazonalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SazonalidadeDTO result = sazonalidadeService.save(sazonalidadeDTO);
        return ResponseEntity.created(new URI("/api/sazonalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sazonalidades} : Updates an existing sazonalidade.
     *
     * @param sazonalidadeDTO the sazonalidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sazonalidadeDTO,
     * or with status {@code 400 (Bad Request)} if the sazonalidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sazonalidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sazonalidades")
    public ResponseEntity<SazonalidadeDTO> updateSazonalidade(@Valid @RequestBody SazonalidadeDTO sazonalidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Sazonalidade : {}", sazonalidadeDTO);
        if (sazonalidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SazonalidadeDTO result = sazonalidadeService.save(sazonalidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sazonalidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sazonalidades} : get all the sazonalidades.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sazonalidades in body.
     */
    @GetMapping("/sazonalidades")
    public ResponseEntity<List<SazonalidadeDTO>> getAllSazonalidades(Pageable pageable) {
        log.debug("REST request to get a page of Sazonalidades");
        Page<SazonalidadeDTO> page = sazonalidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sazonalidades/:id} : get the "id" sazonalidade.
     *
     * @param id the id of the sazonalidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sazonalidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sazonalidades/{id}")
    public ResponseEntity<SazonalidadeDTO> getSazonalidade(@PathVariable Long id) {
        log.debug("REST request to get Sazonalidade : {}", id);
        Optional<SazonalidadeDTO> sazonalidadeDTO = sazonalidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sazonalidadeDTO);
    }

    /**
     * {@code DELETE  /sazonalidades/:id} : delete the "id" sazonalidade.
     *
     * @param id the id of the sazonalidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sazonalidades/{id}")
    public ResponseEntity<Void> deleteSazonalidade(@PathVariable Long id) {
        log.debug("REST request to delete Sazonalidade : {}", id);
        sazonalidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sazonalidades?query=:query} : search for the sazonalidade corresponding
     * to the query.
     *
     * @param query the query of the sazonalidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sazonalidades")
    public ResponseEntity<List<SazonalidadeDTO>> searchSazonalidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sazonalidades for query {}", query);
        Page<SazonalidadeDTO> page = sazonalidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
