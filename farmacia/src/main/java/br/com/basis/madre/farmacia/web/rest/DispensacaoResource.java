package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.DispensacaoService;
import br.com.basis.madre.farmacia.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.DispensacaoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.Dispensacao}.
 */
@RestController
@RequestMapping("/api")
public class DispensacaoResource {

    private final Logger log = LoggerFactory.getLogger(DispensacaoResource.class);

    private static final String ENTITY_NAME = "farmaciaDispensacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispensacaoService dispensacaoService;

    public DispensacaoResource(DispensacaoService dispensacaoService) {
        this.dispensacaoService = dispensacaoService;
    }

    /**
     * {@code POST  /dispensacaos} : Create a new dispensacao.
     *
     * @param dispensacaoDTO the dispensacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispensacaoDTO, or with status {@code 400 (Bad Request)} if the dispensacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispensacaos")
    public ResponseEntity<DispensacaoDTO> createDispensacao(@RequestBody DispensacaoDTO dispensacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Dispensacao : {}", dispensacaoDTO);
        if (dispensacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispensacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispensacaoDTO result = dispensacaoService.save(dispensacaoDTO);
        return ResponseEntity.created(new URI("/api/dispensacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispensacaos} : Updates an existing dispensacao.
     *
     * @param dispensacaoDTO the dispensacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispensacaoDTO,
     * or with status {@code 400 (Bad Request)} if the dispensacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispensacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispensacaos")
    public ResponseEntity<DispensacaoDTO> updateDispensacao(@RequestBody DispensacaoDTO dispensacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Dispensacao : {}", dispensacaoDTO);
        if (dispensacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DispensacaoDTO result = dispensacaoService.save(dispensacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dispensacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dispensacaos} : get all the dispensacaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispensacaos in body.
     */
    @GetMapping("/dispensacaos")
    public ResponseEntity<List<DispensacaoDTO>> getAllDispensacaos(Pageable pageable) {
        log.debug("REST request to get a page of Dispensacaos");
        Page<DispensacaoDTO> page = dispensacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispensacaos/:id} : get the "id" dispensacao.
     *
     * @param id the id of the dispensacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispensacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispensacaos/{id}")
    public ResponseEntity<DispensacaoDTO> getDispensacao(@PathVariable Long id) {
        log.debug("REST request to get Dispensacao : {}", id);
        Optional<DispensacaoDTO> dispensacaoDTO = dispensacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispensacaoDTO);
    }

    /**
     * {@code DELETE  /dispensacaos/:id} : delete the "id" dispensacao.
     *
     * @param id the id of the dispensacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispensacaos/{id}")
    public ResponseEntity<Void> deleteDispensacao(@PathVariable Long id) {
        log.debug("REST request to delete Dispensacao : {}", id);
        dispensacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/dispensacaos?query=:query} : search for the dispensacao corresponding
     * to the query.
     *
     * @param query the query of the dispensacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dispensacaos")
    public ResponseEntity<List<DispensacaoDTO>> searchDispensacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dispensacaos for query {}", query);
        Page<DispensacaoDTO> page = dispensacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
