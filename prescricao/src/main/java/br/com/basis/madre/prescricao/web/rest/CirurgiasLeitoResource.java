package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.CirurgiasLeitoService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.CirurgiasLeitoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.CirurgiasLeito}.
 */
@RestController
@RequestMapping("/api")
public class CirurgiasLeitoResource {

    private final Logger log = LoggerFactory.getLogger(CirurgiasLeitoResource.class);

    private static final String ENTITY_NAME = "prescricaoCirurgiasLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CirurgiasLeitoService cirurgiasLeitoService;

    public CirurgiasLeitoResource(CirurgiasLeitoService cirurgiasLeitoService) {
        this.cirurgiasLeitoService = cirurgiasLeitoService;
    }

    /**
     * {@code POST  /cirurgias-leitos} : Create a new cirurgiasLeito.
     *
     * @param cirurgiasLeitoDTO the cirurgiasLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cirurgiasLeitoDTO, or with status {@code 400 (Bad Request)} if the cirurgiasLeito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cirurgias-leitos")
    public ResponseEntity<CirurgiasLeitoDTO> createCirurgiasLeito(@Valid @RequestBody CirurgiasLeitoDTO cirurgiasLeitoDTO) throws URISyntaxException {
        log.debug("REST request to save CirurgiasLeito : {}", cirurgiasLeitoDTO);
        if (cirurgiasLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cirurgiasLeito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CirurgiasLeitoDTO result = cirurgiasLeitoService.save(cirurgiasLeitoDTO);
        return ResponseEntity.created(new URI("/api/cirurgias-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cirurgias-leitos} : Updates an existing cirurgiasLeito.
     *
     * @param cirurgiasLeitoDTO the cirurgiasLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cirurgiasLeitoDTO,
     * or with status {@code 400 (Bad Request)} if the cirurgiasLeitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cirurgiasLeitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cirurgias-leitos")
    public ResponseEntity<CirurgiasLeitoDTO> updateCirurgiasLeito(@Valid @RequestBody CirurgiasLeitoDTO cirurgiasLeitoDTO) throws URISyntaxException {
        log.debug("REST request to update CirurgiasLeito : {}", cirurgiasLeitoDTO);
        if (cirurgiasLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CirurgiasLeitoDTO result = cirurgiasLeitoService.save(cirurgiasLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cirurgiasLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cirurgias-leitos} : get all the cirurgiasLeitos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cirurgiasLeitos in body.
     */
    @GetMapping("/cirurgias-leitos")
    public ResponseEntity<List<CirurgiasLeitoDTO>> getAllCirurgiasLeitos(Pageable pageable) {
        log.debug("REST request to get a page of CirurgiasLeitos");
        Page<CirurgiasLeitoDTO> page = cirurgiasLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cirurgias-leitos/:id} : get the "id" cirurgiasLeito.
     *
     * @param id the id of the cirurgiasLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cirurgiasLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cirurgias-leitos/{id}")
    public ResponseEntity<CirurgiasLeitoDTO> getCirurgiasLeito(@PathVariable Long id) {
        log.debug("REST request to get CirurgiasLeito : {}", id);
        Optional<CirurgiasLeitoDTO> cirurgiasLeitoDTO = cirurgiasLeitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cirurgiasLeitoDTO);
    }

    /**
     * {@code DELETE  /cirurgias-leitos/:id} : delete the "id" cirurgiasLeito.
     *
     * @param id the id of the cirurgiasLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cirurgias-leitos/{id}")
    public ResponseEntity<Void> deleteCirurgiasLeito(@PathVariable Long id) {
        log.debug("REST request to delete CirurgiasLeito : {}", id);
        cirurgiasLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cirurgias-leitos?query=:query} : search for the cirurgiasLeito corresponding
     * to the query.
     *
     * @param query the query of the cirurgiasLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cirurgias-leitos")
    public ResponseEntity<List<CirurgiasLeitoDTO>> searchCirurgiasLeitos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CirurgiasLeitos for query {}", query);
        Page<CirurgiasLeitoDTO> page = cirurgiasLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
