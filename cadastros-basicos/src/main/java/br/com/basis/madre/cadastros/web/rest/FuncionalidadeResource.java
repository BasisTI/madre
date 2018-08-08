package br.com.basis.madre.cadastros.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.codahale.metrics.annotation.Timed;

import br.com.basis.madre.cadastros.domain.Funcionalidade;
import br.com.basis.madre.cadastros.service.FuncionalidadeService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Funcionalidade.
 */
@RestController
@RequestMapping("/api")
public class FuncionalidadeResource {

    private final Logger log = LoggerFactory.getLogger(FuncionalidadeResource.class);

    private static final String ENTITY_NAME = "funcionalidade";

    private final FuncionalidadeService funcionalidadeService;

    public FuncionalidadeResource(FuncionalidadeService funcionalidadeService) {
        this.funcionalidadeService = funcionalidadeService;
    }

    /**
     * POST  /funcionalidades : Create a new funcionalidade.
     *
     * @param funcionalidade the funcionalidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new funcionalidade, or with status 400 (Bad Request) if the funcionalidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/funcionalidades")
    @Timed
    public ResponseEntity<Funcionalidade> createFuncionalidade(@Valid @RequestBody Funcionalidade funcionalidade) throws URISyntaxException {
        log.debug("REST request to save Funcionalidade : {}", funcionalidade);
        if (funcionalidade.getId() != null) {
            throw new BadRequestAlertException("A new funcionalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Funcionalidade result = funcionalidadeService.save(funcionalidade);
        return ResponseEntity.created(new URI("/api/funcionalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /funcionalidades : Updates an existing funcionalidade.
     *
     * @param funcionalidade the funcionalidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated funcionalidade,
     * or with status 400 (Bad Request) if the funcionalidade is not valid,
     * or with status 500 (Internal Server Error) if the funcionalidade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/funcionalidades")
    @Timed
    public ResponseEntity<Funcionalidade> updateFuncionalidade(@Valid @RequestBody Funcionalidade funcionalidade) throws URISyntaxException {
        log.debug("REST request to update Funcionalidade : {}", funcionalidade);
        if (funcionalidade.getId() == null) {
            return createFuncionalidade(funcionalidade);
        }
        Funcionalidade result = funcionalidadeService.save(funcionalidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, funcionalidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /funcionalidades : get all the funcionalidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of funcionalidades in body
     */
    @GetMapping("/funcionalidades")
    @Timed
    public ResponseEntity<List<Funcionalidade>> getAllFuncionalidades(Pageable pageable) {
        log.debug("REST request to get a page of Funcionalidades");
        Page<Funcionalidade> page = funcionalidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/funcionalidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /funcionalidades/:id : get the "id" funcionalidade.
     *
     * @param id the id of the funcionalidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the funcionalidade, or with status 404 (Not Found)
     */
    @GetMapping("/funcionalidades/{id}")
    @Timed
    public ResponseEntity<Funcionalidade> getFuncionalidade(@PathVariable Long id) {
        log.debug("REST request to get Funcionalidade : {}", id);
        Funcionalidade funcionalidade = funcionalidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(funcionalidade));
    }

    /**
     * DELETE  /funcionalidades/:id : delete the "id" funcionalidade.
     *
     * @param id the id of the funcionalidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/funcionalidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteFuncionalidade(@PathVariable Long id) {
        log.debug("REST request to delete Funcionalidade : {}", id);
        funcionalidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/funcionalidades?query=:query : search for the funcionalidade corresponding
     * to the query.
     *
     * @param query    the query of the funcionalidade search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/funcionalidades")
    @Timed
    public ResponseEntity<List<Funcionalidade>> searchFuncionalidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Funcionalidades for query {}", query);
        Page<Funcionalidade> page = funcionalidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/funcionalidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
