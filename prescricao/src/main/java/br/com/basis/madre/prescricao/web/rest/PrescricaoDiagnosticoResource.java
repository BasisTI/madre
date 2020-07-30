package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.PrescricaoDiagnosticoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDiagnosticoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.PrescricaoDiagnostico}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoDiagnosticoResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoDiagnosticoResource.class);

    private static final String ENTITY_NAME = "prescricaoPrescricaoDiagnostico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoDiagnosticoService prescricaoDiagnosticoService;

    public PrescricaoDiagnosticoResource(PrescricaoDiagnosticoService prescricaoDiagnosticoService) {
        this.prescricaoDiagnosticoService = prescricaoDiagnosticoService;
    }

    /**
     * {@code POST  /prescricao-diagnosticos} : Create a new prescricaoDiagnostico.
     *
     * @param prescricaoDiagnosticoDTO the prescricaoDiagnosticoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoDiagnosticoDTO, or with status {@code 400 (Bad Request)} if the prescricaoDiagnostico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricao-diagnosticos")
    public ResponseEntity<PrescricaoDiagnosticoDTO> createPrescricaoDiagnostico(@RequestBody PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO) throws URISyntaxException {
        log.debug("REST request to save PrescricaoDiagnostico : {}", prescricaoDiagnosticoDTO);
        if (prescricaoDiagnosticoDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescricaoDiagnostico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoDiagnosticoDTO result = prescricaoDiagnosticoService.save(prescricaoDiagnosticoDTO);
        return ResponseEntity.created(new URI("/api/prescricao-diagnosticos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricao-diagnosticos} : Updates an existing prescricaoDiagnostico.
     *
     * @param prescricaoDiagnosticoDTO the prescricaoDiagnosticoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoDiagnosticoDTO,
     * or with status {@code 400 (Bad Request)} if the prescricaoDiagnosticoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoDiagnosticoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricao-diagnosticos")
    public ResponseEntity<PrescricaoDiagnosticoDTO> updatePrescricaoDiagnostico(@RequestBody PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO) throws URISyntaxException {
        log.debug("REST request to update PrescricaoDiagnostico : {}", prescricaoDiagnosticoDTO);
        if (prescricaoDiagnosticoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoDiagnosticoDTO result = prescricaoDiagnosticoService.save(prescricaoDiagnosticoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoDiagnosticoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricao-diagnosticos} : get all the prescricaoDiagnosticos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaoDiagnosticos in body.
     */
    @GetMapping("/prescricao-diagnosticos")
    public ResponseEntity<List<PrescricaoDiagnosticoDTO>> getAllPrescricaoDiagnosticos(Pageable pageable) {
        log.debug("REST request to get a page of PrescricaoDiagnosticos");
        Page<PrescricaoDiagnosticoDTO> page = prescricaoDiagnosticoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescricao-diagnosticos/:id} : get the "id" prescricaoDiagnostico.
     *
     * @param id the id of the prescricaoDiagnosticoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoDiagnosticoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricao-diagnosticos/{id}")
    public ResponseEntity<PrescricaoDiagnosticoDTO> getPrescricaoDiagnostico(@PathVariable Long id) {
        log.debug("REST request to get PrescricaoDiagnostico : {}", id);
        Optional<PrescricaoDiagnosticoDTO> prescricaoDiagnosticoDTO = prescricaoDiagnosticoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescricaoDiagnosticoDTO);
    }

    /**
     * {@code DELETE  /prescricao-diagnosticos/:id} : delete the "id" prescricaoDiagnostico.
     *
     * @param id the id of the prescricaoDiagnosticoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricao-diagnosticos/{id}")
    public ResponseEntity<Void> deletePrescricaoDiagnostico(@PathVariable Long id) {
        log.debug("REST request to delete PrescricaoDiagnostico : {}", id);
        prescricaoDiagnosticoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricao-diagnosticos?query=:query} : search for the prescricaoDiagnostico corresponding
     * to the query.
     *
     * @param query the query of the prescricaoDiagnostico search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricao-diagnosticos")
    public ResponseEntity<List<PrescricaoDiagnosticoDTO>> searchPrescricaoDiagnosticos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrescricaoDiagnosticos for query {}", query);
        Page<PrescricaoDiagnosticoDTO> page = prescricaoDiagnosticoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
