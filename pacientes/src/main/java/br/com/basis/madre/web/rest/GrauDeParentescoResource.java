package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.GrauDeParentescoService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.GrauDeParentescoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.GrauDeParentesco}.
 */
@RestController
@RequestMapping("/api")
public class GrauDeParentescoResource {

    private final Logger log = LoggerFactory.getLogger(GrauDeParentescoResource.class);

    private static final String ENTITY_NAME = "snffaturaGrauDeParentesco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrauDeParentescoService grauDeParentescoService;

    public GrauDeParentescoResource(GrauDeParentescoService grauDeParentescoService) {
        this.grauDeParentescoService = grauDeParentescoService;
    }

    /**
     * {@code POST  /grau-de-parentescos} : Create a new grauDeParentesco.
     *
     * @param grauDeParentescoDTO the grauDeParentescoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grauDeParentescoDTO, or with status {@code 400 (Bad Request)} if the grauDeParentesco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grau-de-parentescos")
    public ResponseEntity<GrauDeParentescoDTO> createGrauDeParentesco(@Valid @RequestBody GrauDeParentescoDTO grauDeParentescoDTO) throws URISyntaxException {
        log.debug("REST request to save GrauDeParentesco : {}", grauDeParentescoDTO);
        if (grauDeParentescoDTO.getId() != null) {
            throw new BadRequestAlertException("A new grauDeParentesco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrauDeParentescoDTO result = grauDeParentescoService.save(grauDeParentescoDTO);
        return ResponseEntity.created(new URI("/api/grau-de-parentescos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grau-de-parentescos} : Updates an existing grauDeParentesco.
     *
     * @param grauDeParentescoDTO the grauDeParentescoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grauDeParentescoDTO,
     * or with status {@code 400 (Bad Request)} if the grauDeParentescoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grauDeParentescoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grau-de-parentescos")
    public ResponseEntity<GrauDeParentescoDTO> updateGrauDeParentesco(@Valid @RequestBody GrauDeParentescoDTO grauDeParentescoDTO) throws URISyntaxException {
        log.debug("REST request to update GrauDeParentesco : {}", grauDeParentescoDTO);
        if (grauDeParentescoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrauDeParentescoDTO result = grauDeParentescoService.save(grauDeParentescoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grauDeParentescoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grau-de-parentescos} : get all the grauDeParentescos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grauDeParentescos in body.
     */
    @GetMapping("/grau-de-parentescos")
    public ResponseEntity<List<GrauDeParentescoDTO>> getAllGrauDeParentescos(Pageable pageable) {
        log.debug("REST request to get a page of GrauDeParentescos");
        Page<GrauDeParentescoDTO> page = grauDeParentescoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grau-de-parentescos/:id} : get the "id" grauDeParentesco.
     *
     * @param id the id of the grauDeParentescoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grauDeParentescoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grau-de-parentescos/{id}")
    public ResponseEntity<GrauDeParentescoDTO> getGrauDeParentesco(@PathVariable Long id) {
        log.debug("REST request to get GrauDeParentesco : {}", id);
        Optional<GrauDeParentescoDTO> grauDeParentescoDTO = grauDeParentescoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grauDeParentescoDTO);
    }

    /**
     * {@code DELETE  /grau-de-parentescos/:id} : delete the "id" grauDeParentesco.
     *
     * @param id the id of the grauDeParentescoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grau-de-parentescos/{id}")
    public ResponseEntity<Void> deleteGrauDeParentesco(@PathVariable Long id) {
        log.debug("REST request to delete GrauDeParentesco : {}", id);
        grauDeParentescoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grau-de-parentescos?query=:query} : search for the grauDeParentesco corresponding
     * to the query.
     *
     * @param query the query of the grauDeParentesco search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grau-de-parentescos")
    public ResponseEntity<List<GrauDeParentescoDTO>> searchGrauDeParentescos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrauDeParentescos for query {}", query);
        Page<GrauDeParentescoDTO> page = grauDeParentescoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
