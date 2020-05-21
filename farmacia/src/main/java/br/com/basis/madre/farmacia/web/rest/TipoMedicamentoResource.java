package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.TipoMedicamentoService;
import br.com.basis.madre.farmacia.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.TipoMedicamentoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.TipoMedicamento}.
 */
@RestController
@RequestMapping("/api")
public class TipoMedicamentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoMedicamentoResource.class);

    private static final String ENTITY_NAME = "farmaciaTipoMedicamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoMedicamentoService tipoMedicamentoService;

    public TipoMedicamentoResource(TipoMedicamentoService tipoMedicamentoService) {
        this.tipoMedicamentoService = tipoMedicamentoService;
    }

    /**
     * {@code POST  /tipo-medicamentos} : Create a new tipoMedicamento.
     *
     * @param tipoMedicamentoDTO the tipoMedicamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoMedicamentoDTO, or with status {@code 400 (Bad Request)} if the tipoMedicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-medicamentos")
    public ResponseEntity<TipoMedicamentoDTO> createTipoMedicamento(@RequestBody TipoMedicamentoDTO tipoMedicamentoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoMedicamento : {}", tipoMedicamentoDTO);
        if (tipoMedicamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoMedicamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoMedicamentoDTO result = tipoMedicamentoService.save(tipoMedicamentoDTO);
        return ResponseEntity.created(new URI("/api/tipo-medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-medicamentos} : Updates an existing tipoMedicamento.
     *
     * @param tipoMedicamentoDTO the tipoMedicamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMedicamentoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoMedicamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoMedicamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-medicamentos")
    public ResponseEntity<TipoMedicamentoDTO> updateTipoMedicamento(@RequestBody TipoMedicamentoDTO tipoMedicamentoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoMedicamento : {}", tipoMedicamentoDTO);
        if (tipoMedicamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoMedicamentoDTO result = tipoMedicamentoService.save(tipoMedicamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoMedicamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-medicamentos} : get all the tipoMedicamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoMedicamentos in body.
     */
    @GetMapping("/tipo-medicamentos")
    public ResponseEntity<List<TipoMedicamentoDTO>> getAllTipoMedicamentos(Pageable pageable) {
        log.debug("REST request to get a page of TipoMedicamentos");
        Page<TipoMedicamentoDTO> page = tipoMedicamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-medicamentos/:id} : get the "id" tipoMedicamento.
     *
     * @param id the id of the tipoMedicamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoMedicamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-medicamentos/{id}")
    public ResponseEntity<TipoMedicamentoDTO> getTipoMedicamento(@PathVariable Long id) {
        log.debug("REST request to get TipoMedicamento : {}", id);
        Optional<TipoMedicamentoDTO> tipoMedicamentoDTO = tipoMedicamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoMedicamentoDTO);
    }

    /**
     * {@code DELETE  /tipo-medicamentos/:id} : delete the "id" tipoMedicamento.
     *
     * @param id the id of the tipoMedicamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-medicamentos/{id}")
    public ResponseEntity<Void> deleteTipoMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete TipoMedicamento : {}", id);
        tipoMedicamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-medicamentos?query=:query} : search for the tipoMedicamento corresponding
     * to the query.
     *
     * @param query the query of the tipoMedicamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-medicamentos")
    public ResponseEntity<List<TipoMedicamentoDTO>> searchTipoMedicamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoMedicamentos for query {}", query);
        Page<TipoMedicamentoDTO> page = tipoMedicamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
