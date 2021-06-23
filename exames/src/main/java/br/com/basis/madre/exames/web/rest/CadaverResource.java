package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.CadaverService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.CadaverDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link br.com.basis.madre.exames.domain.Cadaver}.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cadavers in body.
     */
    @GetMapping("/cadavers")
    public List<CadaverDTO> getAllCadavers() {
        log.debug("REST request to get all Cadavers");
        return cadaverService.findAll();
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
     * @return the result of the search.
     */
    @GetMapping("/_search/cadavers")
    public List<CadaverDTO> searchCadavers(@RequestParam String query) {
        log.debug("REST request to search Cadavers for query {}", query);
        return cadaverService.search(query);
    }
}
