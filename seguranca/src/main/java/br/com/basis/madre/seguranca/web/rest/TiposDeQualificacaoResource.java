package br.com.basis.madre.seguranca.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.basis.madre.seguranca.service.TiposDeQualificacaoService;
import br.com.basis.madre.seguranca.service.dto.TiposDeQualificacaoDTO;
import br.com.basis.madre.seguranca.service.projection.TiposDeQualificacaoResumo;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.TiposDeQualificacao}.
 */
@RestController
@RequestMapping("/api")
public class TiposDeQualificacaoResource {

    private final Logger log = LoggerFactory.getLogger(TiposDeQualificacaoResource.class);

    private static final String ENTITY_NAME = "madresegurancaTiposDeQualificacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TiposDeQualificacaoService tiposDeQualificacaoService;

    public TiposDeQualificacaoResource(TiposDeQualificacaoService tiposDeQualificacaoService) {
        this.tiposDeQualificacaoService = tiposDeQualificacaoService;
    }

    /**
     * {@code POST  /tipos-de-qualificacaos} : Create a new tiposDeQualificacao.
     *
     * @param tiposDeQualificacaoDTO the tiposDeQualificacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tiposDeQualificacaoDTO, or with status {@code 400 (Bad Request)} if the tiposDeQualificacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipos-de-qualificacaos")
    public ResponseEntity<TiposDeQualificacaoDTO> createTiposDeQualificacao(@Valid @RequestBody TiposDeQualificacaoDTO tiposDeQualificacaoDTO) throws URISyntaxException {
        log.debug("REST request to save TiposDeQualificacao : {}", tiposDeQualificacaoDTO);
        if (tiposDeQualificacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tiposDeQualificacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TiposDeQualificacaoDTO result = tiposDeQualificacaoService.save(tiposDeQualificacaoDTO);
        return ResponseEntity.created(new URI("/api/tipos-de-qualificacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipos-de-qualificacaos} : Updates an existing tiposDeQualificacao.
     *
     * @param tiposDeQualificacaoDTO the tiposDeQualificacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tiposDeQualificacaoDTO,
     * or with status {@code 400 (Bad Request)} if the tiposDeQualificacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tiposDeQualificacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipos-de-qualificacaos")
    public ResponseEntity<TiposDeQualificacaoDTO> updateTiposDeQualificacao(@Valid @RequestBody TiposDeQualificacaoDTO tiposDeQualificacaoDTO) throws URISyntaxException {
        log.debug("REST request to update TiposDeQualificacao : {}", tiposDeQualificacaoDTO);
        if (tiposDeQualificacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TiposDeQualificacaoDTO result = tiposDeQualificacaoService.save(tiposDeQualificacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tiposDeQualificacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipos-de-qualificacaos} : get all the tiposDeQualificacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tiposDeQualificacaos in body.
     */
    @GetMapping("/tipos-de-qualificacaos")
    public ResponseEntity<List<TiposDeQualificacaoDTO>> getAllTiposDeQualificacaos(Pageable pageable) {
        log.debug("REST request to get a page of TiposDeQualificacaos");
        Page<TiposDeQualificacaoDTO> page = tiposDeQualificacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipos-de-qualificacaos/:id} : get the "id" tiposDeQualificacao.
     *
     * @param id the id of the tiposDeQualificacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tiposDeQualificacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipos-de-qualificacaos/{id}")
    public ResponseEntity<TiposDeQualificacaoDTO> getTiposDeQualificacao(@PathVariable Long id) {
        log.debug("REST request to get TiposDeQualificacao : {}", id);
        Optional<TiposDeQualificacaoDTO> tiposDeQualificacaoDTO = tiposDeQualificacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tiposDeQualificacaoDTO);
    }

    /**
     * {@code DELETE  /tipos-de-qualificacaos/:id} : delete the "id" tiposDeQualificacao.
     *
     * @param id the id of the tiposDeQualificacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipos-de-qualificacaos/{id}")
    public ResponseEntity<Void> deleteTiposDeQualificacao(@PathVariable Long id) {
        log.debug("REST request to delete TiposDeQualificacao : {}", id);
        tiposDeQualificacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipos-de-qualificacaos?query=:query} : search for the tiposDeQualificacao corresponding
     * to the query.
     *
     * @param query the query of the tiposDeQualificacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipos-de-qualificacaos")
    public ResponseEntity<List<TiposDeQualificacaoDTO>> searchTiposDeQualificacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TiposDeQualificacaos for query {}", query);
        Page<TiposDeQualificacaoDTO> page = tiposDeQualificacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tipos-de-qualificacaos/_resumo")
    public ResponseEntity<Page<TiposDeQualificacaoResumo>> findAllProjectedTiposDeQualificacaoResumoBy(@RequestParam(required = false,defaultValue = "") String descricao, Pageable pageable) {
        return ResponseEntity.ok(tiposDeQualificacaoService.findAllProjectedTiposDeQualificacaoBy(descricao,pageable));
    }


}
