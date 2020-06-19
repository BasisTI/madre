package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TipoResiduoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.TipoResiduoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.TipoResiduo}.
 */
@RestController
@RequestMapping("/api")
public class TipoResiduoResource {

    private final Logger log = LoggerFactory.getLogger(TipoResiduoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosTipoResiduo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoResiduoService tipoResiduoService;

    public TipoResiduoResource(TipoResiduoService tipoResiduoService) {
        this.tipoResiduoService = tipoResiduoService;
    }

    /**
     * {@code POST  /tipo-residuos} : Create a new tipoResiduo.
     *
     * @param tipoResiduoDTO the tipoResiduoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoResiduoDTO, or with status {@code 400 (Bad Request)} if the tipoResiduo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-residuos")
    public ResponseEntity<TipoResiduoDTO> createTipoResiduo(@Valid @RequestBody TipoResiduoDTO tipoResiduoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoResiduo : {}", tipoResiduoDTO);
        if (tipoResiduoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoResiduo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoResiduoDTO result = tipoResiduoService.save(tipoResiduoDTO);
        return ResponseEntity.created(new URI("/api/tipo-residuos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-residuos} : Updates an existing tipoResiduo.
     *
     * @param tipoResiduoDTO the tipoResiduoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoResiduoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoResiduoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoResiduoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-residuos")
    public ResponseEntity<TipoResiduoDTO> updateTipoResiduo(@Valid @RequestBody TipoResiduoDTO tipoResiduoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoResiduo : {}", tipoResiduoDTO);
        if (tipoResiduoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoResiduoDTO result = tipoResiduoService.save(tipoResiduoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoResiduoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-residuos} : get all the tipoResiduos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoResiduos in body.
     */
    @GetMapping("/tipo-residuos")
    public ResponseEntity<List<TipoResiduoDTO>> getAllTipoResiduos(Pageable pageable) {
        log.debug("REST request to get a page of TipoResiduos");
        Page<TipoResiduoDTO> page = tipoResiduoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-residuos/:id} : get the "id" tipoResiduo.
     *
     * @param id the id of the tipoResiduoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoResiduoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-residuos/{id}")
    public ResponseEntity<TipoResiduoDTO> getTipoResiduo(@PathVariable Long id) {
        log.debug("REST request to get TipoResiduo : {}", id);
        Optional<TipoResiduoDTO> tipoResiduoDTO = tipoResiduoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoResiduoDTO);
    }

    /**
     * {@code DELETE  /tipo-residuos/:id} : delete the "id" tipoResiduo.
     *
     * @param id the id of the tipoResiduoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-residuos/{id}")
    public ResponseEntity<Void> deleteTipoResiduo(@PathVariable Long id) {
        log.debug("REST request to delete TipoResiduo : {}", id);
        tipoResiduoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-residuos?query=:query} : search for the tipoResiduo corresponding
     * to the query.
     *
     * @param query the query of the tipoResiduo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-residuos")
    public ResponseEntity<List<TipoResiduoDTO>> searchTipoResiduos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoResiduos for query {}", query);
        Page<TipoResiduoDTO> page = tipoResiduoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
