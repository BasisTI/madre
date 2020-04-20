package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;

import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Triagem.
 */
@RestController
@RequestMapping("/api")
public class TriagemResource {

    private final Logger log = LoggerFactory.getLogger(TriagemResource.class);

    private static final String ENTITY_NAME = "pacientesTriagem";

    private final TriagemRepository triagemRepository;

    private final TriagemSearchRepository triagemSearchRepository;
    private String applicationName;

    public TriagemResource(TriagemRepository triagemRepository, TriagemSearchRepository triagemSearchRepository) {
        this.triagemRepository = triagemRepository;
        this.triagemSearchRepository = triagemSearchRepository;
    }

    /**
     * POST  /triagems : Create a new triagem.
     *
     * @param triagem the triagem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new triagem, or with status 400 (Bad Request) if the triagem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/triagems")
    @Timed
    public ResponseEntity<Triagem> createTriagem(@Valid @RequestBody Triagem triagem) throws URISyntaxException {
        log.debug("REST request to save Triagem : {}", triagem);
        if (triagem.getId() != null) {
            throw new BadRequestAlertException("A new triagem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Triagem result = triagemRepository.save(triagem);
        triagemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/triagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /triagems : Updates an existing triagem.
     *
     * @param triagem the triagem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated triagem,
     * or with status 400 (Bad Request) if the triagem is not valid,
     * or with status 500 (Internal Server Error) if the triagem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/triagems")
    @Timed
    public ResponseEntity<Triagem> updateTriagem(@Valid @RequestBody Triagem triagem) throws URISyntaxException {
        log.debug("REST request to update Triagem : {}", triagem);
        if (triagem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Triagem result = triagemRepository.save(triagem);
        triagemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, triagem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /triagems : get all the triagems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of triagems in body
     */
    @GetMapping("/triagems")
    @Timed
    public List<Triagem> getAllTriagems() {
        log.debug("REST request to get all Triagems");
        return triagemRepository.findAll();
    }

    /**
     * GET  /triagems/:id : get the "id" triagem.
     *
     * @param id the id of the triagem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the triagem, or with status 404 (Not Found)
     */
    @GetMapping("/triagems/{id}")
    @Timed
    public ResponseEntity<Triagem> getTriagem(@PathVariable Long id) {
        log.debug("REST request to get Triagem : {}", id);
        Optional<Triagem> triagem = triagemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(triagem);
    }

    /**
     * DELETE  /triagems/:id : delete the "id" triagem.
     *
     * @param id the id of the triagem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/triagems/{id}")
    @Timed
    public ResponseEntity<Void> deleteTriagem(@PathVariable Long id) {
        log.debug("REST request to delete Triagem : {}", id);

        triagemRepository.deleteById(id);
        triagemSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/triagems?query=:query : search for the triagem corresponding
     * to the query.
     *
     * @param query the query of the triagem search
     * @return the result of the search
     */
    @GetMapping("/_search/triagems")
    @Timed
    public List<Triagem> searchTriagems(@RequestParam String query) {
        log.debug("REST request to search Triagems for query {}", query);
        return StreamSupport
            .stream(triagemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
