package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.SolicitacaoComprasService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.SolicitacaoComprasDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.SolicitacaoCompras}.
 */
@RestController
@RequestMapping("/api")
public class SolicitacaoComprasResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoComprasResource.class);

    private static final String ENTITY_NAME = "madresuprimentosSolicitacaoCompras";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoComprasService solicitacaoComprasService;

    public SolicitacaoComprasResource(SolicitacaoComprasService solicitacaoComprasService) {
        this.solicitacaoComprasService = solicitacaoComprasService;
    }

    /**
     * {@code POST  /solicitacao-compras} : Create a new solicitacaoCompras.
     *
     * @param solicitacaoComprasDTO the solicitacaoComprasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitacaoComprasDTO, or with status {@code 400 (Bad Request)} if the solicitacaoCompras has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacao-compras")
    public ResponseEntity<SolicitacaoComprasDTO> createSolicitacaoCompras(@Valid @RequestBody SolicitacaoComprasDTO solicitacaoComprasDTO) throws URISyntaxException {
        log.debug("REST request to save SolicitacaoCompras : {}", solicitacaoComprasDTO);
        if (solicitacaoComprasDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitacaoCompras cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoComprasDTO result = solicitacaoComprasService.save(solicitacaoComprasDTO);
        return ResponseEntity.created(new URI("/api/solicitacao-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacao-compras} : Updates an existing solicitacaoCompras.
     *
     * @param solicitacaoComprasDTO the solicitacaoComprasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacaoComprasDTO,
     * or with status {@code 400 (Bad Request)} if the solicitacaoComprasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitacaoComprasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacao-compras")
    public ResponseEntity<SolicitacaoComprasDTO> updateSolicitacaoCompras(@Valid @RequestBody SolicitacaoComprasDTO solicitacaoComprasDTO) throws URISyntaxException {
        log.debug("REST request to update SolicitacaoCompras : {}", solicitacaoComprasDTO);
        if (solicitacaoComprasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitacaoComprasDTO result = solicitacaoComprasService.save(solicitacaoComprasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoComprasDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicitacao-compras} : get all the solicitacaoCompras.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitacaoCompras in body.
     */
    @GetMapping("/solicitacao-compras")
    public ResponseEntity<List<SolicitacaoComprasDTO>> getAllSolicitacaoCompras(Pageable pageable) {
        log.debug("REST request to get a page of SolicitacaoCompras");
        Page<SolicitacaoComprasDTO> page = solicitacaoComprasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solicitacao-compras/:id} : get the "id" solicitacaoCompras.
     *
     * @param id the id of the solicitacaoComprasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitacaoComprasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacao-compras/{id}")
    public ResponseEntity<SolicitacaoComprasDTO> getSolicitacaoCompras(@PathVariable Long id) {
        log.debug("REST request to get SolicitacaoCompras : {}", id);
        Optional<SolicitacaoComprasDTO> solicitacaoComprasDTO = solicitacaoComprasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoComprasDTO);
    }

    /**
     * {@code DELETE  /solicitacao-compras/:id} : delete the "id" solicitacaoCompras.
     *
     * @param id the id of the solicitacaoComprasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacao-compras/{id}")
    public ResponseEntity<Void> deleteSolicitacaoCompras(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoCompras : {}", id);
        solicitacaoComprasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/solicitacao-compras?query=:query} : search for the solicitacaoCompras corresponding
     * to the query.
     *
     * @param query the query of the solicitacaoCompras search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/solicitacao-compras")
    public ResponseEntity<List<SolicitacaoComprasDTO>> searchSolicitacaoCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolicitacaoCompras for query {}", query);
        Page<SolicitacaoComprasDTO> page = solicitacaoComprasService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
