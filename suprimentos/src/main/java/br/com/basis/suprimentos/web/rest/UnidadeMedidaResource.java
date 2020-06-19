package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.UnidadeMedidaService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.UnidadeMedidaDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.UnidadeMedida}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeMedidaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeMedidaResource.class);

    private static final String ENTITY_NAME = "madresuprimentosUnidadeMedida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeMedidaService unidadeMedidaService;

    public UnidadeMedidaResource(UnidadeMedidaService unidadeMedidaService) {
        this.unidadeMedidaService = unidadeMedidaService;
    }

    /**
     * {@code POST  /unidade-medidas} : Create a new unidadeMedida.
     *
     * @param unidadeMedidaDTO the unidadeMedidaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeMedidaDTO, or with status {@code 400 (Bad Request)} if the unidadeMedida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-medidas")
    public ResponseEntity<UnidadeMedidaDTO> createUnidadeMedida(@Valid @RequestBody UnidadeMedidaDTO unidadeMedidaDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadeMedida : {}", unidadeMedidaDTO);
        if (unidadeMedidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadeMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeMedidaDTO result = unidadeMedidaService.save(unidadeMedidaDTO);
        return ResponseEntity.created(new URI("/api/unidade-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-medidas} : Updates an existing unidadeMedida.
     *
     * @param unidadeMedidaDTO the unidadeMedidaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeMedidaDTO,
     * or with status {@code 400 (Bad Request)} if the unidadeMedidaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeMedidaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-medidas")
    public ResponseEntity<UnidadeMedidaDTO> updateUnidadeMedida(@Valid @RequestBody UnidadeMedidaDTO unidadeMedidaDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadeMedida : {}", unidadeMedidaDTO);
        if (unidadeMedidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeMedidaDTO result = unidadeMedidaService.save(unidadeMedidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeMedidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-medidas} : get all the unidadeMedidas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeMedidas in body.
     */
    @GetMapping("/unidade-medidas")
    public ResponseEntity<List<UnidadeMedidaDTO>> getAllUnidadeMedidas(Pageable pageable) {
        log.debug("REST request to get a page of UnidadeMedidas");
        Page<UnidadeMedidaDTO> page = unidadeMedidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidade-medidas/:id} : get the "id" unidadeMedida.
     *
     * @param id the id of the unidadeMedidaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeMedidaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-medidas/{id}")
    public ResponseEntity<UnidadeMedidaDTO> getUnidadeMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadeMedida : {}", id);
        Optional<UnidadeMedidaDTO> unidadeMedidaDTO = unidadeMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeMedidaDTO);
    }

    /**
     * {@code DELETE  /unidade-medidas/:id} : delete the "id" unidadeMedida.
     *
     * @param id the id of the unidadeMedidaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-medidas/{id}")
    public ResponseEntity<Void> deleteUnidadeMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeMedida : {}", id);
        unidadeMedidaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-medidas?query=:query} : search for the unidadeMedida corresponding
     * to the query.
     *
     * @param query the query of the unidadeMedida search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-medidas")
    public ResponseEntity<List<UnidadeMedidaDTO>> searchUnidadeMedidas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeMedidas for query {}", query);
        Page<UnidadeMedidaDTO> page = unidadeMedidaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
