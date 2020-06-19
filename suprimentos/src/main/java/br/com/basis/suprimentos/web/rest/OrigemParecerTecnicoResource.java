package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.OrigemParecerTecnicoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.OrigemParecerTecnicoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.OrigemParecerTecnico}.
 */
@RestController
@RequestMapping("/api")
public class OrigemParecerTecnicoResource {

    private final Logger log = LoggerFactory.getLogger(OrigemParecerTecnicoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosOrigemParecerTecnico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrigemParecerTecnicoService origemParecerTecnicoService;

    public OrigemParecerTecnicoResource(OrigemParecerTecnicoService origemParecerTecnicoService) {
        this.origemParecerTecnicoService = origemParecerTecnicoService;
    }

    /**
     * {@code POST  /origem-parecer-tecnicos} : Create a new origemParecerTecnico.
     *
     * @param origemParecerTecnicoDTO the origemParecerTecnicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new origemParecerTecnicoDTO, or with status {@code 400 (Bad Request)} if the origemParecerTecnico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origem-parecer-tecnicos")
    public ResponseEntity<OrigemParecerTecnicoDTO> createOrigemParecerTecnico(@Valid @RequestBody OrigemParecerTecnicoDTO origemParecerTecnicoDTO) throws URISyntaxException {
        log.debug("REST request to save OrigemParecerTecnico : {}", origemParecerTecnicoDTO);
        if (origemParecerTecnicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new origemParecerTecnico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrigemParecerTecnicoDTO result = origemParecerTecnicoService.save(origemParecerTecnicoDTO);
        return ResponseEntity.created(new URI("/api/origem-parecer-tecnicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origem-parecer-tecnicos} : Updates an existing origemParecerTecnico.
     *
     * @param origemParecerTecnicoDTO the origemParecerTecnicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated origemParecerTecnicoDTO,
     * or with status {@code 400 (Bad Request)} if the origemParecerTecnicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the origemParecerTecnicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origem-parecer-tecnicos")
    public ResponseEntity<OrigemParecerTecnicoDTO> updateOrigemParecerTecnico(@Valid @RequestBody OrigemParecerTecnicoDTO origemParecerTecnicoDTO) throws URISyntaxException {
        log.debug("REST request to update OrigemParecerTecnico : {}", origemParecerTecnicoDTO);
        if (origemParecerTecnicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrigemParecerTecnicoDTO result = origemParecerTecnicoService.save(origemParecerTecnicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, origemParecerTecnicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /origem-parecer-tecnicos} : get all the origemParecerTecnicos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of origemParecerTecnicos in body.
     */
    @GetMapping("/origem-parecer-tecnicos")
    public ResponseEntity<List<OrigemParecerTecnicoDTO>> getAllOrigemParecerTecnicos(Pageable pageable) {
        log.debug("REST request to get a page of OrigemParecerTecnicos");
        Page<OrigemParecerTecnicoDTO> page = origemParecerTecnicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /origem-parecer-tecnicos/:id} : get the "id" origemParecerTecnico.
     *
     * @param id the id of the origemParecerTecnicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the origemParecerTecnicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origem-parecer-tecnicos/{id}")
    public ResponseEntity<OrigemParecerTecnicoDTO> getOrigemParecerTecnico(@PathVariable Long id) {
        log.debug("REST request to get OrigemParecerTecnico : {}", id);
        Optional<OrigemParecerTecnicoDTO> origemParecerTecnicoDTO = origemParecerTecnicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(origemParecerTecnicoDTO);
    }

    /**
     * {@code DELETE  /origem-parecer-tecnicos/:id} : delete the "id" origemParecerTecnico.
     *
     * @param id the id of the origemParecerTecnicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origem-parecer-tecnicos/{id}")
    public ResponseEntity<Void> deleteOrigemParecerTecnico(@PathVariable Long id) {
        log.debug("REST request to delete OrigemParecerTecnico : {}", id);
        origemParecerTecnicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/origem-parecer-tecnicos?query=:query} : search for the origemParecerTecnico corresponding
     * to the query.
     *
     * @param query the query of the origemParecerTecnico search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/origem-parecer-tecnicos")
    public ResponseEntity<List<OrigemParecerTecnicoDTO>> searchOrigemParecerTecnicos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrigemParecerTecnicos for query {}", query);
        Page<OrigemParecerTecnicoDTO> page = origemParecerTecnicoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
