package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.domain.Diluente;
import br.com.basis.madre.prescricao.repository.DiluenteRepository;
import br.com.basis.madre.prescricao.repository.search.DiluenteSearchRepository;
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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.Diluente}.
 */
@RestController
@RequestMapping("/api")
public class DiluenteResource {

    private final Logger log = LoggerFactory.getLogger(DiluenteResource.class);

    private static final String ENTITY_NAME = "prescricaoDiluente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiluenteRepository diluenteRepository;

    private final DiluenteSearchRepository diluenteSearchRepository;

    public DiluenteResource(DiluenteRepository diluenteRepository, DiluenteSearchRepository diluenteSearchRepository) {
        this.diluenteRepository = diluenteRepository;
        this.diluenteSearchRepository = diluenteSearchRepository;
    }

    /**
     * {@code POST  /diluentes} : Create a new diluente.
     *
     * @param diluente the diluente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diluente, or with status {@code 400 (Bad Request)} if the diluente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diluentes")
    public ResponseEntity<Diluente> createDiluente(@Valid @RequestBody Diluente diluente) throws URISyntaxException {
        log.debug("REST request to save Diluente : {}", diluente);
        if (diluente.getId() != null) {
            throw new BadRequestAlertException("A new diluente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diluente result = diluenteRepository.save(diluente);
        diluenteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/diluentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diluentes} : Updates an existing diluente.
     *
     * @param diluente the diluente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diluente,
     * or with status {@code 400 (Bad Request)} if the diluente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diluente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diluentes")
    public ResponseEntity<Diluente> updateDiluente(@Valid @RequestBody Diluente diluente) throws URISyntaxException {
        log.debug("REST request to update Diluente : {}", diluente);
        if (diluente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Diluente result = diluenteRepository.save(diluente);
        diluenteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diluente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /diluentes} : get all the diluentes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diluentes in body.
     */
    @GetMapping("/diluentes")
    public List<Diluente> getAllDiluentes() {
        log.debug("REST request to get all Diluentes");
        return diluenteRepository.findAll();
    }

    /**
     * {@code GET  /diluentes/:id} : get the "id" diluente.
     *
     * @param id the id of the diluente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diluente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diluentes/{id}")
    public ResponseEntity<Diluente> getDiluente(@PathVariable Long id) {
        log.debug("REST request to get Diluente : {}", id);
        Optional<Diluente> diluente = diluenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diluente);
    }

    /**
     * {@code DELETE  /diluentes/:id} : delete the "id" diluente.
     *
     * @param id the id of the diluente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diluentes/{id}")
    public ResponseEntity<Void> deleteDiluente(@PathVariable Long id) {
        log.debug("REST request to delete Diluente : {}", id);
        diluenteRepository.deleteById(id);
        diluenteSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/diluentes?query=:query} : search for the diluente corresponding
     * to the query.
     *
     * @param query the query of the diluente search.
     * @return the result of the search.
     */
    @GetMapping("/_search/diluentes")
    public List<Diluente> searchDiluentes(@RequestParam String query) {
        log.debug("REST request to search Diluentes for query {}", query);
        return StreamSupport
            .stream(diluenteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
