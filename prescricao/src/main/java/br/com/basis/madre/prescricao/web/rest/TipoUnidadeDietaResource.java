package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.TipoUnidadeDietaService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.TipoUnidadeDietaDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.TipoUnidadeDieta}.
 */
@RestController
@RequestMapping("/api")
public class TipoUnidadeDietaResource {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadeDietaResource.class);

    private static final String ENTITY_NAME = "prescricaoTipoUnidadeDieta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoUnidadeDietaService tipoUnidadeDietaService;

    public TipoUnidadeDietaResource(TipoUnidadeDietaService tipoUnidadeDietaService) {
        this.tipoUnidadeDietaService = tipoUnidadeDietaService;
    }

    /**
     * {@code POST  /tipo-unidade-dietas} : Create a new tipoUnidadeDieta.
     *
     * @param tipoUnidadeDietaDTO the tipoUnidadeDietaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoUnidadeDietaDTO, or with status {@code 400 (Bad Request)} if the tipoUnidadeDieta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-unidade-dietas")
    public ResponseEntity<TipoUnidadeDietaDTO> createTipoUnidadeDieta(@Valid @RequestBody TipoUnidadeDietaDTO tipoUnidadeDietaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoUnidadeDieta : {}", tipoUnidadeDietaDTO);
        if (tipoUnidadeDietaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoUnidadeDieta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoUnidadeDietaDTO result = tipoUnidadeDietaService.save(tipoUnidadeDietaDTO);
        return ResponseEntity.created(new URI("/api/tipo-unidade-dietas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-unidade-dietas} : Updates an existing tipoUnidadeDieta.
     *
     * @param tipoUnidadeDietaDTO the tipoUnidadeDietaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoUnidadeDietaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoUnidadeDietaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoUnidadeDietaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-unidade-dietas")
    public ResponseEntity<TipoUnidadeDietaDTO> updateTipoUnidadeDieta(@Valid @RequestBody TipoUnidadeDietaDTO tipoUnidadeDietaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoUnidadeDieta : {}", tipoUnidadeDietaDTO);
        if (tipoUnidadeDietaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoUnidadeDietaDTO result = tipoUnidadeDietaService.save(tipoUnidadeDietaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoUnidadeDietaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-unidade-dietas} : get all the tipoUnidadeDietas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoUnidadeDietas in body.
     */
    @GetMapping("/tipo-unidade-dietas")
    public ResponseEntity<List<TipoUnidadeDietaDTO>> getAllTipoUnidadeDietas(Pageable pageable) {
        log.debug("REST request to get a page of TipoUnidadeDietas");
        Page<TipoUnidadeDietaDTO> page = tipoUnidadeDietaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-unidade-dietas/:id} : get the "id" tipoUnidadeDieta.
     *
     * @param id the id of the tipoUnidadeDietaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoUnidadeDietaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-unidade-dietas/{id}")
    public ResponseEntity<TipoUnidadeDietaDTO> getTipoUnidadeDieta(@PathVariable Long id) {
        log.debug("REST request to get TipoUnidadeDieta : {}", id);
        Optional<TipoUnidadeDietaDTO> tipoUnidadeDietaDTO = tipoUnidadeDietaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoUnidadeDietaDTO);
    }

    /**
     * {@code DELETE  /tipo-unidade-dietas/:id} : delete the "id" tipoUnidadeDieta.
     *
     * @param id the id of the tipoUnidadeDietaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-unidade-dietas/{id}")
    public ResponseEntity<Void> deleteTipoUnidadeDieta(@PathVariable Long id) {
        log.debug("REST request to delete TipoUnidadeDieta : {}", id);
        tipoUnidadeDietaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-unidade-dietas?query=:query} : search for the tipoUnidadeDieta corresponding
     * to the query.
     *
     * @param query the query of the tipoUnidadeDieta search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-unidade-dietas")
    public ResponseEntity<List<TipoUnidadeDietaDTO>> searchTipoUnidadeDietas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoUnidadeDietas for query {}", query);
        Page<TipoUnidadeDietaDTO> page = tipoUnidadeDietaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
