package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.TipoUnidadeService;

import br.com.basis.madre.service.dto.TipoUnidadeDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


/**
 * REST controller for managing {@link br.com.basis.madre.domain.TipoUnidade}.
 */
@RestController
@RequestMapping("/api")
public class TipoUnidadeResource {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadeResource.class);

    private static final String ENTITY_NAME = "internacaoTipoUnidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoUnidadeService tipoUnidadeService;

    public TipoUnidadeResource(TipoUnidadeService tipoUnidadeService) {
        this.tipoUnidadeService = tipoUnidadeService;
    }

    /**
     * {@code POST  /tipo-unidades} : Create a new tipoUnidade.
     *
     * @param tipoUnidadeDTO the tipoUnidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoUnidadeDTO, or with status {@code 400 (Bad Request)} if the tipoUnidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-unidades")
    public ResponseEntity<TipoUnidadeDTO> createTipoUnidade(@RequestBody TipoUnidadeDTO tipoUnidadeDTO) throws URISyntaxException {
        log.debug("REST request to save TipoUnidade : {}", tipoUnidadeDTO);
        if (tipoUnidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoUnidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoUnidadeDTO result = tipoUnidadeService.save(tipoUnidadeDTO);
        return ResponseEntity.created(new URI("/api/tipo-unidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-unidades} : Updates an existing tipoUnidade.
     *
     * @param tipoUnidadeDTO the tipoUnidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoUnidadeDTO,
     * or with status {@code 400 (Bad Request)} if the tipoUnidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoUnidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-unidades")
    public ResponseEntity<TipoUnidadeDTO> updateTipoUnidade(@RequestBody TipoUnidadeDTO tipoUnidadeDTO) throws URISyntaxException {
        log.debug("REST request to update TipoUnidade : {}", tipoUnidadeDTO);
        if (tipoUnidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoUnidadeDTO result = tipoUnidadeService.save(tipoUnidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoUnidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-unidades} : get all the tipoUnidades.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoUnidades in body.
     */
    @GetMapping("/tipo-unidades")
    public ResponseEntity<List<TipoUnidadeDTO>> getAllTipoUnidades(Pageable pageable) {
        log.debug("REST request to get a page of TipoUnidades");
        Page<TipoUnidadeDTO> page = tipoUnidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-unidades/:id} : get the "id" tipoUnidade.
     *
     * @param id the id of the tipoUnidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoUnidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-unidades/{id}")
    public ResponseEntity<TipoUnidadeDTO> getTipoUnidade(@PathVariable Long id) {
        log.debug("REST request to get TipoUnidade : {}", id);
        Optional<TipoUnidadeDTO> tipoUnidadeDTO = tipoUnidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoUnidadeDTO);
    }

    /**
     * {@code DELETE  /tipo-unidades/:id} : delete the "id" tipoUnidade.
     *
     * @param id the id of the tipoUnidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-unidades/{id}")
    public ResponseEntity<Void> deleteTipoUnidade(@PathVariable Long id) {
        log.debug("REST request to delete TipoUnidade : {}", id);
        tipoUnidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-unidades?query=:query} : search for the tipoUnidade corresponding
     * to the query.
     *
     * @param query the query of the tipoUnidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-unidades")
    public ResponseEntity<List<TipoUnidadeDTO>> searchTipoUnidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoUnidades for query {}", query);
        Page<TipoUnidadeDTO> page = tipoUnidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
