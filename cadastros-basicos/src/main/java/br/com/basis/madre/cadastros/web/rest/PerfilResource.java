package br.com.basis.madre.cadastros.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.service.PerfilService;
import br.com.basis.madre.cadastros.util.MadreUtil;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Perfil.
 */
@RestController
@RequestMapping("/api")
public class PerfilResource {

    private final Logger log = LoggerFactory.getLogger(PerfilResource.class);

    private static final String ENTITY_NAME = "perfil";

    private final PerfilService perfilService;

    private final PerfilRepository perfilRepository;

    public PerfilResource(PerfilService perfilService, PerfilRepository perfilRepository) {
        this.perfilService = perfilService;
        this.perfilRepository = perfilRepository;
    }

    /**
     * POST  /perfils : Create a new perfil.
     *
     * @param perfil the perfil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perfil, or with status 400 (Bad Request) if the perfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/perfils")
    @Timed
    public ResponseEntity<Perfil> createPerfil(@Valid @RequestBody Perfil perfil) throws URISyntaxException {
        log.debug("REST request to save Perfil : {}", perfil);
        perfil.setNomePerfil(MadreUtil.removeCaracteresEmBranco(perfil.getNomePerfil()));
        if (perfil.getId() != null) {
            throw new BadRequestAlertException("A new perfil cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (perfilRepository.findOneByNomePerfilIgnoreCase(MadreUtil.removeCaracteresEmBranco(perfil.getNomePerfil())).isPresent()){
            throw new BadRequestAlertException("Perfil já cadastrado", ENTITY_NAME, "perfilexists");
        }

        Perfil result = perfilService.save(perfil);
        return ResponseEntity.created(new URI("/api/perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /perfils : Updates an existing perfil.
     *
     * @param perfil the perfil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perfil,
     * or with status 400 (Bad Request) if the perfil is not valid,
     * or with status 500 (Internal Server Error) if the perfil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/perfils")
    @Timed
    public ResponseEntity<Perfil> updatePerfil(@Valid @RequestBody Perfil perfil) throws URISyntaxException {
        log.debug("REST request to update Perfil : {}", perfil);
        perfil.setNomePerfil(MadreUtil.removeCaracteresEmBranco(perfil.getNomePerfil()));
        if (perfil.getId() == null) {
            return createPerfil(perfil);
        }

        if (perfilRepository.findOneByNomePerfilIgnoreCase(MadreUtil.removeCaracteresEmBranco(perfil.getNomePerfil())).isPresent()){
            throw new BadRequestAlertException("Perfil já cadastrado", ENTITY_NAME, "perfilexists");
        }

        Perfil result = perfilService.save(perfil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perfil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /perfils : get all the perfils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of perfils in body
     */
    @GetMapping("/perfils")
    @Timed
    public ResponseEntity<List<Perfil>> getAllPerfils(Pageable pageable) {
        log.debug("REST request to get a page of Perfils");
        Page<Perfil> page = perfilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/perfils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /perfils/:id : get the "id" perfil.
     *
     * @param id the id of the perfil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perfil, or with status 404 (Not Found)
     */
    @GetMapping("/perfils/{id}")
    @Timed
    public ResponseEntity<Perfil> getPerfil(@PathVariable Long id) {
        log.debug("REST request to get Perfil : {}", id);
        Perfil perfil = perfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perfil));
    }

    /**
     * DELETE  /perfils/:id : delete the "id" perfil.
     *
     * @param id the id of the perfil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/perfils/{id}")
    @Timed
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        log.debug("REST request to delete Perfil : {}", id);
        perfilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/perfils?query=:query : search for the perfil corresponding
     * to the query.
     *
     * @param query the query of the perfil search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/perfils")
    @Timed
    public ResponseEntity<List<Perfil>> searchPerfils(@RequestParam(defaultValue = "*") String query, Pageable pageable) {
        log.debug("REST request to search for a page of Perfils for query {}", query);
        Page<Perfil> page = perfilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/perfils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/perfil/exportacao/{tipoRelatorio}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public ResponseEntity<InputStreamResource> getRelatorioExportacao (@PathVariable String tipoRelatorio,@RequestParam (defaultValue = "*")String query){
            try {
                return perfilService.gerarRelatorioExportacao(tipoRelatorio,query);
            } catch (RelatorioException e){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, RelatorioException.getCodeEntidade(), e.getMessage())).body(null);
            }
    }

}
