package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.MotivoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.MotivoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.Motivo}.
 */
@RestController
@RequestMapping("/api")
public class MotivoResource {

    private final Logger log = LoggerFactory.getLogger(MotivoResource.class);

    private static final String ENTITY_NAME = "farmaciaMotivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivoService motivoService;

    public MotivoResource(MotivoService motivoService) {
        this.motivoService = motivoService;
    }

    /**
     * {@code POST  /motivos} : Create a new motivo.
     *
     * @param motivoDTO the motivoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivoDTO, or with status {@code 400 (Bad Request)} if the motivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivos")
    public ResponseEntity<MotivoDTO> createMotivo(@RequestBody MotivoDTO motivoDTO) throws URISyntaxException {
        log.debug("REST request to save Motivo : {}", motivoDTO);
        if (motivoDTO.getId() != null) {
            throw new BadRequestAlertException("A new motivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotivoDTO result = motivoService.save(motivoDTO);
        return ResponseEntity.created(new URI("/api/motivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motivos} : Updates an existing motivo.
     *
     * @param motivoDTO the motivoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivoDTO,
     * or with status {@code 400 (Bad Request)} if the motivoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivos")
    public ResponseEntity<MotivoDTO> updateMotivo(@RequestBody MotivoDTO motivoDTO) throws URISyntaxException {
        log.debug("REST request to update Motivo : {}", motivoDTO);
        if (motivoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MotivoDTO result = motivoService.save(motivoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, motivoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motivos} : get all the motivos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motivos in body.
     */
    @GetMapping("/motivos")
    public ResponseEntity<List<MotivoDTO>> getAllMotivos(Pageable pageable) {
        log.debug("REST request to get a page of Motivos");
        Page<MotivoDTO> page = motivoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /motivos/:id} : get the "id" motivo.
     *
     * @param id the id of the motivoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivos/{id}")
    public ResponseEntity<MotivoDTO> getMotivo(@PathVariable Long id) {
        log.debug("REST request to get Motivo : {}", id);
        Optional<MotivoDTO> motivoDTO = motivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motivoDTO);
    }

    /**
     * {@code DELETE  /motivos/:id} : delete the "id" motivo.
     *
     * @param id the id of the motivoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivos/{id}")
    public ResponseEntity<Void> deleteMotivo(@PathVariable Long id) {
        log.debug("REST request to delete Motivo : {}", id);
        motivoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/motivos?query=:query} : search for the motivo corresponding
     * to the query.
     *
     * @param query the query of the motivo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/motivos")
    public ResponseEntity<List<MotivoDTO>> searchMotivos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Motivos for query {}", query);
        Page<MotivoDTO> page = motivoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
