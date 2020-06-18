package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.ComposicaoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.ComposicaoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.Composicao}.
 */
@RestController
@RequestMapping("/api")
public class ComposicaoResource {

    private final Logger log = LoggerFactory.getLogger(ComposicaoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosComposicao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComposicaoService composicaoService;

    public ComposicaoResource(ComposicaoService composicaoService) {
        this.composicaoService = composicaoService;
    }

    /**
     * {@code POST  /composicaos} : Create a new composicao.
     *
     * @param composicaoDTO the composicaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new composicaoDTO, or with status {@code 400 (Bad Request)} if the composicao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/composicaos")
    public ResponseEntity<ComposicaoDTO> createComposicao(@Valid @RequestBody ComposicaoDTO composicaoDTO) throws URISyntaxException {
        log.debug("REST request to save Composicao : {}", composicaoDTO);
        if (composicaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new composicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComposicaoDTO result = composicaoService.save(composicaoDTO);
        return ResponseEntity.created(new URI("/api/composicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /composicaos} : Updates an existing composicao.
     *
     * @param composicaoDTO the composicaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated composicaoDTO,
     * or with status {@code 400 (Bad Request)} if the composicaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the composicaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/composicaos")
    public ResponseEntity<ComposicaoDTO> updateComposicao(@Valid @RequestBody ComposicaoDTO composicaoDTO) throws URISyntaxException {
        log.debug("REST request to update Composicao : {}", composicaoDTO);
        if (composicaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComposicaoDTO result = composicaoService.save(composicaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, composicaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /composicaos} : get all the composicaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of composicaos in body.
     */
    @GetMapping("/composicaos")
    public ResponseEntity<List<ComposicaoDTO>> getAllComposicaos(Pageable pageable) {
        log.debug("REST request to get a page of Composicaos");
        Page<ComposicaoDTO> page = composicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /composicaos/:id} : get the "id" composicao.
     *
     * @param id the id of the composicaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the composicaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/composicaos/{id}")
    public ResponseEntity<ComposicaoDTO> getComposicao(@PathVariable Long id) {
        log.debug("REST request to get Composicao : {}", id);
        Optional<ComposicaoDTO> composicaoDTO = composicaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(composicaoDTO);
    }

    /**
     * {@code DELETE  /composicaos/:id} : delete the "id" composicao.
     *
     * @param id the id of the composicaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/composicaos/{id}")
    public ResponseEntity<Void> deleteComposicao(@PathVariable Long id) {
        log.debug("REST request to delete Composicao : {}", id);
        composicaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/composicaos?query=:query} : search for the composicao corresponding
     * to the query.
     *
     * @param query the query of the composicao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/composicaos")
    public ResponseEntity<List<ComposicaoDTO>> searchComposicaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Composicaos for query {}", query);
        Page<ComposicaoDTO> page = composicaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
