package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TransferenciaAlmoxarifadoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado}.
 */
@RestController
@RequestMapping("/api")
public class TransferenciaAlmoxarifadoResource {

    private final Logger log = LoggerFactory.getLogger(TransferenciaAlmoxarifadoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosTransferenciaAlmoxarifado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferenciaAlmoxarifadoService transferenciaAlmoxarifadoService;

    public TransferenciaAlmoxarifadoResource(TransferenciaAlmoxarifadoService transferenciaAlmoxarifadoService) {
        this.transferenciaAlmoxarifadoService = transferenciaAlmoxarifadoService;
    }

    /**
     * {@code POST  /transferencia-almoxarifados} : Create a new transferenciaAlmoxarifado.
     *
     * @param transferenciaAlmoxarifadoDTO the transferenciaAlmoxarifadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferenciaAlmoxarifadoDTO, or with status {@code 400 (Bad Request)} if the transferenciaAlmoxarifado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferencia-almoxarifados")
    public ResponseEntity<TransferenciaAlmoxarifadoDTO> createTransferenciaAlmoxarifado(@Valid @RequestBody TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to save TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        if (transferenciaAlmoxarifadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferenciaAlmoxarifado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoService.save(transferenciaAlmoxarifadoDTO);
        return ResponseEntity.created(new URI("/api/transferencia-almoxarifados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transferencia-almoxarifados} : Updates an existing transferenciaAlmoxarifado.
     *
     * @param transferenciaAlmoxarifadoDTO the transferenciaAlmoxarifadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferenciaAlmoxarifadoDTO,
     * or with status {@code 400 (Bad Request)} if the transferenciaAlmoxarifadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferenciaAlmoxarifadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferencia-almoxarifados")
    public ResponseEntity<TransferenciaAlmoxarifadoDTO> updateTransferenciaAlmoxarifado(@Valid @RequestBody TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to update TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        if (transferenciaAlmoxarifadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoService.save(transferenciaAlmoxarifadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transferenciaAlmoxarifadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transferencia-almoxarifados} : get all the transferenciaAlmoxarifados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferenciaAlmoxarifados in body.
     */
    @GetMapping("/transferencia-almoxarifados")
    public ResponseEntity<List<TransferenciaAlmoxarifadoDTO>> getAllTransferenciaAlmoxarifados(Pageable pageable) {
        log.debug("REST request to get a page of TransferenciaAlmoxarifados");
        Page<TransferenciaAlmoxarifadoDTO> page = transferenciaAlmoxarifadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transferencia-almoxarifados/:id} : get the "id" transferenciaAlmoxarifado.
     *
     * @param id the id of the transferenciaAlmoxarifadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferenciaAlmoxarifadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferencia-almoxarifados/{id}")
    public ResponseEntity<TransferenciaAlmoxarifadoDTO> getTransferenciaAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to get TransferenciaAlmoxarifado : {}", id);
        Optional<TransferenciaAlmoxarifadoDTO> transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferenciaAlmoxarifadoDTO);
    }

    /**
     * {@code DELETE  /transferencia-almoxarifados/:id} : delete the "id" transferenciaAlmoxarifado.
     *
     * @param id the id of the transferenciaAlmoxarifadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferencia-almoxarifados/{id}")
    public ResponseEntity<Void> deleteTransferenciaAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to delete TransferenciaAlmoxarifado : {}", id);
        transferenciaAlmoxarifadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transferencia-almoxarifados?query=:query} : search for the transferenciaAlmoxarifado corresponding
     * to the query.
     *
     * @param query the query of the transferenciaAlmoxarifado search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/transferencia-almoxarifados")
    public ResponseEntity<List<TransferenciaAlmoxarifadoDTO>> searchTransferenciaAlmoxarifados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TransferenciaAlmoxarifados for query {}", query);
        Page<TransferenciaAlmoxarifadoDTO> page = transferenciaAlmoxarifadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
