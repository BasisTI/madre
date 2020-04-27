package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ProcedenciaService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.ProcedenciaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Procedencia}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProcedenciaResource {
    private final Logger log = LoggerFactory.getLogger(ProcedenciaResource.class);
    private static final String ENTITY_NAME = "pacientesProcedencia";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final ProcedenciaService procedenciaService;

    /**
     * {@code POST  /procedencias} : Create a new procedencia.
     *
     * @param procedenciaDTO the procedenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procedenciaDTO, or with status {@code 400 (Bad Request)} if the procedencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procedencias")
    public ResponseEntity<ProcedenciaDTO> createProcedencia(@Valid @RequestBody ProcedenciaDTO procedenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Procedencia : {}", procedenciaDTO);
        if (procedenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedenciaDTO result = procedenciaService.save(procedenciaDTO);
        return ResponseEntity.created(new URI("/api/procedencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procedencias} : Updates an existing procedencia.
     *
     * @param procedenciaDTO the procedenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedenciaDTO,
     * or with status {@code 400 (Bad Request)} if the procedenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procedenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procedencias")
    public ResponseEntity<ProcedenciaDTO> updateProcedencia(@Valid @RequestBody ProcedenciaDTO procedenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Procedencia : {}", procedenciaDTO);
        if (procedenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcedenciaDTO result = procedenciaService.save(procedenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procedenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /procedencias} : get all the procedencias.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procedencias in body.
     */
    @GetMapping("/procedencias")
    public ResponseEntity<List<ProcedenciaDTO>> getAllProcedencias(Pageable pageable) {
        log.debug("REST request to get a page of Procedencias");
        Page<ProcedenciaDTO> page = procedenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /procedencias/:id} : get the "id" procedencia.
     *
     * @param id the id of the procedenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procedenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procedencias/{id}")
    public ResponseEntity<ProcedenciaDTO> getProcedencia(@PathVariable Long id) {
        log.debug("REST request to get Procedencia : {}", id);
        Optional<ProcedenciaDTO> procedenciaDTO = procedenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedenciaDTO);
    }

    /**
     * {@code DELETE  /procedencias/:id} : delete the "id" procedencia.
     *
     * @param id the id of the procedenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procedencias/{id}")
    public ResponseEntity<Void> deleteProcedencia(@PathVariable Long id) {
        log.debug("REST request to delete Procedencia : {}", id);
        procedenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/procedencias?query=:query} : search for the procedencia corresponding
     * to the query.
     *
     * @param query the query of the procedencia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/procedencias")
    public ResponseEntity<List<ProcedenciaDTO>> searchProcedencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Procedencias for query {}", query);
        Page<ProcedenciaDTO> page = procedenciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
