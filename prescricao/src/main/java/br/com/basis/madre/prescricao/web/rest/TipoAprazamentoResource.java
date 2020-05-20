package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.TipoAprazamentoService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.TipoAprazamentoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.TipoAprazamento}.
 */
@RestController
@RequestMapping("/api")
public class TipoAprazamentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoAprazamentoResource.class);

    private static final String ENTITY_NAME = "prescricaoTipoAprazamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoAprazamentoService tipoAprazamentoService;

    public TipoAprazamentoResource(TipoAprazamentoService tipoAprazamentoService) {
        this.tipoAprazamentoService = tipoAprazamentoService;
    }

    /**
     * {@code POST  /tipo-aprazamentos} : Create a new tipoAprazamento.
     *
     * @param tipoAprazamentoDTO the tipoAprazamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoAprazamentoDTO, or with status {@code 400 (Bad Request)} if the tipoAprazamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-aprazamentos")
    public ResponseEntity<TipoAprazamentoDTO> createTipoAprazamento(@Valid @RequestBody TipoAprazamentoDTO tipoAprazamentoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoAprazamento : {}", tipoAprazamentoDTO);
        if (tipoAprazamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoAprazamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAprazamentoDTO result = tipoAprazamentoService.save(tipoAprazamentoDTO);
        return ResponseEntity.created(new URI("/api/tipo-aprazamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-aprazamentos} : Updates an existing tipoAprazamento.
     *
     * @param tipoAprazamentoDTO the tipoAprazamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoAprazamentoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoAprazamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoAprazamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-aprazamentos")
    public ResponseEntity<TipoAprazamentoDTO> updateTipoAprazamento(@Valid @RequestBody TipoAprazamentoDTO tipoAprazamentoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoAprazamento : {}", tipoAprazamentoDTO);
        if (tipoAprazamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoAprazamentoDTO result = tipoAprazamentoService.save(tipoAprazamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoAprazamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-aprazamentos} : get all the tipoAprazamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoAprazamentos in body.
     */
    @GetMapping("/tipo-aprazamentos")
    public ResponseEntity<List<TipoAprazamentoDTO>> getAllTipoAprazamentos(Pageable pageable) {
        log.debug("REST request to get a page of TipoAprazamentos");
        Page<TipoAprazamentoDTO> page = tipoAprazamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-aprazamentos/:id} : get the "id" tipoAprazamento.
     *
     * @param id the id of the tipoAprazamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoAprazamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-aprazamentos/{id}")
    public ResponseEntity<TipoAprazamentoDTO> getTipoAprazamento(@PathVariable Long id) {
        log.debug("REST request to get TipoAprazamento : {}", id);
        Optional<TipoAprazamentoDTO> tipoAprazamentoDTO = tipoAprazamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoAprazamentoDTO);
    }

    /**
     * {@code DELETE  /tipo-aprazamentos/:id} : delete the "id" tipoAprazamento.
     *
     * @param id the id of the tipoAprazamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-aprazamentos/{id}")
    public ResponseEntity<Void> deleteTipoAprazamento(@PathVariable Long id) {
        log.debug("REST request to delete TipoAprazamento : {}", id);
        tipoAprazamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-aprazamentos?query=:query} : search for the tipoAprazamento corresponding
     * to the query.
     *
     * @param query the query of the tipoAprazamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-aprazamentos")
    public ResponseEntity<List<TipoAprazamentoDTO>> searchTipoAprazamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoAprazamentos for query {}", query);
        Page<TipoAprazamentoDTO> page = tipoAprazamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
