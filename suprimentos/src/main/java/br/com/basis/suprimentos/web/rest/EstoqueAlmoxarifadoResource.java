package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.EstoqueAlmoxarifadoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.EstoqueAlmoxarifado}.
 */
@RestController
@RequestMapping("/api")
public class EstoqueAlmoxarifadoResource {

    private final Logger log = LoggerFactory.getLogger(EstoqueAlmoxarifadoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosEstoqueAlmoxarifado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstoqueAlmoxarifadoService estoqueAlmoxarifadoService;

    public EstoqueAlmoxarifadoResource(EstoqueAlmoxarifadoService estoqueAlmoxarifadoService) {
        this.estoqueAlmoxarifadoService = estoqueAlmoxarifadoService;
    }

    /**
     * {@code POST  /estoque-almoxarifados} : Create a new estoqueAlmoxarifado.
     *
     * @param estoqueAlmoxarifadoDTO the estoqueAlmoxarifadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estoqueAlmoxarifadoDTO, or with status {@code 400 (Bad Request)} if the estoqueAlmoxarifado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estoque-almoxarifados")
    public ResponseEntity<EstoqueAlmoxarifadoDTO> createEstoqueAlmoxarifado(@Valid @RequestBody EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to save EstoqueAlmoxarifado : {}", estoqueAlmoxarifadoDTO);
        if (estoqueAlmoxarifadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estoqueAlmoxarifado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstoqueAlmoxarifadoDTO result = estoqueAlmoxarifadoService.save(estoqueAlmoxarifadoDTO);
        return ResponseEntity.created(new URI("/api/estoque-almoxarifados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estoque-almoxarifados} : Updates an existing estoqueAlmoxarifado.
     *
     * @param estoqueAlmoxarifadoDTO the estoqueAlmoxarifadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estoqueAlmoxarifadoDTO,
     * or with status {@code 400 (Bad Request)} if the estoqueAlmoxarifadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estoqueAlmoxarifadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estoque-almoxarifados")
    public ResponseEntity<EstoqueAlmoxarifadoDTO> updateEstoqueAlmoxarifado(@Valid @RequestBody EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to update EstoqueAlmoxarifado : {}", estoqueAlmoxarifadoDTO);
        if (estoqueAlmoxarifadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstoqueAlmoxarifadoDTO result = estoqueAlmoxarifadoService.save(estoqueAlmoxarifadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estoqueAlmoxarifadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estoque-almoxarifados} : get all the estoqueAlmoxarifados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estoqueAlmoxarifados in body.
     */
    @GetMapping("/estoque-almoxarifados")
    public ResponseEntity<List<EstoqueAlmoxarifadoDTO>> getAllEstoqueAlmoxarifados(Pageable pageable) {
        log.debug("REST request to get a page of EstoqueAlmoxarifados");
        Page<EstoqueAlmoxarifadoDTO> page = estoqueAlmoxarifadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estoque-almoxarifados/:id} : get the "id" estoqueAlmoxarifado.
     *
     * @param id the id of the estoqueAlmoxarifadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estoqueAlmoxarifadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estoque-almoxarifados/{id}")
    public ResponseEntity<EstoqueAlmoxarifadoDTO> getEstoqueAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to get EstoqueAlmoxarifado : {}", id);
        Optional<EstoqueAlmoxarifadoDTO> estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estoqueAlmoxarifadoDTO);
    }

    /**
     * {@code DELETE  /estoque-almoxarifados/:id} : delete the "id" estoqueAlmoxarifado.
     *
     * @param id the id of the estoqueAlmoxarifadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estoque-almoxarifados/{id}")
    public ResponseEntity<Void> deleteEstoqueAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to delete EstoqueAlmoxarifado : {}", id);
        estoqueAlmoxarifadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/estoque-almoxarifados?query=:query} : search for the estoqueAlmoxarifado corresponding
     * to the query.
     *
     * @param query the query of the estoqueAlmoxarifado search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/estoque-almoxarifados")
    public ResponseEntity<List<EstoqueAlmoxarifadoDTO>> searchEstoqueAlmoxarifados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EstoqueAlmoxarifados for query {}", query);
        Page<EstoqueAlmoxarifadoDTO> page = estoqueAlmoxarifadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
