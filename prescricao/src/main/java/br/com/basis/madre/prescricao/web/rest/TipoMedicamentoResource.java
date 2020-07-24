package br.com.basis.madre.prescricao.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

import br.com.basis.madre.prescricao.domain.TipoMedicamento;
import br.com.basis.madre.prescricao.repository.TipoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.TipoMedicamentoSearchRepository;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.TipoMedicamento}.
 */
@RestController
@RequestMapping("/api")
public class TipoMedicamentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoMedicamentoResource.class);

    private static final String ENTITY_NAME = "prescricaoTipoMedicamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoMedicamentoRepository tipoMedicamentoRepository;

    private final TipoMedicamentoSearchRepository tipoMedicamentoSearchRepository;

    public TipoMedicamentoResource(TipoMedicamentoRepository tipoMedicamentoRepository, TipoMedicamentoSearchRepository tipoMedicamentoSearchRepository) {
        this.tipoMedicamentoRepository = tipoMedicamentoRepository;
        this.tipoMedicamentoSearchRepository = tipoMedicamentoSearchRepository;
    }

    /**
     * {@code POST  /tipo-medicamentos} : Create a new tipoMedicamento.
     *
     * @param tipoMedicamento the tipoMedicamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoMedicamento, or with status {@code 400 (Bad Request)} if the tipoMedicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-medicamentos")
    public ResponseEntity<TipoMedicamento> createTipoMedicamento(@Valid @RequestBody TipoMedicamento tipoMedicamento) throws URISyntaxException {
        log.debug("REST request to save TipoMedicamento : {}", tipoMedicamento);
        if (tipoMedicamento.getId() != null) {
            throw new BadRequestAlertException("A new tipoMedicamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoMedicamento result = tipoMedicamentoRepository.save(tipoMedicamento);
        tipoMedicamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipo-medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-medicamentos} : Updates an existing tipoMedicamento.
     *
     * @param tipoMedicamento the tipoMedicamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMedicamento,
     * or with status {@code 400 (Bad Request)} if the tipoMedicamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoMedicamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-medicamentos")
    public ResponseEntity<TipoMedicamento> updateTipoMedicamento(@Valid @RequestBody TipoMedicamento tipoMedicamento) throws URISyntaxException {
        log.debug("REST request to update TipoMedicamento : {}", tipoMedicamento);
        if (tipoMedicamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoMedicamento result = tipoMedicamentoRepository.save(tipoMedicamento);
        tipoMedicamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoMedicamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-medicamentos} : get all the tipoMedicamentos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoMedicamentos in body.
     */
    @GetMapping("/tipo-medicamentos")
    public List<TipoMedicamento> getAllTipoMedicamentos() {
        log.debug("REST request to get all TipoMedicamentos");
        return tipoMedicamentoRepository.findAll();
    }

    /**
     * {@code GET  /tipo-medicamentos/:id} : get the "id" tipoMedicamento.
     *
     * @param id the id of the tipoMedicamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoMedicamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-medicamentos/{id}")
    public ResponseEntity<TipoMedicamento> getTipoMedicamento(@PathVariable Long id) {
        log.debug("REST request to get TipoMedicamento : {}", id);
        Optional<TipoMedicamento> tipoMedicamento = tipoMedicamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoMedicamento);
    }

    /**
     * {@code DELETE  /tipo-medicamentos/:id} : delete the "id" tipoMedicamento.
     *
     * @param id the id of the tipoMedicamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-medicamentos/{id}")
    public ResponseEntity<Void> deleteTipoMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete TipoMedicamento : {}", id);
        tipoMedicamentoRepository.deleteById(id);
        tipoMedicamentoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-medicamentos?query=:query} : search for the tipoMedicamento corresponding
     * to the query.
     *
     * @param query the query of the tipoMedicamento search.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-medicamentos")
    public List<TipoMedicamento> searchTipoMedicamentos(@RequestParam String query) {
        log.debug("REST request to search TipoMedicamentos for query {}", query);
        return StreamSupport
            .stream(tipoMedicamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
