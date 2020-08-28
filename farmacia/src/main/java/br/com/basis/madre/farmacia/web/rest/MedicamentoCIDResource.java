package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.MedicamentoCIDService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.MedicamentoCIDDTO;

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
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.MedicamentoCID}.
 */
@RestController
@RequestMapping("/api")
public class MedicamentoCIDResource {

    private final Logger log = LoggerFactory.getLogger(MedicamentoCIDResource.class);

    private static final String ENTITY_NAME = "farmaciaMedicamentoCid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicamentoCIDService medicamentoCIDService;

    public MedicamentoCIDResource(MedicamentoCIDService medicamentoCIDService) {
        this.medicamentoCIDService = medicamentoCIDService;
    }

    /**
     * {@code POST  /medicamento-cids} : Create a new medicamentoCID.
     *
     * @param medicamentoCIDDTO the medicamentoCIDDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicamentoCIDDTO, or with status {@code 400 (Bad Request)} if the medicamentoCID has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicamento-cids")
    public ResponseEntity<MedicamentoCIDDTO> createMedicamentoCID(@RequestBody MedicamentoCIDDTO medicamentoCIDDTO) throws URISyntaxException {
        log.debug("REST request to save MedicamentoCID : {}", medicamentoCIDDTO);
        if (medicamentoCIDDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicamentoCID cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicamentoCIDDTO result = medicamentoCIDService.save(medicamentoCIDDTO);
        return ResponseEntity.created(new URI("/api/medicamento-cids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicamento-cids} : Updates an existing medicamentoCID.
     *
     * @param medicamentoCIDDTO the medicamentoCIDDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicamentoCIDDTO,
     * or with status {@code 400 (Bad Request)} if the medicamentoCIDDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicamentoCIDDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicamento-cids")
    public ResponseEntity<MedicamentoCIDDTO> updateMedicamentoCID(@RequestBody MedicamentoCIDDTO medicamentoCIDDTO) throws URISyntaxException {
        log.debug("REST request to update MedicamentoCID : {}", medicamentoCIDDTO);
        if (medicamentoCIDDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicamentoCIDDTO result = medicamentoCIDService.save(medicamentoCIDDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicamentoCIDDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicamento-cids} : get all the medicamentoCIDS.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicamentoCIDS in body.
     */
    @GetMapping("/medicamento-cids")
    public ResponseEntity<List<MedicamentoCIDDTO>> getAllMedicamentoCIDS(Pageable pageable) {
        log.debug("REST request to get a page of MedicamentoCIDS");
        Page<MedicamentoCIDDTO> page = medicamentoCIDService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicamento-cids/:id} : get the "id" medicamentoCID.
     *
     * @param id the id of the medicamentoCIDDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicamentoCIDDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicamento-cids/{id}")
    public ResponseEntity<MedicamentoCIDDTO> getMedicamentoCID(@PathVariable Long id) {
        log.debug("REST request to get MedicamentoCID : {}", id);
        Optional<MedicamentoCIDDTO> medicamentoCIDDTO = medicamentoCIDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicamentoCIDDTO);
    }

    /**
     * {@code DELETE  /medicamento-cids/:id} : delete the "id" medicamentoCID.
     *
     * @param id the id of the medicamentoCIDDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicamento-cids/{id}")
    public ResponseEntity<Void> deleteMedicamentoCID(@PathVariable Long id) {
        log.debug("REST request to delete MedicamentoCID : {}", id);
        medicamentoCIDService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medicamento-cids?query=:query} : search for the medicamentoCID corresponding
     * to the query.
     *
     * @param query the query of the medicamentoCID search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/medicamento-cids")
    public ResponseEntity<List<MedicamentoCIDDTO>> searchMedicamentoCIDS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicamentoCIDS for query {}", query);
        Page<MedicamentoCIDDTO> page = medicamentoCIDService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
