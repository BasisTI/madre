package br.com.basis.madre.cadastros.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.service.PreCadastroService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
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
 * REST controller for managing PreCadastro.
 */
@RestController
@RequestMapping("/api")
public class PreCadastroResource {

    private final Logger log = LoggerFactory.getLogger(PreCadastroResource.class);

    private static final String ENTITY_NAME = "preCadastro";

    private final PreCadastroService preCadastroService;

    public PreCadastroResource(PreCadastroService preCadastroService) {
        this.preCadastroService = preCadastroService;
    }

    /**
     * POST  /pre-cadastros : Create a new preCadastro.
     *
     * @param preCadastro the preCadastro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preCadastro, or with status 400 (Bad Request) if the preCadastro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pre-cadastros")
    @Timed
    public ResponseEntity<PreCadastro> createPreCadastro(@Valid @RequestBody PreCadastro preCadastro) throws URISyntaxException {
        log.debug("REST request to save PreCadastro : {}", preCadastro);
        if (preCadastro.getId() != null) {
            throw new BadRequestAlertException("A new preCadastro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreCadastro result = preCadastroService.save(preCadastro);
        return ResponseEntity.created(new URI("/api/pre-cadastros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pre-cadastros : Updates an existing preCadastro.
     *
     * @param preCadastro the preCadastro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preCadastro,
     * or with status 400 (Bad Request) if the preCadastro is not valid,
     * or with status 500 (Internal Server Error) if the preCadastro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pre-cadastros")
    @Timed
    public ResponseEntity<PreCadastro> updatePreCadastro(@Valid @RequestBody PreCadastro preCadastro) throws URISyntaxException {
        log.debug("REST request to update PreCadastro : {}", preCadastro);
        if (preCadastro.getId() == null) {
            return createPreCadastro(preCadastro);
        }
        PreCadastro result = preCadastroService.save(preCadastro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preCadastro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pre-cadastros : get all the preCadastros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of preCadastros in body
     */
    @GetMapping("/pre-cadastros")
    @Timed
    public ResponseEntity<List<PreCadastro>> getAllPreCadastros(Pageable pageable) {
        log.debug("REST request to get a page of PreCadastros");
        Page<PreCadastro> page = preCadastroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pre-cadastros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pre-cadastros/:id : get the "id" preCadastro.
     *
     * @param id the id of the preCadastro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preCadastro, or with status 404 (Not Found)
     */
    @GetMapping("/pre-cadastros/{id}")
    @Timed
    public ResponseEntity<PreCadastro> getPreCadastro(@PathVariable Long id) {
        log.debug("REST request to get PreCadastro : {}", id);
        PreCadastro preCadastro = preCadastroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preCadastro));
    }

    /**
     * DELETE  /pre-cadastros/:id : delete the "id" preCadastro.
     *
     * @param id the id of the preCadastro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pre-cadastros/{id}")
    @Timed
    public ResponseEntity<Void> deletePreCadastro(@PathVariable Long id) {
        log.debug("REST request to delete PreCadastro : {}", id);
        preCadastroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pre-cadastros?query=:query : search for the preCadastro corresponding
     * to the query.
     *
     * @param query the query of the preCadastro search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pre-cadastros")
    @Timed
    public ResponseEntity<List<PreCadastro>> searchPreCadastros(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PreCadastros for query {}", query);
        Page<PreCadastro> page = preCadastroService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pre-cadastros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
