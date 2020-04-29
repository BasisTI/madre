package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.SolicitacaoDeInternacaoService;
import br.com.basis.madre.service.dto.SolicitacaoDeInternacaoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SolicitacaoDeInternacaoResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoDeInternacaoResource.class);

    private static final String ENTITY_NAME = "internacaoSolicitacaoDeInternacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoDeInternacaoService solicitacaoDeInternacaoService;

    /**
     * {@code POST  /solicitacao-de-internacaos} : Create a new solicitacaoDeInternacao.
     *
     * @param solicitacaoDeInternacaoDTO the solicitacaoDeInternacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * solicitacaoDeInternacaoDTO, or with status {@code 400 (Bad Request)} if the
     * solicitacaoDeInternacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacoes-de-internacao")
    public ResponseEntity<SolicitacaoDeInternacaoDTO> createSolicitacaoDeInternacao(
        @Valid @RequestBody SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to save SolicitacaoDeInternacao : {}", solicitacaoDeInternacaoDTO);
        if (solicitacaoDeInternacaoDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new solicitacaoDeInternacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoDeInternacaoDTO result = solicitacaoDeInternacaoService
            .save(solicitacaoDeInternacaoDTO);
        return ResponseEntity.created(new URI("/api/solicitacao-de-internacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacao-de-internacaos} : Updates an existing solicitacaoDeInternacao.
     *
     * @param solicitacaoDeInternacaoDTO the solicitacaoDeInternacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * solicitacaoDeInternacaoDTO, or with status {@code 400 (Bad Request)} if the
     * solicitacaoDeInternacaoDTO is not valid, or with status {@code 500 (Internal Server Error)}
     * if the solicitacaoDeInternacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacoes-de-internacao")
    public ResponseEntity<SolicitacaoDeInternacaoDTO> updateSolicitacaoDeInternacao(
        @Valid @RequestBody SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to update SolicitacaoDeInternacao : {}",
            solicitacaoDeInternacaoDTO);
        if (solicitacaoDeInternacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitacaoDeInternacaoDTO result = solicitacaoDeInternacaoService
            .save(solicitacaoDeInternacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                solicitacaoDeInternacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicitacao-de-internacaos} : get all the solicitacaoDeInternacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * solicitacaoDeInternacaos in body.
     */
    @GetMapping("/solicitacoes-de-internacao")
    public ResponseEntity<List<SolicitacaoDeInternacaoDTO>> getAllSolicitacaoDeInternacaos(
        Pageable pageable) {
        log.debug("REST request to get a page of SolicitacaoDeInternacaos");
        Page<SolicitacaoDeInternacaoDTO> page = solicitacaoDeInternacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solicitacao-de-internacaos/:id} : get the "id" solicitacaoDeInternacao.
     *
     * @param id the id of the solicitacaoDeInternacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * solicitacaoDeInternacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacoes-de-internacao/{id}")
    public ResponseEntity<SolicitacaoDeInternacaoDTO> getSolicitacaoDeInternacao(
        @PathVariable Long id) {
        log.debug("REST request to get SolicitacaoDeInternacao : {}", id);
        Optional<SolicitacaoDeInternacaoDTO> solicitacaoDeInternacaoDTO = solicitacaoDeInternacaoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoDeInternacaoDTO);
    }

    /**
     * {@code DELETE  /solicitacao-de-internacaos/:id} : delete the "id" solicitacaoDeInternacao.
     *
     * @param id the id of the solicitacaoDeInternacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacoes-de-internacao/{id}")
    public ResponseEntity<Void> deleteSolicitacaoDeInternacao(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoDeInternacao : {}", id);
        solicitacaoDeInternacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/solicitacao-de-internacaos?query=:query} : search for the
     * solicitacaoDeInternacao corresponding to the query.
     *
     * @param query    the query of the solicitacaoDeInternacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/solicitacoes-de-internacao")
    public ResponseEntity<List<SolicitacaoDeInternacaoDTO>> searchSolicitacaoDeInternacaos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolicitacaoDeInternacaos for query {}",
            query);
        Page<SolicitacaoDeInternacaoDTO> page = solicitacaoDeInternacaoService
            .search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
