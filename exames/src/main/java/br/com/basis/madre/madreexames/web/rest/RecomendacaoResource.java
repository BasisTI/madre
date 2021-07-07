package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.RecomendacaoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.RecomendacaoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.Recomendacao}.
 */
@RestController
@RequestMapping("/api")
public class RecomendacaoResource {

    private final Logger log = LoggerFactory.getLogger(RecomendacaoResource.class);

    private static final String ENTITY_NAME = "madreexamesRecomendacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecomendacaoService recomendacaoService;

    public RecomendacaoResource(RecomendacaoService recomendacaoService) {
        this.recomendacaoService = recomendacaoService;
    }

    /**
     * {@code POST  /recomendacaos} : Create a new recomendacao.
     *
     * @param recomendacaoDTO the recomendacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recomendacaoDTO, or with status {@code 400 (Bad Request)} if the recomendacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recomendacaos")
    public ResponseEntity<RecomendacaoDTO> createRecomendacao(@Valid @RequestBody RecomendacaoDTO recomendacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Recomendacao : {}", recomendacaoDTO);
        if (recomendacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recomendacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecomendacaoDTO result = recomendacaoService.save(recomendacaoDTO);
        return ResponseEntity.created(new URI("/api/recomendacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recomendacaos} : Updates an existing recomendacao.
     *
     * @param recomendacaoDTO the recomendacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recomendacaoDTO,
     * or with status {@code 400 (Bad Request)} if the recomendacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recomendacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recomendacaos")
    public ResponseEntity<RecomendacaoDTO> updateRecomendacao(@Valid @RequestBody RecomendacaoDTO recomendacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Recomendacao : {}", recomendacaoDTO);
        if (recomendacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecomendacaoDTO result = recomendacaoService.save(recomendacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recomendacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recomendacaos} : get all the recomendacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recomendacaos in body.
     */
    @GetMapping("/recomendacaos")
    public ResponseEntity<List<RecomendacaoDTO>> getAllRecomendacaos(Pageable pageable) {
        log.debug("REST request to get a page of Recomendacaos");
        Page<RecomendacaoDTO> page = recomendacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recomendacaos/:id} : get the "id" recomendacao.
     *
     * @param id the id of the recomendacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recomendacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recomendacaos/{id}")
    public ResponseEntity<RecomendacaoDTO> getRecomendacao(@PathVariable Long id) {
        log.debug("REST request to get Recomendacao : {}", id);
        Optional<RecomendacaoDTO> recomendacaoDTO = recomendacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recomendacaoDTO);
    }

    /**
     * {@code DELETE  /recomendacaos/:id} : delete the "id" recomendacao.
     *
     * @param id the id of the recomendacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recomendacaos/{id}")
    public ResponseEntity<Void> deleteRecomendacao(@PathVariable Long id) {
        log.debug("REST request to delete Recomendacao : {}", id);
        recomendacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/recomendacaos?query=:query} : search for the recomendacao corresponding
     * to the query.
     *
     * @param query the query of the recomendacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/recomendacaos")
    public ResponseEntity<List<RecomendacaoDTO>> searchRecomendacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recomendacaos for query {}", query);
        Page<RecomendacaoDTO> page = recomendacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
