package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.CadaverService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.CadaverDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.Cadaver}.
 */
@RestController
@RequestMapping("/api")
public class CadaverResource {

    private final Logger log = LoggerFactory.getLogger(CadaverResource.class);

    private static final String ENTITY_NAME = "madreexamesCadaver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CadaverService cadaverService;

    public CadaverResource(CadaverService cadaverService) {
        this.cadaverService = cadaverService;
    }

    /**
     * {@code POST  /cadavers} : Create a new cadaver.
     *
     * @param cadaverDTO the cadaverDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cadaverDTO, or with status {@code 400 (Bad Request)} if the cadaver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cadavers")
    public ResponseEntity<CadaverDTO> createCadaver(@Valid @RequestBody CadaverDTO cadaverDTO) throws URISyntaxException {
        log.debug("REST request to save Cadaver : {}", cadaverDTO);
        if (cadaverDTO.getId() != null) {
            throw new BadRequestAlertException("A new cadaver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CadaverDTO result = cadaverService.save(cadaverDTO);
        return ResponseEntity.created(new URI("/api/cadavers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cadavers} : Updates an existing cadaver.
     *
     * @param cadaverDTO the cadaverDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cadaverDTO,
     * or with status {@code 400 (Bad Request)} if the cadaverDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cadaverDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cadavers")
    public ResponseEntity<CadaverDTO> updateCadaver(@Valid @RequestBody CadaverDTO cadaverDTO) throws URISyntaxException {
        log.debug("REST request to update Cadaver : {}", cadaverDTO);
        if (cadaverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CadaverDTO result = cadaverService.save(cadaverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cadaverDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cadavers} : get all the cadavers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cadavers in body.
     */
    @GetMapping("/cadavers")
    public ResponseEntity<List<CadaverDTO>> getAllCadavers(Pageable pageable) {
        log.debug("REST request to get a page of Cadavers");
        Page<CadaverDTO> page = cadaverService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cadavers/:id} : get the "id" cadaver.
     *
     * @param id the id of the cadaverDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cadaverDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cadavers/{id}")
    public ResponseEntity<CadaverDTO> getCadaver(@PathVariable Long id) {
        log.debug("REST request to get Cadaver : {}", id);
        Optional<CadaverDTO> cadaverDTO = cadaverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cadaverDTO);
    }

    /**
     * {@code DELETE  /cadavers/:id} : delete the "id" cadaver.
     *
     * @param id the id of the cadaverDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cadavers/{id}")
    public ResponseEntity<Void> deleteCadaver(@PathVariable Long id) {
        log.debug("REST request to delete Cadaver : {}", id);
        cadaverService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cadavers?query=:query} : search for the cadaver corresponding
     * to the query.
     *
     * @param query the query of the cadaver search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cadavers")
    public ResponseEntity<List<CadaverDTO>> searchCadavers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cadavers for query {}", query);
        Page<CadaverDTO> page = cadaverService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
