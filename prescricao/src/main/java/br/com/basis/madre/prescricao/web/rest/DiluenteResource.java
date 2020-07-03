package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.DiluenteService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.DiluenteDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.Diluente}.
 */
@RestController
@RequestMapping("/api")
public class DiluenteResource {

    private final Logger log = LoggerFactory.getLogger(DiluenteResource.class);

    private static final String ENTITY_NAME = "prescricaoDiluente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiluenteService diluenteService;

    public DiluenteResource(DiluenteService diluenteService) {
        this.diluenteService = diluenteService;
    }

    /**
     * {@code POST  /diluentes} : Create a new diluente.
     *
     * @param diluenteDTO the diluenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diluenteDTO, or with status {@code 400 (Bad Request)} if the diluente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diluentes")
    public ResponseEntity<DiluenteDTO> createDiluente(@Valid @RequestBody DiluenteDTO diluenteDTO) throws URISyntaxException {
        log.debug("REST request to save Diluente : {}", diluenteDTO);
        if (diluenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new diluente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiluenteDTO result = diluenteService.save(diluenteDTO);
        return ResponseEntity.created(new URI("/api/diluentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diluentes} : Updates an existing diluente.
     *
     * @param diluenteDTO the diluenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diluenteDTO,
     * or with status {@code 400 (Bad Request)} if the diluenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diluenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diluentes")
    public ResponseEntity<DiluenteDTO> updateDiluente(@Valid @RequestBody DiluenteDTO diluenteDTO) throws URISyntaxException {
        log.debug("REST request to update Diluente : {}", diluenteDTO);
        if (diluenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiluenteDTO result = diluenteService.save(diluenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, diluenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /diluentes} : get all the diluentes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diluentes in body.
     */
    @GetMapping("/diluentes")
    public ResponseEntity<List<DiluenteDTO>> getAllDiluentes(Pageable pageable) {
        log.debug("REST request to get a page of Diluentes");
        Page<DiluenteDTO> page = diluenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diluentes/:id} : get the "id" diluente.
     *
     * @param id the id of the diluenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diluenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diluentes/{id}")
    public ResponseEntity<DiluenteDTO> getDiluente(@PathVariable Long id) {
        log.debug("REST request to get Diluente : {}", id);
        Optional<DiluenteDTO> diluenteDTO = diluenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diluenteDTO);
    }

    /**
     * {@code DELETE  /diluentes/:id} : delete the "id" diluente.
     *
     * @param id the id of the diluenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diluentes/{id}")
    public ResponseEntity<Void> deleteDiluente(@PathVariable Long id) {
        log.debug("REST request to delete Diluente : {}", id);
        diluenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/diluentes?query=:query} : search for the diluente corresponding
     * to the query.
     *
     * @param query the query of the diluente search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/diluentes")
    public ResponseEntity<List<DiluenteDTO>> searchDiluentes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Diluentes for query {}", query);
        Page<DiluenteDTO> page = diluenteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
