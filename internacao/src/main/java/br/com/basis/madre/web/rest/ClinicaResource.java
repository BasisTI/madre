package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ClinicaService;
import br.com.basis.madre.service.dto.ClinicaDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Clinica}.
 */
@RestController
@RequestMapping("/api")
public class ClinicaResource {

    private final Logger log = LoggerFactory.getLogger(ClinicaResource.class);

    private static final String ENTITY_NAME = "internacaoClinica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClinicaService clinicaService;

    public ClinicaResource(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }

    /**
     * {@code POST  /clinicas} : Create a new clinica.
     *
     * @param clinicaDTO the clinicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clinicaDTO, or with status {@code 400 (Bad Request)} if the clinica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clinicas")
    public ResponseEntity<ClinicaDTO> createClinica(@RequestBody ClinicaDTO clinicaDTO) throws URISyntaxException {
        log.debug("REST request to save Clinica : {}", clinicaDTO);
        if (clinicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new clinica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClinicaDTO result = clinicaService.save(clinicaDTO);
        return ResponseEntity.created(new URI("/api/clinicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clinicas} : Updates an existing clinica.
     *
     * @param clinicaDTO the clinicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clinicaDTO,
     * or with status {@code 400 (Bad Request)} if the clinicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clinicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clinicas")
    public ResponseEntity<ClinicaDTO> updateClinica(@RequestBody ClinicaDTO clinicaDTO) throws URISyntaxException {
        log.debug("REST request to update Clinica : {}", clinicaDTO);
        if (clinicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClinicaDTO result = clinicaService.save(clinicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clinicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clinicas} : get all the clinicas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clinicas in body.
     */
    @GetMapping("/clinicas")
    public ResponseEntity<List<ClinicaDTO>> getAllClinicas(Pageable pageable) {
        log.debug("REST request to get a page of Clinicas");
        Page<ClinicaDTO> page = clinicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clinicas/:id} : get the "id" clinica.
     *
     * @param id the id of the clinicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clinicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clinicas/{id}")
    public ResponseEntity<ClinicaDTO> getClinica(@PathVariable Long id) {
        log.debug("REST request to get Clinica : {}", id);
        Optional<ClinicaDTO> clinicaDTO = clinicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clinicaDTO);
    }

    /**
     * {@code DELETE  /clinicas/:id} : delete the "id" clinica.
     *
     * @param id the id of the clinicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clinicas/{id}")
    public ResponseEntity<Void> deleteClinica(@PathVariable Long id) {
        log.debug("REST request to delete Clinica : {}", id);
        clinicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/clinicas?query=:query} : search for the clinica corresponding
     * to the query.
     *
     * @param query the query of the clinica search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/clinicas")
    public ResponseEntity<List<ClinicaDTO>> searchClinicas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clinicas for query {}", query);
        Page<ClinicaDTO> page = clinicaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
