package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.PrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.PrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.PrescricaoMedicamento}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoMedicamentoResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoMedicamentoResource.class);

    private static final String ENTITY_NAME = "prescricaoPrescricaoMedicamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoMedicamentoRepository prescricaoMedicamentoRepository;

    private final PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository;

    public PrescricaoMedicamentoResource(PrescricaoMedicamentoRepository prescricaoMedicamentoRepository, PrescricaoMedicamentoSearchRepository prescricaoMedicamentoSearchRepository) {
        this.prescricaoMedicamentoRepository = prescricaoMedicamentoRepository;
        this.prescricaoMedicamentoSearchRepository = prescricaoMedicamentoSearchRepository;
    }

    /**
     * {@code POST  /prescricao-medicamentos} : Create a new prescricaoMedicamento.
     *
     * @param prescricaoMedicamento the prescricaoMedicamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoMedicamento, or with status {@code 400 (Bad Request)} if the prescricaoMedicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricao-medicamentos")
    public ResponseEntity<PrescricaoMedicamento> createPrescricaoMedicamento(@Valid @RequestBody PrescricaoMedicamento prescricaoMedicamento) throws URISyntaxException {
        log.debug("REST request to save PrescricaoMedicamento : {}", prescricaoMedicamento);
        if (prescricaoMedicamento.getId() != null) {
            throw new BadRequestAlertException("A new prescricaoMedicamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoMedicamento result = prescricaoMedicamentoRepository.save(prescricaoMedicamento);
        prescricaoMedicamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prescricao-medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricao-medicamentos} : Updates an existing prescricaoMedicamento.
     *
     * @param prescricaoMedicamento the prescricaoMedicamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoMedicamento,
     * or with status {@code 400 (Bad Request)} if the prescricaoMedicamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoMedicamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricao-medicamentos")
    public ResponseEntity<PrescricaoMedicamento> updatePrescricaoMedicamento(@Valid @RequestBody PrescricaoMedicamento prescricaoMedicamento) throws URISyntaxException {
        log.debug("REST request to update PrescricaoMedicamento : {}", prescricaoMedicamento);
        if (prescricaoMedicamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoMedicamento result = prescricaoMedicamentoRepository.save(prescricaoMedicamento);
        prescricaoMedicamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoMedicamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricao-medicamentos} : get all the prescricaoMedicamentos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaoMedicamentos in body.
     */
    @GetMapping("/prescricao-medicamentos")
    public List<PrescricaoMedicamento> getAllPrescricaoMedicamentos() {
        log.debug("REST request to get all PrescricaoMedicamentos");
        return prescricaoMedicamentoRepository.findAll();
    }

    /**
     * {@code GET  /prescricao-medicamentos/:id} : get the "id" prescricaoMedicamento.
     *
     * @param id the id of the prescricaoMedicamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoMedicamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricao-medicamentos/{id}")
    public ResponseEntity<PrescricaoMedicamento> getPrescricaoMedicamento(@PathVariable Long id) {
        log.debug("REST request to get PrescricaoMedicamento : {}", id);
        Optional<PrescricaoMedicamento> prescricaoMedicamento = prescricaoMedicamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prescricaoMedicamento);
    }

    /**
     * {@code DELETE  /prescricao-medicamentos/:id} : delete the "id" prescricaoMedicamento.
     *
     * @param id the id of the prescricaoMedicamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricao-medicamentos/{id}")
    public ResponseEntity<Void> deletePrescricaoMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete PrescricaoMedicamento : {}", id);
        prescricaoMedicamentoRepository.deleteById(id);
        prescricaoMedicamentoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricao-medicamentos?query=:query} : search for the prescricaoMedicamento corresponding
     * to the query.
     *
     * @param query the query of the prescricaoMedicamento search.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricao-medicamentos")
    public List<PrescricaoMedicamento> searchPrescricaoMedicamentos(@RequestParam String query) {
        log.debug("REST request to search PrescricaoMedicamentos for query {}", query);
        return StreamSupport
            .stream(prescricaoMedicamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
