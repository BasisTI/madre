package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.AutorizacaoFornecimentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.AutorizacaoFornecimentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.AutorizacaoFornecimento}.
 */
@RestController
@RequestMapping("/api")
public class AutorizacaoFornecimentoResource {

    private final Logger log = LoggerFactory.getLogger(AutorizacaoFornecimentoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosAutorizacaoFornecimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutorizacaoFornecimentoService autorizacaoFornecimentoService;

    public AutorizacaoFornecimentoResource(AutorizacaoFornecimentoService autorizacaoFornecimentoService) {
        this.autorizacaoFornecimentoService = autorizacaoFornecimentoService;
    }

    /**
     * {@code POST  /autorizacao-fornecimentos} : Create a new autorizacaoFornecimento.
     *
     * @param autorizacaoFornecimentoDTO the autorizacaoFornecimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autorizacaoFornecimentoDTO, or with status {@code 400 (Bad Request)} if the autorizacaoFornecimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autorizacao-fornecimentos")
    public ResponseEntity<AutorizacaoFornecimentoDTO> createAutorizacaoFornecimento(@Valid @RequestBody AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) throws URISyntaxException {
        log.debug("REST request to save AutorizacaoFornecimento : {}", autorizacaoFornecimentoDTO);
        if (autorizacaoFornecimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new autorizacaoFornecimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutorizacaoFornecimentoDTO result = autorizacaoFornecimentoService.save(autorizacaoFornecimentoDTO);
        return ResponseEntity.created(new URI("/api/autorizacao-fornecimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autorizacao-fornecimentos} : Updates an existing autorizacaoFornecimento.
     *
     * @param autorizacaoFornecimentoDTO the autorizacaoFornecimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autorizacaoFornecimentoDTO,
     * or with status {@code 400 (Bad Request)} if the autorizacaoFornecimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autorizacaoFornecimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autorizacao-fornecimentos")
    public ResponseEntity<AutorizacaoFornecimentoDTO> updateAutorizacaoFornecimento(@Valid @RequestBody AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) throws URISyntaxException {
        log.debug("REST request to update AutorizacaoFornecimento : {}", autorizacaoFornecimentoDTO);
        if (autorizacaoFornecimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutorizacaoFornecimentoDTO result = autorizacaoFornecimentoService.save(autorizacaoFornecimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autorizacaoFornecimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /autorizacao-fornecimentos} : get all the autorizacaoFornecimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autorizacaoFornecimentos in body.
     */
    @GetMapping("/autorizacao-fornecimentos")
    public ResponseEntity<List<AutorizacaoFornecimentoDTO>> getAllAutorizacaoFornecimentos(Pageable pageable) {
        log.debug("REST request to get a page of AutorizacaoFornecimentos");
        Page<AutorizacaoFornecimentoDTO> page = autorizacaoFornecimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autorizacao-fornecimentos/:id} : get the "id" autorizacaoFornecimento.
     *
     * @param id the id of the autorizacaoFornecimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autorizacaoFornecimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autorizacao-fornecimentos/{id}")
    public ResponseEntity<AutorizacaoFornecimentoDTO> getAutorizacaoFornecimento(@PathVariable Long id) {
        log.debug("REST request to get AutorizacaoFornecimento : {}", id);
        Optional<AutorizacaoFornecimentoDTO> autorizacaoFornecimentoDTO = autorizacaoFornecimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autorizacaoFornecimentoDTO);
    }

    /**
     * {@code DELETE  /autorizacao-fornecimentos/:id} : delete the "id" autorizacaoFornecimento.
     *
     * @param id the id of the autorizacaoFornecimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autorizacao-fornecimentos/{id}")
    public ResponseEntity<Void> deleteAutorizacaoFornecimento(@PathVariable Long id) {
        log.debug("REST request to delete AutorizacaoFornecimento : {}", id);
        autorizacaoFornecimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/autorizacao-fornecimentos?query=:query} : search for the autorizacaoFornecimento corresponding
     * to the query.
     *
     * @param query the query of the autorizacaoFornecimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/autorizacao-fornecimentos")
    public ResponseEntity<List<AutorizacaoFornecimentoDTO>> searchAutorizacaoFornecimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AutorizacaoFornecimentos for query {}", query);
        Page<AutorizacaoFornecimentoDTO> page = autorizacaoFornecimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
