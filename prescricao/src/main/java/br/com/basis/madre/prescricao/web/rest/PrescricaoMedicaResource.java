package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.PrescricaoMedicaService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicaDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.PrescricaoMedica}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoMedicaResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoMedicaResource.class);

    private static final String ENTITY_NAME = "prescricaoPrescricaoMedica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoMedicaService prescricaoMedicaService;

    public PrescricaoMedicaResource(PrescricaoMedicaService prescricaoMedicaService) {
        this.prescricaoMedicaService = prescricaoMedicaService;
    }

    /**
     * {@code POST  /prescricao-medicas} : Create a new prescricaoMedica.
     *
     * @param prescricaoMedicaDTO the prescricaoMedicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoMedicaDTO, or with status {@code 400 (Bad Request)} if the prescricaoMedica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricao-medicas")
    public ResponseEntity<PrescricaoMedicaDTO> createPrescricaoMedica(@RequestBody PrescricaoMedicaDTO prescricaoMedicaDTO) throws URISyntaxException {
        log.debug("REST request to save PrescricaoMedica : {}", prescricaoMedicaDTO);
        if (prescricaoMedicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescricaoMedica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoMedicaDTO result = prescricaoMedicaService.save(prescricaoMedicaDTO);
        return ResponseEntity.created(new URI("/api/prescricao-medicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricao-medicas} : Updates an existing prescricaoMedica.
     *
     * @param prescricaoMedicaDTO the prescricaoMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the prescricaoMedicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricao-medicas")
    public ResponseEntity<PrescricaoMedicaDTO> updatePrescricaoMedica(@RequestBody PrescricaoMedicaDTO prescricaoMedicaDTO) throws URISyntaxException {
        log.debug("REST request to update PrescricaoMedica : {}", prescricaoMedicaDTO);
        if (prescricaoMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoMedicaDTO result = prescricaoMedicaService.save(prescricaoMedicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoMedicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricao-medicas} : get all the prescricaoMedicas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaoMedicas in body.
     */
    @GetMapping("/prescricao-medicas")
    public ResponseEntity<List<PrescricaoMedicaDTO>> getAllPrescricaoMedicas(Pageable pageable) {
        log.debug("REST request to get a page of PrescricaoMedicas");
        Page<PrescricaoMedicaDTO> page = prescricaoMedicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescricao-medicas/:id} : get the "id" prescricaoMedica.
     *
     * @param id the id of the prescricaoMedicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoMedicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricao-medicas/{id}")
    public ResponseEntity<PrescricaoMedicaDTO> getPrescricaoMedica(@PathVariable Long id) {
        log.debug("REST request to get PrescricaoMedica : {}", id);
        Optional<PrescricaoMedicaDTO> prescricaoMedicaDTO = prescricaoMedicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescricaoMedicaDTO);
    }

    /**
     * {@code DELETE  /prescricao-medicas/:id} : delete the "id" prescricaoMedica.
     *
     * @param id the id of the prescricaoMedicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricao-medicas/{id}")
    public ResponseEntity<Void> deletePrescricaoMedica(@PathVariable Long id) {
        log.debug("REST request to delete PrescricaoMedica : {}", id);
        prescricaoMedicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricao-medicas?query=:query} : search for the prescricaoMedica corresponding
     * to the query.
     *
     * @param query the query of the prescricaoMedica search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricao-medicas")
    public ResponseEntity<List<PrescricaoMedicaDTO>> searchPrescricaoMedicas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrescricaoMedicas for query {}", query);
        Page<PrescricaoMedicaDTO> page = prescricaoMedicaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
