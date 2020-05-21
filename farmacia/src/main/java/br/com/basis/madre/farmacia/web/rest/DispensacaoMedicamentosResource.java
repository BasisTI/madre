package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.DispensacaoMedicamentosService;
import br.com.basis.madre.farmacia.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.DispensacaoMedicamentosDTO;

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
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos}.
 */
@RestController
@RequestMapping("/api")
public class DispensacaoMedicamentosResource {

    private final Logger log = LoggerFactory.getLogger(DispensacaoMedicamentosResource.class);

    private static final String ENTITY_NAME = "farmaciaDispensacaoMedicamentos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispensacaoMedicamentosService dispensacaoMedicamentosService;

    public DispensacaoMedicamentosResource(DispensacaoMedicamentosService dispensacaoMedicamentosService) {
        this.dispensacaoMedicamentosService = dispensacaoMedicamentosService;
    }

    /**
     * {@code POST  /dispensacao-medicamentos} : Create a new dispensacaoMedicamentos.
     *
     * @param dispensacaoMedicamentosDTO the dispensacaoMedicamentosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispensacaoMedicamentosDTO, or with status {@code 400 (Bad Request)} if the dispensacaoMedicamentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispensacao-medicamentos")
    public ResponseEntity<DispensacaoMedicamentosDTO> createDispensacaoMedicamentos(@RequestBody DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO) throws URISyntaxException {
        log.debug("REST request to save DispensacaoMedicamentos : {}", dispensacaoMedicamentosDTO);
        if (dispensacaoMedicamentosDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispensacaoMedicamentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispensacaoMedicamentosDTO result = dispensacaoMedicamentosService.save(dispensacaoMedicamentosDTO);
        return ResponseEntity.created(new URI("/api/dispensacao-medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispensacao-medicamentos} : Updates an existing dispensacaoMedicamentos.
     *
     * @param dispensacaoMedicamentosDTO the dispensacaoMedicamentosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispensacaoMedicamentosDTO,
     * or with status {@code 400 (Bad Request)} if the dispensacaoMedicamentosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispensacaoMedicamentosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispensacao-medicamentos")
    public ResponseEntity<DispensacaoMedicamentosDTO> updateDispensacaoMedicamentos(@RequestBody DispensacaoMedicamentosDTO dispensacaoMedicamentosDTO) throws URISyntaxException {
        log.debug("REST request to update DispensacaoMedicamentos : {}", dispensacaoMedicamentosDTO);
        if (dispensacaoMedicamentosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DispensacaoMedicamentosDTO result = dispensacaoMedicamentosService.save(dispensacaoMedicamentosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dispensacaoMedicamentosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dispensacao-medicamentos} : get all the dispensacaoMedicamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispensacaoMedicamentos in body.
     */
    @GetMapping("/dispensacao-medicamentos")
    public ResponseEntity<List<DispensacaoMedicamentosDTO>> getAllDispensacaoMedicamentos(Pageable pageable) {
        log.debug("REST request to get a page of DispensacaoMedicamentos");
        Page<DispensacaoMedicamentosDTO> page = dispensacaoMedicamentosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispensacao-medicamentos/:id} : get the "id" dispensacaoMedicamentos.
     *
     * @param id the id of the dispensacaoMedicamentosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispensacaoMedicamentosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispensacao-medicamentos/{id}")
    public ResponseEntity<DispensacaoMedicamentosDTO> getDispensacaoMedicamentos(@PathVariable Long id) {
        log.debug("REST request to get DispensacaoMedicamentos : {}", id);
        Optional<DispensacaoMedicamentosDTO> dispensacaoMedicamentosDTO = dispensacaoMedicamentosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispensacaoMedicamentosDTO);
    }

    /**
     * {@code DELETE  /dispensacao-medicamentos/:id} : delete the "id" dispensacaoMedicamentos.
     *
     * @param id the id of the dispensacaoMedicamentosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispensacao-medicamentos/{id}")
    public ResponseEntity<Void> deleteDispensacaoMedicamentos(@PathVariable Long id) {
        log.debug("REST request to delete DispensacaoMedicamentos : {}", id);
        dispensacaoMedicamentosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/dispensacao-medicamentos?query=:query} : search for the dispensacaoMedicamentos corresponding
     * to the query.
     *
     * @param query the query of the dispensacaoMedicamentos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dispensacao-medicamentos")
    public ResponseEntity<List<DispensacaoMedicamentosDTO>> searchDispensacaoMedicamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DispensacaoMedicamentos for query {}", query);
        Page<DispensacaoMedicamentosDTO> page = dispensacaoMedicamentosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
