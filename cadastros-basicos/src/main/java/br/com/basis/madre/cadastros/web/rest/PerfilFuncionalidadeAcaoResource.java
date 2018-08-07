package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.PerfilFuncionalidadeAcao;
import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.cadastros.service.PerfilFuncionalidadeAcaoService;
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

/**
 * REST controller for managing PerfilFuncionalidadeAcao.
 */
@RestController
@RequestMapping("/api")
public class PerfilFuncionalidadeAcaoResource {

    private final Logger log = LoggerFactory.getLogger(PerfilFuncionalidadeAcaoResource.class);

    private static final String ENTITY_NAME = "perfil_funcionalidade_acao";

    private final PerfilFuncionalidadeAcaoService perfilFuncionalidadeAcaoService;

    public PerfilFuncionalidadeAcaoResource(PerfilFuncionalidadeAcaoService perfilFuncionalidadeAcaoService) {
        this.perfilFuncionalidadeAcaoService = perfilFuncionalidadeAcaoService;
    }

    /**
     * POST  /perfil-funcionalidade-acaos : Create a new perfil_funcionalidade_acao.
     *
     * @param perfilFuncionalidadeAcao the perfil_funcionalidade_acao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perfil_funcionalidade_acao, or with status 400 (Bad Request) if the perfil_funcionalidade_acao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/perfil-funcionalidade-acaos")
    @Timed
    public ResponseEntity<PerfilFuncionalidadeAcao> createPerfilFuncionalidadeAcao(@Valid @RequestBody PerfilFuncionalidadeAcao perfilFuncionalidadeAcao) throws URISyntaxException {
        log.debug("REST request to save PerfilFuncionalidadeAcao : {}", perfilFuncionalidadeAcao);
        if (perfilFuncionalidadeAcao.getId() != null) {
            throw new BadRequestAlertException("A new perfil_funcionalidade_acao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfilFuncionalidadeAcao result = perfilFuncionalidadeAcaoService.save(perfilFuncionalidadeAcao);
        return ResponseEntity.created(new URI("/api/perfil-funcionalidade-acaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /perfil-funcionalidade-acaos : Updates an existing perfil_funcionalidade_acao.
     *
     * @param perfilFuncionalidadeAcao the perfil_funcionalidade_acao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perfil_funcionalidade_acao,
     * or with status 400 (Bad Request) if the perfil_funcionalidade_acao is not valid,
     * or with status 500 (Internal Server Error) if the perfil_funcionalidade_acao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/perfil-funcionalidade-acaos")
    @Timed
    public ResponseEntity<PerfilFuncionalidadeAcao> updatePerfilFuncionalidadeAcao(@Valid @RequestBody PerfilFuncionalidadeAcao perfilFuncionalidadeAcao) throws URISyntaxException {
        log.debug("REST request to update PerfilFuncionalidadeAcao : {}", perfilFuncionalidadeAcao);
        if (perfilFuncionalidadeAcao.getId() == null) {
            return createPerfilFuncionalidadeAcao(perfilFuncionalidadeAcao);
        }
        PerfilFuncionalidadeAcao result = perfilFuncionalidadeAcaoService.save(perfilFuncionalidadeAcao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perfilFuncionalidadeAcao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /perfil-funcionalidade-acaos : get all the perfil_funcionalidade_acaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of perfil_funcionalidade_acaos in body
     */
    @GetMapping("/perfil-funcionalidade-acaos")
    @Timed
    public ResponseEntity<List<PerfilFuncionalidadeAcao>> getAllPerfilFuncionalidadeAcaos(Pageable pageable) {
        log.debug("REST request to get a page of Perfil_funcionalidade_acaos");
        Page<PerfilFuncionalidadeAcao> page = perfilFuncionalidadeAcaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/perfil-funcionalidade-acaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /perfil-funcionalidade-acaos/:id : get the "id" perfil_funcionalidade_acao.
     *
     * @param id the id of the perfil_funcionalidade_acao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perfil_funcionalidade_acao, or with status 404 (Not Found)
     */
    @GetMapping("/perfil-funcionalidade-acaos/{id}")
    @Timed
    public ResponseEntity<PerfilFuncionalidadeAcao> getPerfilFuncionalidadeAcao(@PathVariable Long id) {
        log.debug("REST request to get PerfilFuncionalidadeAcao : {}", id);
        PerfilFuncionalidadeAcao perfilFuncionalidadeAcao = perfilFuncionalidadeAcaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perfilFuncionalidadeAcao));
    }

    /**
     * DELETE  /perfil-funcionalidade-acaos/:id : delete the "id" perfil_funcionalidade_acao.
     *
     * @param id the id of the perfil_funcionalidade_acao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/perfil-funcionalidade-acaos/{id}")
    @Timed
    public ResponseEntity<Void> deletePerfilFuncionalidadeAcao(@PathVariable Long id) {
        log.debug("REST request to delete PerfilFuncionalidadeAcao : {}", id);
        perfilFuncionalidadeAcaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/perfil-funcionalidade-acaos?query=:query : search for the perfil_funcionalidade_acao corresponding
     * to the query.
     *
     * @param query the query of the perfil_funcionalidade_acao search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/perfil-funcionalidade-acaos")
    @Timed
    public ResponseEntity<List<PerfilFuncionalidadeAcao>> searchPerfilFuncionalidadeAcaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Perfil_funcionalidade_acaos for query {}", query);
        Page<PerfilFuncionalidadeAcao> page = perfilFuncionalidadeAcaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/perfil-funcionalidade-acaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
