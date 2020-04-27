package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.HospitalService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.HospitalDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Hospital}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HospitalResource {
    private final Logger log = LoggerFactory.getLogger(HospitalResource.class);
    private static final String ENTITY_NAME = "pacientesHospital";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final HospitalService hospitalService;

    /**
     * {@code POST  /hospitals} : Create a new hospital.
     *
     * @param hospitalDTO the hospitalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalDTO, or with status {@code 400 (Bad Request)} if the hospital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospitals")
    public ResponseEntity<HospitalDTO> createHospital(@Valid @RequestBody HospitalDTO hospitalDTO) throws URISyntaxException {
        log.debug("REST request to save Hospital : {}", hospitalDTO);
        if (hospitalDTO.getId() != null) {
            throw new BadRequestAlertException("A new hospital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HospitalDTO result = hospitalService.save(hospitalDTO);
        return ResponseEntity.created(new URI("/api/hospitals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospitals} : Updates an existing hospital.
     *
     * @param hospitalDTO the hospitalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalDTO,
     * or with status {@code 400 (Bad Request)} if the hospitalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospitals")
    public ResponseEntity<HospitalDTO> updateHospital(@Valid @RequestBody HospitalDTO hospitalDTO) throws URISyntaxException {
        log.debug("REST request to update Hospital : {}", hospitalDTO);
        if (hospitalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HospitalDTO result = hospitalService.save(hospitalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hospitals} : get all the hospitals.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitals in body.
     */
    @GetMapping("/hospitals")
    public ResponseEntity<List<HospitalDTO>> getAllHospitals(Pageable pageable) {
        log.debug("REST request to get a page of Hospitals");
        Page<HospitalDTO> page = hospitalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hospitals/:id} : get the "id" hospital.
     *
     * @param id the id of the hospitalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospitals/{id}")
    public ResponseEntity<HospitalDTO> getHospital(@PathVariable Long id) {
        log.debug("REST request to get Hospital : {}", id);
        Optional<HospitalDTO> hospitalDTO = hospitalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hospitalDTO);
    }

    /**
     * {@code DELETE  /hospitals/:id} : delete the "id" hospital.
     *
     * @param id the id of the hospitalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospitals/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        log.debug("REST request to delete Hospital : {}", id);
        hospitalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/hospitals?query=:query} : search for the hospital corresponding
     * to the query.
     *
     * @param query the query of the hospital search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/hospitals")
    public ResponseEntity<List<HospitalDTO>> searchHospitals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Hospitals for query {}", query);
        Page<HospitalDTO> page = hospitalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
