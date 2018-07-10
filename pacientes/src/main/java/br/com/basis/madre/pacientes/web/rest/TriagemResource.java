package br.com.basis.madre.pacientes.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.pacientes.domain.Triagem;
import br.com.basis.madre.pacientes.service.TriagemService;
import br.com.basis.madre.pacientes.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.pacientes.web.rest.util.HeaderUtil;
import br.com.basis.madre.pacientes.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Triagem.
 */
@RestController
@RequestMapping("/api")
public class TriagemResource {

    private final Logger log = LoggerFactory.getLogger(TriagemResource.class);

    private static final String ENTITY_NAME = "triagem";

    private final TriagemService triagemService;

    public TriagemResource(TriagemService triagemService) {
        this.triagemService = triagemService;
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
        Triagem result = triagemService.save(triagem);
        return ResponseEntity.created(new URI("/api/triagems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
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
            return createTriagem(triagem);
        }
        Triagem result = triagemService.save(triagem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, triagem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /triagems : get all the triagems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of triagems in body
     */
    @GetMapping("/triagems")
    @Timed
    public ResponseEntity<List<Triagem>> getAllTriagems(Pageable pageable) {
        log.debug("REST request to get a page of Triagems");
        Page<Triagem> page = triagemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/triagems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        Triagem triagem = triagemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(triagem));
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
        triagemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/triagems?query=:query : search for the triagem corresponding
     * to the query.
     *
     * @param query the query of the triagem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/triagems")
    @Timed
    public ResponseEntity<List<Triagem>> searchTriagems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Triagems for query {}", query);
        Page<Triagem> page = triagemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/triagems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
