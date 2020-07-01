package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.service.EmergenciaService;
import br.com.basis.consulta.service.dto.EmergenciaDTO;
import br.com.basis.consulta.service.projection.CalendarioResumo;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.consulta.domain.Emergencia}.
 */
@RestController
@RequestMapping("/api")
public class EmergenciaResource {

    private final Logger log = LoggerFactory.getLogger(EmergenciaResource.class);

    private static final String ENTITY_NAME = "madreconsultaEmergencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmergenciaService emergenciaService;

    public EmergenciaResource(EmergenciaService emergenciaService) {
        this.emergenciaService = emergenciaService;
    }

    /**
     * {@code POST  /emergencias} : Create a new emergencia.
     *
     * @param emergenciaDTO the emergenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emergenciaDTO, or with status {@code 400 (Bad Request)} if the emergencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consultas-emergencias")
    public ResponseEntity<EmergenciaDTO> createEmergencia(@Valid @RequestBody EmergenciaDTO emergenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Emergencia : {}", emergenciaDTO);
        if (emergenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new emergencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmergenciaDTO result = emergenciaService.save(emergenciaDTO);
        return ResponseEntity.created(new URI("/api/emergencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emergencias} : Updates an existing emergencia.
     *
     * @param emergenciaDTO the emergenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emergenciaDTO,
     * or with status {@code 400 (Bad Request)} if the emergenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emergenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consultas-emergencias")
    public ResponseEntity<EmergenciaDTO> updateEmergencia(@Valid @RequestBody EmergenciaDTO emergenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Emergencia : {}", emergenciaDTO);
        if (emergenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmergenciaDTO result = emergenciaService.save(emergenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emergenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emergencias} : get all the emergencias.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emergencias in body.
     */
    @GetMapping("/consultas-emergencias")
    public ResponseEntity<List<EmergenciaDTO>> getAllEmergencias(Pageable pageable) {
        log.debug("REST request to get a page of Emergencias");
        Page<EmergenciaDTO> page = emergenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emergencias/:id} : get the "id" emergencia.
     *
     * @param id the id of the emergenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emergenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emergencias/{id}")
    public ResponseEntity<EmergenciaDTO> getEmergencia(@PathVariable Long id) {
        log.debug("REST request to get Emergencia : {}", id);
        Optional<EmergenciaDTO> emergenciaDTO = emergenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emergenciaDTO);
    }

    /**
     * {@code DELETE  /emergencias/:id} : delete the "id" emergencia.
     *
     * @param id the id of the emergenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consultas-emergencias/{id}")
    public ResponseEntity<Void> deleteEmergencia(@PathVariable Long id) {
        log.debug("REST request to delete Emergencia : {}", id);
        emergenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/emergencias?query=:query} : search for the emergencia corresponding
     * to the query.
     *
     * @param query the query of the emergencia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/consultas-emergencias")
    public ResponseEntity<List<EmergenciaDTO>> searchEmergencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Emergencias for query {}", query);
        Page<EmergenciaDTO> page = emergenciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/consultas-emergencias/calendario")
    @Timed
    public ResponseEntity<Page<CalendarioResumo>> buscarCalendarioResumo(Pageable pageable) {
        log.debug("REST request to get all Triagens");
        return ResponseEntity.ok(emergenciaService.buscarCalendarioResumo(pageable));
    }

}
