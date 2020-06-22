package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.CentroDeAtividadeService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.CentroDeAtividadeDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.CentroDeAtividade}.
 */
@RestController
@RequestMapping("/api")
public class CentroDeAtividadeResource {

    private final Logger log = LoggerFactory.getLogger(CentroDeAtividadeResource.class);

    private static final String ENTITY_NAME = "madresuprimentosCentroDeAtividade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroDeAtividadeService centroDeAtividadeService;

    public CentroDeAtividadeResource(CentroDeAtividadeService centroDeAtividadeService) {
        this.centroDeAtividadeService = centroDeAtividadeService;
    }

    /**
     * {@code POST  /centro-de-atividades} : Create a new centroDeAtividade.
     *
     * @param centroDeAtividadeDTO the centroDeAtividadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroDeAtividadeDTO, or with status {@code 400 (Bad Request)} if the centroDeAtividade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centro-de-atividades")
    public ResponseEntity<CentroDeAtividadeDTO> createCentroDeAtividade(@Valid @RequestBody CentroDeAtividadeDTO centroDeAtividadeDTO) throws URISyntaxException {
        log.debug("REST request to save CentroDeAtividade : {}", centroDeAtividadeDTO);
        if (centroDeAtividadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new centroDeAtividade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentroDeAtividadeDTO result = centroDeAtividadeService.save(centroDeAtividadeDTO);
        return ResponseEntity.created(new URI("/api/centro-de-atividades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centro-de-atividades} : Updates an existing centroDeAtividade.
     *
     * @param centroDeAtividadeDTO the centroDeAtividadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroDeAtividadeDTO,
     * or with status {@code 400 (Bad Request)} if the centroDeAtividadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroDeAtividadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centro-de-atividades")
    public ResponseEntity<CentroDeAtividadeDTO> updateCentroDeAtividade(@Valid @RequestBody CentroDeAtividadeDTO centroDeAtividadeDTO) throws URISyntaxException {
        log.debug("REST request to update CentroDeAtividade : {}", centroDeAtividadeDTO);
        if (centroDeAtividadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentroDeAtividadeDTO result = centroDeAtividadeService.save(centroDeAtividadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centroDeAtividadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /centro-de-atividades} : get all the centroDeAtividades.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroDeAtividades in body.
     */
    @GetMapping("/centro-de-atividades")
    public ResponseEntity<List<CentroDeAtividadeDTO>> getAllCentroDeAtividades(Pageable pageable) {
        log.debug("REST request to get a page of CentroDeAtividades");
        Page<CentroDeAtividadeDTO> page = centroDeAtividadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /centro-de-atividades/:id} : get the "id" centroDeAtividade.
     *
     * @param id the id of the centroDeAtividadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroDeAtividadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centro-de-atividades/{id}")
    public ResponseEntity<CentroDeAtividadeDTO> getCentroDeAtividade(@PathVariable Long id) {
        log.debug("REST request to get CentroDeAtividade : {}", id);
        Optional<CentroDeAtividadeDTO> centroDeAtividadeDTO = centroDeAtividadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroDeAtividadeDTO);
    }

    /**
     * {@code DELETE  /centro-de-atividades/:id} : delete the "id" centroDeAtividade.
     *
     * @param id the id of the centroDeAtividadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centro-de-atividades/{id}")
    public ResponseEntity<Void> deleteCentroDeAtividade(@PathVariable Long id) {
        log.debug("REST request to delete CentroDeAtividade : {}", id);
        centroDeAtividadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/centro-de-atividades?query=:query} : search for the centroDeAtividade corresponding
     * to the query.
     *
     * @param query the query of the centroDeAtividade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/centro-de-atividades")
    public ResponseEntity<List<CentroDeAtividadeDTO>> searchCentroDeAtividades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CentroDeAtividades for query {}", query);
        Page<CentroDeAtividadeDTO> page = centroDeAtividadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
