package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.EstadoCivilService;
import br.com.basis.madre.service.dto.EstadoCivilDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.EstadoCivil}.
 */
@RestController
@RequestMapping("/api")
public class EstadoCivilResource {

    private final Logger log = LoggerFactory.getLogger(EstadoCivilResource.class);

    private static final String ENTITY_NAME = "pacientesEstadoCivil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoCivilService estadoCivilService;

    public EstadoCivilResource(EstadoCivilService estadoCivilService) {
        this.estadoCivilService = estadoCivilService;
    }

    /**
     * {@code POST  /estado-civils} : Create a new estadoCivil.
     *
     * @param estadoCivilDTO the estadoCivilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoCivilDTO, or with status {@code 400 (Bad Request)} if the estadoCivil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-civils")
    public ResponseEntity<EstadoCivilDTO> createEstadoCivil(@Valid @RequestBody EstadoCivilDTO estadoCivilDTO) throws URISyntaxException {
        log.debug("REST request to save EstadoCivil : {}", estadoCivilDTO);
        if (estadoCivilDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoCivil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoCivilDTO result = estadoCivilService.save(estadoCivilDTO);
        return ResponseEntity.created(new URI("/api/estado-civils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-civils} : Updates an existing estadoCivil.
     *
     * @param estadoCivilDTO the estadoCivilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoCivilDTO,
     * or with status {@code 400 (Bad Request)} if the estadoCivilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoCivilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-civils")
    public ResponseEntity<EstadoCivilDTO> updateEstadoCivil(@Valid @RequestBody EstadoCivilDTO estadoCivilDTO) throws URISyntaxException {
        log.debug("REST request to update EstadoCivil : {}", estadoCivilDTO);
        if (estadoCivilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoCivilDTO result = estadoCivilService.save(estadoCivilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estadoCivilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-civils} : get all the estadoCivils.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoCivils in body.
     */
    @GetMapping("/estado-civils")
    public ResponseEntity<List<EstadoCivilDTO>> getAllEstadoCivils(EstadoCivilDTO estadoCivilDTO, Pageable pageable) {
        log.debug("REST request to get a page of EstadoCivils");
        Page<EstadoCivilDTO> page = estadoCivilService.findAll(estadoCivilDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-civils/:id} : get the "id" estadoCivil.
     *
     * @param id the id of the estadoCivilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoCivilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-civils/{id}")
    public ResponseEntity<EstadoCivilDTO> getEstadoCivil(@PathVariable Long id) {
        log.debug("REST request to get EstadoCivil : {}", id);
        Optional<EstadoCivilDTO> estadoCivilDTO = estadoCivilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoCivilDTO);
    }

    /**
     * {@code DELETE  /estado-civils/:id} : delete the "id" estadoCivil.
     *
     * @param id the id of the estadoCivilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-civils/{id}")
    public ResponseEntity<Void> deleteEstadoCivil(@PathVariable Long id) {
        log.debug("REST request to delete EstadoCivil : {}", id);
        estadoCivilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/estado-civils?query=:query} : search for the estadoCivil corresponding
     * to the query.
     *
     * @param query the query of the estadoCivil search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/estado-civils")
    public ResponseEntity<List<EstadoCivilDTO>> searchEstadoCivils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EstadoCivils for query {}", query);
        Page<EstadoCivilDTO> page = estadoCivilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
