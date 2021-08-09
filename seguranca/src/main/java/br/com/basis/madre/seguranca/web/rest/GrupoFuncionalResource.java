package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.service.GrupoFuncionalService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.seguranca.service.dto.GrupoFuncionalDTO;

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
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.GrupoFuncional}.
 */
@RestController
@RequestMapping("/api")
public class GrupoFuncionalResource {

    private final Logger log = LoggerFactory.getLogger(GrupoFuncionalResource.class);

    private static final String ENTITY_NAME = "madresegurancaGrupoFuncional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoFuncionalService grupoFuncionalService;

    public GrupoFuncionalResource(GrupoFuncionalService grupoFuncionalService) {
        this.grupoFuncionalService = grupoFuncionalService;
    }

    /**
     * {@code POST  /grupo-funcionals} : Create a new grupoFuncional.
     *
     * @param grupoFuncionalDTO the grupoFuncionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoFuncionalDTO, or with status {@code 400 (Bad Request)} if the grupoFuncional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-funcionals")
    public ResponseEntity<GrupoFuncionalDTO> createGrupoFuncional(@Valid @RequestBody GrupoFuncionalDTO grupoFuncionalDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoFuncional : {}", grupoFuncionalDTO);
        if (grupoFuncionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoFuncional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoFuncionalDTO result = grupoFuncionalService.save(grupoFuncionalDTO);
        return ResponseEntity.created(new URI("/api/grupo-funcionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-funcionals} : Updates an existing grupoFuncional.
     *
     * @param grupoFuncionalDTO the grupoFuncionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoFuncionalDTO,
     * or with status {@code 400 (Bad Request)} if the grupoFuncionalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoFuncionalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-funcionals")
    public ResponseEntity<GrupoFuncionalDTO> updateGrupoFuncional(@Valid @RequestBody GrupoFuncionalDTO grupoFuncionalDTO) throws URISyntaxException {
        log.debug("REST request to update GrupoFuncional : {}", grupoFuncionalDTO);
        if (grupoFuncionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoFuncionalDTO result = grupoFuncionalService.save(grupoFuncionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoFuncionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupo-funcionals} : get all the grupoFuncionals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoFuncionals in body.
     */
    @GetMapping("/grupo-funcionals")
    public ResponseEntity<List<GrupoFuncionalDTO>> getAllGrupoFuncionals(Pageable pageable) {
        log.debug("REST request to get a page of GrupoFuncionals");
        Page<GrupoFuncionalDTO> page = grupoFuncionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupo-funcionals/:id} : get the "id" grupoFuncional.
     *
     * @param id the id of the grupoFuncionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoFuncionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-funcionals/{id}")
    public ResponseEntity<GrupoFuncionalDTO> getGrupoFuncional(@PathVariable Long id) {
        log.debug("REST request to get GrupoFuncional : {}", id);
        Optional<GrupoFuncionalDTO> grupoFuncionalDTO = grupoFuncionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoFuncionalDTO);
    }

    /**
     * {@code DELETE  /grupo-funcionals/:id} : delete the "id" grupoFuncional.
     *
     * @param id the id of the grupoFuncionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-funcionals/{id}")
    public ResponseEntity<Void> deleteGrupoFuncional(@PathVariable Long id) {
        log.debug("REST request to delete GrupoFuncional : {}", id);
        grupoFuncionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grupo-funcionals?query=:query} : search for the grupoFuncional corresponding
     * to the query.
     *
     * @param query the query of the grupoFuncional search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grupo-funcionals")
    public ResponseEntity<List<GrupoFuncionalDTO>> searchGrupoFuncionals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrupoFuncionals for query {}", query);
        Page<GrupoFuncionalDTO> page = grupoFuncionalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
