package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.PrescricaoService;
import br.com.basis.madre.service.dto.PrescricaoDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Prescricao}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoResource.class);

    private static final String ENTITY_NAME = "internacaoPrescricao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoService prescricaoService;

    public PrescricaoResource(PrescricaoService prescricaoService) {
        this.prescricaoService = prescricaoService;
    }

    /**
     * {@code POST  /prescricaos} : Create a new prescricao.
     *
     * @param prescricaoDTO the prescricaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoDTO, or with status {@code 400 (Bad Request)} if the prescricao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricaos")
    public ResponseEntity<PrescricaoDTO> createPrescricao(@RequestBody PrescricaoDTO prescricaoDTO) throws URISyntaxException {
        log.debug("REST request to save Prescricao : {}", prescricaoDTO);
        if (prescricaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescricao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoDTO result = prescricaoService.save(prescricaoDTO);
        return ResponseEntity.created(new URI("/api/prescricaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricaos} : Updates an existing prescricao.
     *
     * @param prescricaoDTO the prescricaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoDTO,
     * or with status {@code 400 (Bad Request)} if the prescricaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricaos")
    public ResponseEntity<PrescricaoDTO> updatePrescricao(@RequestBody PrescricaoDTO prescricaoDTO) throws URISyntaxException {
        log.debug("REST request to update Prescricao : {}", prescricaoDTO);
        if (prescricaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoDTO result = prescricaoService.save(prescricaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricaos} : get all the prescricaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaos in body.
     */
    @GetMapping("/prescricaos")
    public ResponseEntity<List<PrescricaoDTO>> getAllPrescricaos(Pageable pageable) {
        log.debug("REST request to get a page of Prescricaos");
        Page<PrescricaoDTO> page = prescricaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescricaos/:id} : get the "id" prescricao.
     *
     * @param id the id of the prescricaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricaos/{id}")
    public ResponseEntity<PrescricaoDTO> getPrescricao(@PathVariable Long id) {
        log.debug("REST request to get Prescricao : {}", id);
        Optional<PrescricaoDTO> prescricaoDTO = prescricaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescricaoDTO);
    }

    /**
     * {@code DELETE  /prescricaos/:id} : delete the "id" prescricao.
     *
     * @param id the id of the prescricaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricaos/{id}")
    public ResponseEntity<Void> deletePrescricao(@PathVariable Long id) {
        log.debug("REST request to delete Prescricao : {}", id);
        prescricaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricaos?query=:query} : search for the prescricao corresponding
     * to the query.
     *
     * @param query the query of the prescricao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricaos")
    public ResponseEntity<List<PrescricaoDTO>> searchPrescricaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Prescricaos for query {}", query);
        Page<PrescricaoDTO> page = prescricaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
