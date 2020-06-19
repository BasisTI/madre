package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TempoPorClasseService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.TempoPorClasse}.
 */
@RestController
@RequestMapping("/api")
public class TempoPorClasseResource {

    private final Logger log = LoggerFactory.getLogger(TempoPorClasseResource.class);

    private static final String ENTITY_NAME = "madresuprimentosTempoPorClasse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TempoPorClasseService tempoPorClasseService;

    public TempoPorClasseResource(TempoPorClasseService tempoPorClasseService) {
        this.tempoPorClasseService = tempoPorClasseService;
    }

    /**
     * {@code POST  /tempo-por-classes} : Create a new tempoPorClasse.
     *
     * @param tempoPorClasseDTO the tempoPorClasseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tempoPorClasseDTO, or with status {@code 400 (Bad Request)} if the tempoPorClasse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tempo-por-classes")
    public ResponseEntity<TempoPorClasseDTO> createTempoPorClasse(@Valid @RequestBody TempoPorClasseDTO tempoPorClasseDTO) throws URISyntaxException {
        log.debug("REST request to save TempoPorClasse : {}", tempoPorClasseDTO);
        if (tempoPorClasseDTO.getId() != null) {
            throw new BadRequestAlertException("A new tempoPorClasse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TempoPorClasseDTO result = tempoPorClasseService.save(tempoPorClasseDTO);
        return ResponseEntity.created(new URI("/api/tempo-por-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tempo-por-classes} : Updates an existing tempoPorClasse.
     *
     * @param tempoPorClasseDTO the tempoPorClasseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tempoPorClasseDTO,
     * or with status {@code 400 (Bad Request)} if the tempoPorClasseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tempoPorClasseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tempo-por-classes")
    public ResponseEntity<TempoPorClasseDTO> updateTempoPorClasse(@Valid @RequestBody TempoPorClasseDTO tempoPorClasseDTO) throws URISyntaxException {
        log.debug("REST request to update TempoPorClasse : {}", tempoPorClasseDTO);
        if (tempoPorClasseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TempoPorClasseDTO result = tempoPorClasseService.save(tempoPorClasseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tempoPorClasseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tempo-por-classes} : get all the tempoPorClasses.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tempoPorClasses in body.
     */
    @GetMapping("/tempo-por-classes")
    public ResponseEntity<List<TempoPorClasseDTO>> getAllTempoPorClasses(Pageable pageable) {
        log.debug("REST request to get a page of TempoPorClasses");
        Page<TempoPorClasseDTO> page = tempoPorClasseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tempo-por-classes/:id} : get the "id" tempoPorClasse.
     *
     * @param id the id of the tempoPorClasseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tempoPorClasseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tempo-por-classes/{id}")
    public ResponseEntity<TempoPorClasseDTO> getTempoPorClasse(@PathVariable Long id) {
        log.debug("REST request to get TempoPorClasse : {}", id);
        Optional<TempoPorClasseDTO> tempoPorClasseDTO = tempoPorClasseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tempoPorClasseDTO);
    }

    /**
     * {@code DELETE  /tempo-por-classes/:id} : delete the "id" tempoPorClasse.
     *
     * @param id the id of the tempoPorClasseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tempo-por-classes/{id}")
    public ResponseEntity<Void> deleteTempoPorClasse(@PathVariable Long id) {
        log.debug("REST request to delete TempoPorClasse : {}", id);
        tempoPorClasseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tempo-por-classes?query=:query} : search for the tempoPorClasse corresponding
     * to the query.
     *
     * @param query the query of the tempoPorClasse search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tempo-por-classes")
    public ResponseEntity<List<TempoPorClasseDTO>> searchTempoPorClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TempoPorClasses for query {}", query);
        Page<TempoPorClasseDTO> page = tempoPorClasseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
