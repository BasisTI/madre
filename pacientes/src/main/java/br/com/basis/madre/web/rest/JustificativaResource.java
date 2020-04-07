package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.JustificativaService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.JustificativaDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Justificativa}.
 */
@RestController
@RequestMapping("/api")
public class JustificativaResource {

    private final Logger log = LoggerFactory.getLogger(JustificativaResource.class);

    private static final String ENTITY_NAME = "pacientesJustificativa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JustificativaService justificativaService;

    public JustificativaResource(JustificativaService justificativaService) {
        this.justificativaService = justificativaService;
    }

    /**
     * {@code POST  /justificativas} : Create a new justificativa.
     *
     * @param justificativaDTO the justificativaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new justificativaDTO, or with status {@code 400 (Bad Request)} if the justificativa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/justificativas")
    public ResponseEntity<JustificativaDTO> createJustificativa(@Valid @RequestBody JustificativaDTO justificativaDTO) throws URISyntaxException {
        log.debug("REST request to save Justificativa : {}", justificativaDTO);
        if (justificativaDTO.getId() != null) {
            throw new BadRequestAlertException("A new justificativa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JustificativaDTO result = justificativaService.save(justificativaDTO);
        return ResponseEntity.created(new URI("/api/justificativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /justificativas} : Updates an existing justificativa.
     *
     * @param justificativaDTO the justificativaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated justificativaDTO,
     * or with status {@code 400 (Bad Request)} if the justificativaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the justificativaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/justificativas")
    public ResponseEntity<JustificativaDTO> updateJustificativa(@Valid @RequestBody JustificativaDTO justificativaDTO) throws URISyntaxException {
        log.debug("REST request to update Justificativa : {}", justificativaDTO);
        if (justificativaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JustificativaDTO result = justificativaService.save(justificativaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, justificativaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /justificativas} : get all the justificativas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of justificativas in body.
     */
    @GetMapping("/justificativas")
    public ResponseEntity<List<JustificativaDTO>> getAllJustificativas(Pageable pageable) {
        log.debug("REST request to get a page of Justificativas");
        Page<JustificativaDTO> page = justificativaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /justificativas/:id} : get the "id" justificativa.
     *
     * @param id the id of the justificativaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the justificativaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/justificativas/{id}")
    public ResponseEntity<JustificativaDTO> getJustificativa(@PathVariable Long id) {
        log.debug("REST request to get Justificativa : {}", id);
        Optional<JustificativaDTO> justificativaDTO = justificativaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(justificativaDTO);
    }

    /**
     * {@code DELETE  /justificativas/:id} : delete the "id" justificativa.
     *
     * @param id the id of the justificativaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/justificativas/{id}")
    public ResponseEntity<Void> deleteJustificativa(@PathVariable Long id) {
        log.debug("REST request to delete Justificativa : {}", id);
        justificativaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/justificativas?query=:query} : search for the justificativa corresponding
     * to the query.
     *
     * @param query the query of the justificativa search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/justificativas")
    public ResponseEntity<List<JustificativaDTO>> searchJustificativas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Justificativas for query {}", query);
        Page<JustificativaDTO> page = justificativaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
