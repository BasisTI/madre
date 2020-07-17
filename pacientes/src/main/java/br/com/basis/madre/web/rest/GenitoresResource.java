package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.GenitoresService;
import br.com.basis.madre.service.dto.GenitoresDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Genitores}.
 */
@RestController
@RequestMapping("/api")
public class GenitoresResource {

    private final Logger log = LoggerFactory.getLogger(GenitoresResource.class);

    private static final String ENTITY_NAME = "pacientesGenitores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenitoresService genitoresService;

    public GenitoresResource(GenitoresService genitoresService) {
        this.genitoresService = genitoresService;
    }

    /**
     * {@code POST  /genitores} : Create a new genitores.
     *
     * @param genitoresDTO the genitoresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genitoresDTO, or with status {@code 400 (Bad Request)} if the genitores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/genitores")
    public ResponseEntity<GenitoresDTO> createGenitores(@Valid @RequestBody GenitoresDTO genitoresDTO) throws URISyntaxException {
        log.debug("REST request to save Genitores : {}", genitoresDTO);
        if (genitoresDTO.getId() != null) {
            throw new BadRequestAlertException("A new genitores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenitoresDTO result = genitoresService.save(genitoresDTO);
        return ResponseEntity.created(new URI("/api/genitores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /genitores} : Updates an existing genitores.
     *
     * @param genitoresDTO the genitoresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genitoresDTO,
     * or with status {@code 400 (Bad Request)} if the genitoresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genitoresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/genitores")
    public ResponseEntity<GenitoresDTO> updateGenitores(@Valid @RequestBody GenitoresDTO genitoresDTO) throws URISyntaxException {
        log.debug("REST request to update Genitores : {}", genitoresDTO);
        if (genitoresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GenitoresDTO result = genitoresService.save(genitoresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, genitoresDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /genitores} : get all the genitores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genitores in body.
     */
    @GetMapping("/genitores")
    public ResponseEntity<List<GenitoresDTO>> getAllGenitores(Pageable pageable) {
        log.debug("REST request to get a page of Genitores");
        Page<GenitoresDTO> page = genitoresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /genitores/:id} : get the "id" genitores.
     *
     * @param id the id of the genitoresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genitoresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/genitores/{id}")
    public ResponseEntity<GenitoresDTO> getGenitores(@PathVariable Long id) {
        log.debug("REST request to get Genitores : {}", id);
        Optional<GenitoresDTO> genitoresDTO = genitoresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genitoresDTO);
    }

    /**
     * {@code DELETE  /genitores/:id} : delete the "id" genitores.
     *
     * @param id the id of the genitoresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/genitores/{id}")
    public ResponseEntity<Void> deleteGenitores(@PathVariable Long id) {
        log.debug("REST request to delete Genitores : {}", id);
        genitoresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/genitores?query=:query} : search for the genitores corresponding
     * to the query.
     *
     * @param query the query of the genitores search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/genitores")
    public ResponseEntity<List<GenitoresDTO>> searchGenitores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Genitores for query {}", query);
        Page<GenitoresDTO> page = genitoresService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
