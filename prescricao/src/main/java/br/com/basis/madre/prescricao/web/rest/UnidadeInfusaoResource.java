package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import br.com.basis.madre.prescricao.repository.UnidadeInfusaoRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeInfusaoSearchRepository;
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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.UnidadeInfusao}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeInfusaoResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeInfusaoResource.class);

    private static final String ENTITY_NAME = "prescricaoUnidadeInfusao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeInfusaoRepository unidadeInfusaoRepository;

    private final UnidadeInfusaoSearchRepository unidadeInfusaoSearchRepository;

    public UnidadeInfusaoResource(UnidadeInfusaoRepository unidadeInfusaoRepository, UnidadeInfusaoSearchRepository unidadeInfusaoSearchRepository) {
        this.unidadeInfusaoRepository = unidadeInfusaoRepository;
        this.unidadeInfusaoSearchRepository = unidadeInfusaoSearchRepository;
    }

    /**
     * {@code POST  /unidade-infusaos} : Create a new unidadeInfusao.
     *
     * @param unidadeInfusao the unidadeInfusao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeInfusao, or with status {@code 400 (Bad Request)} if the unidadeInfusao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-infusaos")
    public ResponseEntity<UnidadeInfusao> createUnidadeInfusao(@Valid @RequestBody UnidadeInfusao unidadeInfusao) throws URISyntaxException {
        log.debug("REST request to save UnidadeInfusao : {}", unidadeInfusao);
        if (unidadeInfusao.getId() != null) {
            throw new BadRequestAlertException("A new unidadeInfusao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeInfusao result = unidadeInfusaoRepository.save(unidadeInfusao);
        unidadeInfusaoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/unidade-infusaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-infusaos} : Updates an existing unidadeInfusao.
     *
     * @param unidadeInfusao the unidadeInfusao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeInfusao,
     * or with status {@code 400 (Bad Request)} if the unidadeInfusao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeInfusao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-infusaos")
    public ResponseEntity<UnidadeInfusao> updateUnidadeInfusao(@Valid @RequestBody UnidadeInfusao unidadeInfusao) throws URISyntaxException {
        log.debug("REST request to update UnidadeInfusao : {}", unidadeInfusao);
        if (unidadeInfusao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeInfusao result = unidadeInfusaoRepository.save(unidadeInfusao);
        unidadeInfusaoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeInfusao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-infusaos} : get all the unidadeInfusaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeInfusaos in body.
     */
    @GetMapping("/unidade-infusaos")
    public List<UnidadeInfusao> getAllUnidadeInfusaos() {
        log.debug("REST request to get all UnidadeInfusaos");
        return unidadeInfusaoRepository.findAll();
    }

    /**
     * {@code GET  /unidade-infusaos/:id} : get the "id" unidadeInfusao.
     *
     * @param id the id of the unidadeInfusao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeInfusao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-infusaos/{id}")
    public ResponseEntity<UnidadeInfusao> getUnidadeInfusao(@PathVariable Long id) {
        log.debug("REST request to get UnidadeInfusao : {}", id);
        Optional<UnidadeInfusao> unidadeInfusao = unidadeInfusaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidadeInfusao);
    }

    /**
     * {@code DELETE  /unidade-infusaos/:id} : delete the "id" unidadeInfusao.
     *
     * @param id the id of the unidadeInfusao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-infusaos/{id}")
    public ResponseEntity<Void> deleteUnidadeInfusao(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeInfusao : {}", id);
        unidadeInfusaoRepository.deleteById(id);
        unidadeInfusaoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-infusaos?query=:query} : search for the unidadeInfusao corresponding
     * to the query.
     *
     * @param query the query of the unidadeInfusao search.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-infusaos")
    public List<UnidadeInfusao> searchUnidadeInfusaos(@RequestParam String query) {
        log.debug("REST request to search UnidadeInfusaos for query {}", query);
        return StreamSupport
            .stream(unidadeInfusaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
