package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.TipoItemDietaService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.TipoItemDietaDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.TipoItemDieta}.
 */
@RestController
@RequestMapping("/api")
public class TipoItemDietaResource {

    private final Logger log = LoggerFactory.getLogger(TipoItemDietaResource.class);

    private static final String ENTITY_NAME = "prescricaoTipoItemDieta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoItemDietaService tipoItemDietaService;

    public TipoItemDietaResource(TipoItemDietaService tipoItemDietaService) {
        this.tipoItemDietaService = tipoItemDietaService;
    }

    /**
     * {@code POST  /tipo-item-dietas} : Create a new tipoItemDieta.
     *
     * @param tipoItemDietaDTO the tipoItemDietaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoItemDietaDTO, or with status {@code 400 (Bad Request)} if the tipoItemDieta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-item-dietas")
    public ResponseEntity<TipoItemDietaDTO> createTipoItemDieta(@Valid @RequestBody TipoItemDietaDTO tipoItemDietaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoItemDieta : {}", tipoItemDietaDTO);
        if (tipoItemDietaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoItemDieta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoItemDietaDTO result = tipoItemDietaService.save(tipoItemDietaDTO);
        return ResponseEntity.created(new URI("/api/tipo-item-dietas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-item-dietas} : Updates an existing tipoItemDieta.
     *
     * @param tipoItemDietaDTO the tipoItemDietaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoItemDietaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoItemDietaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoItemDietaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-item-dietas")
    public ResponseEntity<TipoItemDietaDTO> updateTipoItemDieta(@Valid @RequestBody TipoItemDietaDTO tipoItemDietaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoItemDieta : {}", tipoItemDietaDTO);
        if (tipoItemDietaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoItemDietaDTO result = tipoItemDietaService.save(tipoItemDietaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoItemDietaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-item-dietas} : get all the tipoItemDietas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoItemDietas in body.
     */
    @GetMapping("/tipo-item-dietas")
    public ResponseEntity<List<TipoItemDietaDTO>> getAllTipoItemDietas(Pageable pageable) {
        log.debug("REST request to get a page of TipoItemDietas");
        Page<TipoItemDietaDTO> page = tipoItemDietaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-item-dietas/:id} : get the "id" tipoItemDieta.
     *
     * @param id the id of the tipoItemDietaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoItemDietaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-item-dietas/{id}")
    public ResponseEntity<TipoItemDietaDTO> getTipoItemDieta(@PathVariable Long id) {
        log.debug("REST request to get TipoItemDieta : {}", id);
        Optional<TipoItemDietaDTO> tipoItemDietaDTO = tipoItemDietaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoItemDietaDTO);
    }

    /**
     * {@code DELETE  /tipo-item-dietas/:id} : delete the "id" tipoItemDieta.
     *
     * @param id the id of the tipoItemDietaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-item-dietas/{id}")
    public ResponseEntity<Void> deleteTipoItemDieta(@PathVariable Long id) {
        log.debug("REST request to delete TipoItemDieta : {}", id);
        tipoItemDietaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-item-dietas?query=:query} : search for the tipoItemDieta corresponding
     * to the query.
     *
     * @param query the query of the tipoItemDieta search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-item-dietas")
    public ResponseEntity<List<TipoItemDietaDTO>> searchTipoItemDietas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoItemDietas for query {}", query);
        Page<TipoItemDietaDTO> page = tipoItemDietaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
