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

import br.com.basis.madre.cadastros.domain.Acao;
import br.com.basis.madre.cadastros.service.AcaoService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Acao.
 */
@RestController
@RequestMapping("/api")
public class AcaoResource {

    private final Logger log = LoggerFactory.getLogger(AcaoResource.class);

    private static final String ENTITY_NAME = "acao";

    private final AcaoService acaoService;

    public AcaoResource(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    /**
     * POST  /acaos : Create a new acao.
     *
     * @param acao the acao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acao, or with status 400 (Bad Request) if the acao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acaos")
    @Timed
    public ResponseEntity<Acao> createAcao(@Valid @RequestBody Acao acao) throws URISyntaxException {
        log.debug("REST request to save Acao : {}", acao);
        acao.setNmAcao("pesquisar");
        acao.setNmAcao("incluir");
        acao.setNmAcao("excluir");
        if (acao.getId() != null) {
            throw new BadRequestAlertException("A new acao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acao result = acaoService.save(acao);
        return ResponseEntity.created(new URI("/api/acaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acaos : Updates an existing acao.
     *
     * @param acao the acao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acao,
     * or with status 400 (Bad Request) if the acao is not valid,
     * or with status 500 (Internal Server Error) if the acao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acaos")
    @Timed
    public ResponseEntity<Acao> updateAcao(@Valid @RequestBody Acao acao) throws URISyntaxException {
        log.debug("REST request to update Acao : {}", acao);
        if (acao.getId() == null) {
            return createAcao(acao);
        }
        Acao result = acaoService.save(acao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acaos : get all the acaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of acaos in body
     */
    @GetMapping("/acaos")
    @Timed
    public ResponseEntity<List<Acao>> getAllAcaos(Pageable pageable) {
        log.debug("REST request to get a page of Acaos");
        Page<Acao> page = acaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/acaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /acaos/:id : get the "id" acao.
     *
     * @param id the id of the acao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acao, or with status 404 (Not Found)
     */
    @GetMapping("/acaos/{id}")
    @Timed
    public ResponseEntity<Acao> getAcao(@PathVariable Long id) {
        log.debug("REST request to get Acao : {}", id);
        Acao acao = acaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acao));
    }

    /**
     * DELETE  /acaos/:id : delete the "id" acao.
     *
     * @param id the id of the acao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcao(@PathVariable Long id) {
        log.debug("REST request to delete Acao : {}", id);
        acaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/acaos?query=:query : search for the acao corresponding
     * to the query.
     *
     * @param query the query of the acao search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/acaos")
    @Timed
    public ResponseEntity<List<Acao>> searchAcaos(@RequestParam(defaultValue = "*") String query, Pageable pageable) {
        log.debug("REST request to search for a page of Acaos for query {}", query);
        Page<Acao> page = acaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/acaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
