package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.ApresentacaoService;
import br.com.basis.madre.farmacia.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.ApresentacaoDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.Apresentacao}.
 */
@RestController
@RequestMapping("/api")
public class ApresentacaoResource {

    private final Logger log = LoggerFactory.getLogger(ApresentacaoResource.class);

    private static final String ENTITY_NAME = "farmaciaApresentacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApresentacaoService apresentacaoService;

    public ApresentacaoResource(ApresentacaoService apresentacaoService) {
        this.apresentacaoService = apresentacaoService;
    }

    /**
     * {@code POST  /apresentacaos} : Create a new apresentacao.
     *
     * @param apresentacaoDTO the apresentacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apresentacaoDTO, or with status {@code 400 (Bad Request)} if the apresentacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/apresentacaos")
    public ResponseEntity<ApresentacaoDTO> createApresentacao(@RequestBody ApresentacaoDTO apresentacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Apresentacao : {}", apresentacaoDTO);
        if (apresentacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new apresentacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApresentacaoDTO result = apresentacaoService.save(apresentacaoDTO);
        return ResponseEntity.created(new URI("/api/apresentacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /apresentacaos} : Updates an existing apresentacao.
     *
     * @param apresentacaoDTO the apresentacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apresentacaoDTO,
     * or with status {@code 400 (Bad Request)} if the apresentacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apresentacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/apresentacaos")
    public ResponseEntity<ApresentacaoDTO> updateApresentacao(@RequestBody ApresentacaoDTO apresentacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Apresentacao : {}", apresentacaoDTO);
        if (apresentacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApresentacaoDTO result = apresentacaoService.save(apresentacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apresentacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /apresentacaos} : get all the apresentacaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apresentacaos in body.
     */
    @GetMapping("/apresentacaos")
    public ResponseEntity<List<ApresentacaoDTO>> getAllApresentacaos(ApresentacaoDTO apresentacaoDTO, Pageable pageable) {
        log.debug("REST request to get a page of Apresentacaos");
        Page<ApresentacaoDTO> page = apresentacaoService.findAll(apresentacaoDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apresentacaos/:id} : get the "id" apresentacao.
     *
     * @param id the id of the apresentacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apresentacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/apresentacaos/{id}")
    public ResponseEntity<ApresentacaoDTO> getApresentacao(@PathVariable Long id) {
        log.debug("REST request to get Apresentacao : {}", id);
        Optional<ApresentacaoDTO> apresentacaoDTO = apresentacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apresentacaoDTO);
    }

    /**
     * {@code DELETE  /apresentacaos/:id} : delete the "id" apresentacao.
     *
     * @param id the id of the apresentacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/apresentacaos/{id}")
    public ResponseEntity<Void> deleteApresentacao(@PathVariable Long id) {
        log.debug("REST request to delete Apresentacao : {}", id);
        apresentacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/apresentacaos?query=:query} : search for the apresentacao corresponding
     * to the query.
     *
     * @param query the query of the apresentacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/apresentacaos")
    public ResponseEntity<List<ApresentacaoDTO>> searchApresentacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Apresentacaos for query {}", query);
        Page<ApresentacaoDTO> page = apresentacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
