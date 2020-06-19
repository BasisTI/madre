package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.AlmoxarifadoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.Almoxarifado}.
 */
@RestController
@RequestMapping("/api")
public class AlmoxarifadoResource {

    private final Logger log = LoggerFactory.getLogger(AlmoxarifadoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosAlmoxarifado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlmoxarifadoService almoxarifadoService;

    public AlmoxarifadoResource(AlmoxarifadoService almoxarifadoService) {
        this.almoxarifadoService = almoxarifadoService;
    }

    /**
     * {@code POST  /almoxarifados} : Create a new almoxarifado.
     *
     * @param almoxarifadoDTO the almoxarifadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new almoxarifadoDTO, or with status {@code 400 (Bad Request)} if the almoxarifado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/almoxarifados")
    public ResponseEntity<AlmoxarifadoDTO> createAlmoxarifado(@Valid @RequestBody AlmoxarifadoDTO almoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to save Almoxarifado : {}", almoxarifadoDTO);
        if (almoxarifadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new almoxarifado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlmoxarifadoDTO result = almoxarifadoService.save(almoxarifadoDTO);
        return ResponseEntity.created(new URI("/api/almoxarifados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /almoxarifados} : Updates an existing almoxarifado.
     *
     * @param almoxarifadoDTO the almoxarifadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated almoxarifadoDTO,
     * or with status {@code 400 (Bad Request)} if the almoxarifadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the almoxarifadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/almoxarifados")
    public ResponseEntity<AlmoxarifadoDTO> updateAlmoxarifado(@Valid @RequestBody AlmoxarifadoDTO almoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to update Almoxarifado : {}", almoxarifadoDTO);
        if (almoxarifadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlmoxarifadoDTO result = almoxarifadoService.save(almoxarifadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, almoxarifadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /almoxarifados} : get all the almoxarifados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of almoxarifados in body.
     */
    @GetMapping("/almoxarifados")
    public ResponseEntity<List<AlmoxarifadoDTO>> getAllAlmoxarifados(Pageable pageable) {
        log.debug("REST request to get a page of Almoxarifados");
        Page<AlmoxarifadoDTO> page = almoxarifadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /almoxarifados/:id} : get the "id" almoxarifado.
     *
     * @param id the id of the almoxarifadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the almoxarifadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/almoxarifados/{id}")
    public ResponseEntity<AlmoxarifadoDTO> getAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to get Almoxarifado : {}", id);
        Optional<AlmoxarifadoDTO> almoxarifadoDTO = almoxarifadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(almoxarifadoDTO);
    }

    /**
     * {@code DELETE  /almoxarifados/:id} : delete the "id" almoxarifado.
     *
     * @param id the id of the almoxarifadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/almoxarifados/{id}")
    public ResponseEntity<Void> deleteAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to delete Almoxarifado : {}", id);
        almoxarifadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/almoxarifados?query=:query} : search for the almoxarifado corresponding
     * to the query.
     *
     * @param query the query of the almoxarifado search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/almoxarifados")
    public ResponseEntity<List<AlmoxarifadoDTO>> searchAlmoxarifados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Almoxarifados for query {}", query);
        Page<AlmoxarifadoDTO> page = almoxarifadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
