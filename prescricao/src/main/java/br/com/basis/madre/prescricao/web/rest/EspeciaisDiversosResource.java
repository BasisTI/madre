package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.EspeciaisDiversosService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.EspeciaisDiversosDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.EspeciaisDiversos}.
 */
@RestController
@RequestMapping("/api")
public class EspeciaisDiversosResource {

    private final Logger log = LoggerFactory.getLogger(EspeciaisDiversosResource.class);

    private static final String ENTITY_NAME = "prescricaoEspeciaisDiversos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspeciaisDiversosService especiaisDiversosService;

    public EspeciaisDiversosResource(EspeciaisDiversosService especiaisDiversosService) {
        this.especiaisDiversosService = especiaisDiversosService;
    }

    /**
     * {@code POST  /especiais-diversos} : Create a new especiaisDiversos.
     *
     * @param especiaisDiversosDTO the especiaisDiversosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especiaisDiversosDTO, or with status {@code 400 (Bad Request)} if the especiaisDiversos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/especiais-diversos")
    public ResponseEntity<EspeciaisDiversosDTO> createEspeciaisDiversos(@Valid @RequestBody EspeciaisDiversosDTO especiaisDiversosDTO) throws URISyntaxException {
        log.debug("REST request to save EspeciaisDiversos : {}", especiaisDiversosDTO);
        if (especiaisDiversosDTO.getId() != null) {
            throw new BadRequestAlertException("A new especiaisDiversos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspeciaisDiversosDTO result = especiaisDiversosService.save(especiaisDiversosDTO);
        return ResponseEntity.created(new URI("/api/especiais-diversos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /especiais-diversos} : Updates an existing especiaisDiversos.
     *
     * @param especiaisDiversosDTO the especiaisDiversosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especiaisDiversosDTO,
     * or with status {@code 400 (Bad Request)} if the especiaisDiversosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especiaisDiversosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/especiais-diversos")
    public ResponseEntity<EspeciaisDiversosDTO> updateEspeciaisDiversos(@Valid @RequestBody EspeciaisDiversosDTO especiaisDiversosDTO) throws URISyntaxException {
        log.debug("REST request to update EspeciaisDiversos : {}", especiaisDiversosDTO);
        if (especiaisDiversosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EspeciaisDiversosDTO result = especiaisDiversosService.save(especiaisDiversosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, especiaisDiversosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /especiais-diversos} : get all the especiaisDiversos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especiaisDiversos in body.
     */
    @GetMapping("/especiais-diversos")
    public ResponseEntity<List<EspeciaisDiversosDTO>> getAllEspeciaisDiversos(Pageable pageable) {
        log.debug("REST request to get a page of EspeciaisDiversos");
        Page<EspeciaisDiversosDTO> page = especiaisDiversosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especiais-diversos/:id} : get the "id" especiaisDiversos.
     *
     * @param id the id of the especiaisDiversosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especiaisDiversosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/especiais-diversos/{id}")
    public ResponseEntity<EspeciaisDiversosDTO> getEspeciaisDiversos(@PathVariable Long id) {
        log.debug("REST request to get EspeciaisDiversos : {}", id);
        Optional<EspeciaisDiversosDTO> especiaisDiversosDTO = especiaisDiversosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especiaisDiversosDTO);
    }

    /**
     * {@code DELETE  /especiais-diversos/:id} : delete the "id" especiaisDiversos.
     *
     * @param id the id of the especiaisDiversosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/especiais-diversos/{id}")
    public ResponseEntity<Void> deleteEspeciaisDiversos(@PathVariable Long id) {
        log.debug("REST request to delete EspeciaisDiversos : {}", id);
        especiaisDiversosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/especiais-diversos?query=:query} : search for the especiaisDiversos corresponding
     * to the query.
     *
     * @param query the query of the especiaisDiversos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/especiais-diversos")
    public ResponseEntity<List<EspeciaisDiversosDTO>> searchEspeciaisDiversos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EspeciaisDiversos for query {}", query);
        Page<EspeciaisDiversosDTO> page = especiaisDiversosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
