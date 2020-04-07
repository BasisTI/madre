package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ResponsavelService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.ResponsavelDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Responsavel}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelResource.class);

    private static final String ENTITY_NAME = "pacientesResponsavel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelService responsavelService;

    public ResponsavelResource(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    /**
     * {@code POST  /responsavels} : Create a new responsavel.
     *
     * @param responsavelDTO the responsavelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavelDTO, or with status {@code 400 (Bad Request)} if the responsavel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavels")
    public ResponseEntity<ResponsavelDTO> createResponsavel(@RequestBody ResponsavelDTO responsavelDTO) throws URISyntaxException {
        log.debug("REST request to save Responsavel : {}", responsavelDTO);
        if (responsavelDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsavel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsavelDTO result = responsavelService.save(responsavelDTO);
        return ResponseEntity.created(new URI("/api/responsavels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavels} : Updates an existing responsavel.
     *
     * @param responsavelDTO the responsavelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavelDTO,
     * or with status {@code 400 (Bad Request)} if the responsavelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavels")
    public ResponseEntity<ResponsavelDTO> updateResponsavel(@RequestBody ResponsavelDTO responsavelDTO) throws URISyntaxException {
        log.debug("REST request to update Responsavel : {}", responsavelDTO);
        if (responsavelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResponsavelDTO result = responsavelService.save(responsavelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responsavelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /responsavels} : get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavels in body.
     */
    @GetMapping("/responsavels")
    public ResponseEntity<List<ResponsavelDTO>> getAllResponsavels(Pageable pageable) {
        log.debug("REST request to get a page of Responsavels");
        Page<ResponsavelDTO> page = responsavelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavels/:id} : get the "id" responsavel.
     *
     * @param id the id of the responsavelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavels/{id}")
    public ResponseEntity<ResponsavelDTO> getResponsavel(@PathVariable Long id) {
        log.debug("REST request to get Responsavel : {}", id);
        Optional<ResponsavelDTO> responsavelDTO = responsavelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavelDTO);
    }

    /**
     * {@code DELETE  /responsavels/:id} : delete the "id" responsavel.
     *
     * @param id the id of the responsavelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavels/{id}")
    public ResponseEntity<Void> deleteResponsavel(@PathVariable Long id) {
        log.debug("REST request to delete Responsavel : {}", id);
        responsavelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/responsavels?query=:query} : search for the responsavel corresponding
     * to the query.
     *
     * @param query the query of the responsavel search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/responsavels")
    public ResponseEntity<List<ResponsavelDTO>> searchResponsavels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Responsavels for query {}", query);
        Page<ResponsavelDTO> page = responsavelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
