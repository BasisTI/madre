package br.com.basis.madre.prescricao.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.com.basis.madre.prescricao.service.PrescricaoDietaService;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDietaDTO;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.PrescricaoDieta}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoDietaResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoDietaResource.class);

    private static final String ENTITY_NAME = "prescricaoPrescricaoDieta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoDietaService prescricaoDietaService;

    public PrescricaoDietaResource(PrescricaoDietaService prescricaoDietaService) {
        this.prescricaoDietaService = prescricaoDietaService;
    }

    /**
     * {@code POST  /prescricao-dietas} : Create a new prescricaoDieta.
     *
     * @param prescricaoDietaDTO the prescricaoDietaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoDietaDTO, or with status {@code 400 (Bad Request)} if the prescricaoDieta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricao-dietas")
    public ResponseEntity<PrescricaoDietaDTO> createPrescricaoDieta(@Valid @RequestBody PrescricaoDietaDTO prescricaoDietaDTO) throws URISyntaxException {
        log.debug("REST request to save PrescricaoDieta : {}", prescricaoDietaDTO);
        if (prescricaoDietaDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescricaoDieta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoDietaDTO result = prescricaoDietaService.save(prescricaoDietaDTO);
        return ResponseEntity.created(new URI("/api/prescricao-dietas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricao-dietas} : Updates an existing prescricaoDieta.
     *
     * @param prescricaoDietaDTO the prescricaoDietaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoDietaDTO,
     * or with status {@code 400 (Bad Request)} if the prescricaoDietaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoDietaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricao-dietas")
    public ResponseEntity<PrescricaoDietaDTO> updatePrescricaoDieta(@Valid @RequestBody PrescricaoDietaDTO prescricaoDietaDTO) throws URISyntaxException {
        log.debug("REST request to update PrescricaoDieta : {}", prescricaoDietaDTO);
        if (prescricaoDietaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoDietaDTO result = prescricaoDietaService.save(prescricaoDietaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoDietaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricao-dietas} : get all the prescricaoDietas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaoDietas in body.
     */
    @GetMapping("/prescricao-dietas")
    public ResponseEntity<List<PrescricaoDietaDTO>> getAllPrescricaoDietas(Pageable pageable) {
        log.debug("REST request to get a page of PrescricaoDietas");
        Page<PrescricaoDietaDTO> page = prescricaoDietaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescricao-dietas/:id} : get the "id" prescricaoDieta.
     *
     * @param id the id of the prescricaoDietaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoDietaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricao-dietas/{id}")
    public ResponseEntity<PrescricaoDietaDTO> getPrescricaoDieta(@PathVariable Long id) {
        log.debug("REST request to get PrescricaoDieta : {}", id);
        Optional<PrescricaoDietaDTO> prescricaoDietaDTO = prescricaoDietaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescricaoDietaDTO);
    }

    /**
     * {@code DELETE  /prescricao-dietas/:id} : delete the "id" prescricaoDieta.
     *
     * @param id the id of the prescricaoDietaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricao-dietas/{id}")
    public ResponseEntity<Void> deletePrescricaoDieta(@PathVariable Long id) {
        log.debug("REST request to delete PrescricaoDieta : {}", id);
        prescricaoDietaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricao-dietas?query=:query} : search for the prescricaoDieta corresponding
     * to the query.
     *
     * @param query the query of the prescricaoDieta search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricao-dietas")
    public ResponseEntity<List<PrescricaoDietaDTO>> searchPrescricaoDietas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrescricaoDietas for query {}", query);
        Page<PrescricaoDietaDTO> page = prescricaoDietaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
