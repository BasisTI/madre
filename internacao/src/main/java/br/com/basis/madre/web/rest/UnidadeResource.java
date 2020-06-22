package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.UnidadeService;
import br.com.basis.madre.service.dto.FormularioUnidadeDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.UnidadeDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Unidade}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeResource.class);

    private static final String ENTITY_NAME = "internacaoUnidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeService unidadeService;

    public UnidadeResource(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    /**
     * {@code POST  /unidades} : Create a new unidade.
     *
     * @param unidadeDTO the unidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeDTO, or with status {@code 400 (Bad Request)} if the unidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidades")
    public ResponseEntity<FormularioUnidadeDTO> createUnidade(@Valid @RequestBody FormularioUnidadeDTO unidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Unidade : {}", unidadeDTO);
        if (unidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormularioUnidadeDTO result = unidadeService.save(unidadeDTO);
        return ResponseEntity.created(new URI("/api/unidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidades} : Updates an existing unidade.
     *
     * @param unidadeDTO the unidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeDTO,
     * or with status {@code 400 (Bad Request)} if the unidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidades")
    public ResponseEntity<FormularioUnidadeDTO> updateUnidade(@Valid @RequestBody FormularioUnidadeDTO unidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Unidade : {}", unidadeDTO);
        if (unidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormularioUnidadeDTO result = unidadeService.save(unidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidades} : get all the unidades.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidades in body.
     */
    @GetMapping("/unidades")
    public ResponseEntity<List<UnidadeDTO>> getAllUnidades(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Unidades");
        Page<UnidadeDTO> page;
        if (eagerload) {
            page = unidadeService.findAllWithEagerRelationships(pageable);
        } else {
            page = unidadeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidades/:id} : get the "id" unidade.
     *
     * @param id the id of the unidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidades/{id}")
    public ResponseEntity<UnidadeDTO> getUnidade(@PathVariable Long id) {
        log.debug("REST request to get Unidade : {}", id);
        Optional<UnidadeDTO> unidadeDTO = unidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeDTO);
    }

    /**
     * {@code DELETE  /unidades/:id} : delete the "id" unidade.
     *
     * @param id the id of the unidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidades/{id}")
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id) {
        log.debug("REST request to delete Unidade : {}", id);
        unidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidades?query=:query} : search for the unidade corresponding
     * to the query.
     *
     * @param query the query of the unidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidades")
    public ResponseEntity<List<UnidadeDTO>> searchUnidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Unidades for query {}", query);
        Page<UnidadeDTO> page = unidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
