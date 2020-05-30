package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.PrescricaoProcedimentoService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.PrescricaoProcedimento}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoProcedimentoResource.class);

    private static final String ENTITY_NAME = "prescricaoPrescricaoProcedimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoProcedimentoService prescricaoProcedimentoService;

    public PrescricaoProcedimentoResource(PrescricaoProcedimentoService prescricaoProcedimentoService) {
        this.prescricaoProcedimentoService = prescricaoProcedimentoService;
    }

    /**
     * {@code POST  /prescricao-procedimentos} : Create a new prescricaoProcedimento.
     *
     * @param prescricaoProcedimentoDTO the prescricaoProcedimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoProcedimentoDTO, or with status {@code 400 (Bad Request)} if the prescricaoProcedimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricao-procedimentos")
    public ResponseEntity<PrescricaoProcedimentoDTO> createPrescricaoProcedimento(@Valid @RequestBody PrescricaoProcedimentoDTO prescricaoProcedimentoDTO) throws URISyntaxException {
        log.debug("REST request to save PrescricaoProcedimento : {}", prescricaoProcedimentoDTO);
        if (prescricaoProcedimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescricaoProcedimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoProcedimentoDTO result = prescricaoProcedimentoService.save(prescricaoProcedimentoDTO);
        return ResponseEntity.created(new URI("/api/prescricao-procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricao-procedimentos} : Updates an existing prescricaoProcedimento.
     *
     * @param prescricaoProcedimentoDTO the prescricaoProcedimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoProcedimentoDTO,
     * or with status {@code 400 (Bad Request)} if the prescricaoProcedimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoProcedimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricao-procedimentos")
    public ResponseEntity<PrescricaoProcedimentoDTO> updatePrescricaoProcedimento(@Valid @RequestBody PrescricaoProcedimentoDTO prescricaoProcedimentoDTO) throws URISyntaxException {
        log.debug("REST request to update PrescricaoProcedimento : {}", prescricaoProcedimentoDTO);
        if (prescricaoProcedimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoProcedimentoDTO result = prescricaoProcedimentoService.save(prescricaoProcedimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoProcedimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricao-procedimentos} : get all the prescricaoProcedimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaoProcedimentos in body.
     */
    @GetMapping("/prescricao-procedimentos")
    public ResponseEntity<List<PrescricaoProcedimentoDTO>> getAllPrescricaoProcedimentos(Pageable pageable) {
        log.debug("REST request to get a page of PrescricaoProcedimentos");
        Page<PrescricaoProcedimentoDTO> page = prescricaoProcedimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescricao-procedimentos/:id} : get the "id" prescricaoProcedimento.
     *
     * @param id the id of the prescricaoProcedimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoProcedimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricao-procedimentos/{id}")
    public ResponseEntity<PrescricaoProcedimentoDTO> getPrescricaoProcedimento(@PathVariable Long id) {
        log.debug("REST request to get PrescricaoProcedimento : {}", id);
        Optional<PrescricaoProcedimentoDTO> prescricaoProcedimentoDTO = prescricaoProcedimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescricaoProcedimentoDTO);
    }

    /**
     * {@code DELETE  /prescricao-procedimentos/:id} : delete the "id" prescricaoProcedimento.
     *
     * @param id the id of the prescricaoProcedimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricao-procedimentos/{id}")
    public ResponseEntity<Void> deletePrescricaoProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete PrescricaoProcedimento : {}", id);
        prescricaoProcedimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricao-procedimentos?query=:query} : search for the prescricaoProcedimento corresponding
     * to the query.
     *
     * @param query the query of the prescricaoProcedimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricao-procedimentos")
    public ResponseEntity<List<PrescricaoProcedimentoDTO>> searchPrescricaoProcedimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrescricaoProcedimentos for query {}", query);
        Page<PrescricaoProcedimentoDTO> page = prescricaoProcedimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
