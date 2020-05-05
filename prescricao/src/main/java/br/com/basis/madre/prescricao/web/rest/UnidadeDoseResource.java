package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.domain.UnidadeDose;
import br.com.basis.madre.prescricao.repository.UnidadeDoseRepository;
import br.com.basis.madre.prescricao.repository.search.UnidadeDoseSearchRepository;
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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.UnidadeDose}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeDoseResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeDoseResource.class);

    private static final String ENTITY_NAME = "prescricaoUnidadeDose";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeDoseRepository unidadeDoseRepository;

    private final UnidadeDoseSearchRepository unidadeDoseSearchRepository;

    public UnidadeDoseResource(UnidadeDoseRepository unidadeDoseRepository, UnidadeDoseSearchRepository unidadeDoseSearchRepository) {
        this.unidadeDoseRepository = unidadeDoseRepository;
        this.unidadeDoseSearchRepository = unidadeDoseSearchRepository;
    }

    /**
     * {@code POST  /unidade-doses} : Create a new unidadeDose.
     *
     * @param unidadeDose the unidadeDose to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeDose, or with status {@code 400 (Bad Request)} if the unidadeDose has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-doses")
    public ResponseEntity<UnidadeDose> createUnidadeDose(@Valid @RequestBody UnidadeDose unidadeDose) throws URISyntaxException {
        log.debug("REST request to save UnidadeDose : {}", unidadeDose);
        if (unidadeDose.getId() != null) {
            throw new BadRequestAlertException("A new unidadeDose cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeDose result = unidadeDoseRepository.save(unidadeDose);
        unidadeDoseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/unidade-doses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-doses} : Updates an existing unidadeDose.
     *
     * @param unidadeDose the unidadeDose to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeDose,
     * or with status {@code 400 (Bad Request)} if the unidadeDose is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeDose couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-doses")
    public ResponseEntity<UnidadeDose> updateUnidadeDose(@Valid @RequestBody UnidadeDose unidadeDose) throws URISyntaxException {
        log.debug("REST request to update UnidadeDose : {}", unidadeDose);
        if (unidadeDose.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeDose result = unidadeDoseRepository.save(unidadeDose);
        unidadeDoseSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeDose.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-doses} : get all the unidadeDoses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeDoses in body.
     */
    @GetMapping("/unidade-doses")
    public List<UnidadeDose> getAllUnidadeDoses() {
        log.debug("REST request to get all UnidadeDoses");
        return unidadeDoseRepository.findAll();
    }

    /**
     * {@code GET  /unidade-doses/:id} : get the "id" unidadeDose.
     *
     * @param id the id of the unidadeDose to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeDose, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-doses/{id}")
    public ResponseEntity<UnidadeDose> getUnidadeDose(@PathVariable Long id) {
        log.debug("REST request to get UnidadeDose : {}", id);
        Optional<UnidadeDose> unidadeDose = unidadeDoseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidadeDose);
    }

    /**
     * {@code DELETE  /unidade-doses/:id} : delete the "id" unidadeDose.
     *
     * @param id the id of the unidadeDose to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-doses/{id}")
    public ResponseEntity<Void> deleteUnidadeDose(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeDose : {}", id);
        unidadeDoseRepository.deleteById(id);
        unidadeDoseSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-doses?query=:query} : search for the unidadeDose corresponding
     * to the query.
     *
     * @param query the query of the unidadeDose search.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-doses")
    public List<UnidadeDose> searchUnidadeDoses(@RequestParam String query) {
        log.debug("REST request to search UnidadeDoses for query {}", query);
        return StreamSupport
            .stream(unidadeDoseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
