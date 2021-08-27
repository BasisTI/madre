package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.RecipienteService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.RecipienteDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.Recipiente}.
 */
@RestController
@RequestMapping("/api")
public class RecipienteResource {

    private final Logger log = LoggerFactory.getLogger(RecipienteResource.class);

    private static final String ENTITY_NAME = "madreexamesRecipiente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipienteService recipienteService;

    public RecipienteResource(RecipienteService recipienteService) {
        this.recipienteService = recipienteService;
    }

    /**
     * {@code POST  /recipientes} : Create a new recipiente.
     *
     * @param recipienteDTO the recipienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipienteDTO, or with status {@code 400 (Bad Request)} if the recipiente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipientes")
    public ResponseEntity<RecipienteDTO> createRecipiente(@Valid @RequestBody RecipienteDTO recipienteDTO) throws URISyntaxException {
        log.debug("REST request to save Recipiente : {}", recipienteDTO);
        if (recipienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipienteDTO result = recipienteService.save(recipienteDTO);
        return ResponseEntity.created(new URI("/api/recipientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipientes} : Updates an existing recipiente.
     *
     * @param recipienteDTO the recipienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipienteDTO,
     * or with status {@code 400 (Bad Request)} if the recipienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipientes")
    public ResponseEntity<RecipienteDTO> updateRecipiente(@Valid @RequestBody RecipienteDTO recipienteDTO) throws URISyntaxException {
        log.debug("REST request to update Recipiente : {}", recipienteDTO);
        if (recipienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipienteDTO result = recipienteService.save(recipienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recipienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recipientes} : get all the recipientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipientes in body.
     */
    @GetMapping("/recipientes")
    public ResponseEntity<List<RecipienteDTO>> getAllRecipientes(Pageable pageable) {
        log.debug("REST request to get a page of Recipientes");
        Page<RecipienteDTO> page = recipienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recipientes/:id} : get the "id" recipiente.
     *
     * @param id the id of the recipienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipientes/{id}")
    public ResponseEntity<RecipienteDTO> getRecipiente(@PathVariable Long id) {
        log.debug("REST request to get Recipiente : {}", id);
        Optional<RecipienteDTO> recipienteDTO = recipienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipienteDTO);
    }

    /**
     * {@code DELETE  /recipientes/:id} : delete the "id" recipiente.
     *
     * @param id the id of the recipienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipientes/{id}")
    public ResponseEntity<Void> deleteRecipiente(@PathVariable Long id) {
        log.debug("REST request to delete Recipiente : {}", id);
        recipienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/recipientes?query=:query} : search for the recipiente corresponding
     * to the query.
     *
     * @param query the query of the recipiente search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/recipientes")
    public ResponseEntity<List<RecipienteDTO>> searchRecipientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recipientes for query {}", query);
        Page<RecipienteDTO> page = recipienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
