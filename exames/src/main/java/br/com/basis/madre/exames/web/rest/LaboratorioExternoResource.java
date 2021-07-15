package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.LaboratorioExternoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.LaboratorioExternoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.exames.domain.LaboratorioExterno}.
 */
@RestController
@RequestMapping("/api")
public class LaboratorioExternoResource {

    private final Logger log = LoggerFactory.getLogger(LaboratorioExternoResource.class);

    private static final String ENTITY_NAME = "madreexamesLaboratorioExterno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LaboratorioExternoService laboratorioExternoService;

    public LaboratorioExternoResource(LaboratorioExternoService laboratorioExternoService) {
        this.laboratorioExternoService = laboratorioExternoService;
    }

    /**
     * {@code POST  /laboratorio-externos} : Create a new laboratorioExterno.
     *
     * @param laboratorioExternoDTO the laboratorioExternoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new laboratorioExternoDTO, or with status {@code 400 (Bad Request)} if the laboratorioExterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/laboratorio-externos")
    public ResponseEntity<LaboratorioExternoDTO> createLaboratorioExterno(@Valid @RequestBody LaboratorioExternoDTO laboratorioExternoDTO) throws URISyntaxException {
        log.debug("REST request to save LaboratorioExterno : {}", laboratorioExternoDTO);
        if (laboratorioExternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new laboratorioExterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LaboratorioExternoDTO result = laboratorioExternoService.save(laboratorioExternoDTO);
        return ResponseEntity.created(new URI("/api/laboratorio-externos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /laboratorio-externos} : Updates an existing laboratorioExterno.
     *
     * @param laboratorioExternoDTO the laboratorioExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated laboratorioExternoDTO,
     * or with status {@code 400 (Bad Request)} if the laboratorioExternoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the laboratorioExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/laboratorio-externos")
    public ResponseEntity<LaboratorioExternoDTO> updateLaboratorioExterno(@Valid @RequestBody LaboratorioExternoDTO laboratorioExternoDTO) throws URISyntaxException {
        log.debug("REST request to update LaboratorioExterno : {}", laboratorioExternoDTO);
        if (laboratorioExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LaboratorioExternoDTO result = laboratorioExternoService.save(laboratorioExternoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, laboratorioExternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /laboratorio-externos} : get all the laboratorioExternos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of laboratorioExternos in body.
     */
    @GetMapping("/laboratorio-externos")
    public List<LaboratorioExternoDTO> getAllLaboratorioExternos() {
        log.debug("REST request to get all LaboratorioExternos");
        return laboratorioExternoService.findAll();
    }

    /**
     * {@code GET  /laboratorio-externos/:id} : get the "id" laboratorioExterno.
     *
     * @param id the id of the laboratorioExternoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the laboratorioExternoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/laboratorio-externos/{id}")
    public ResponseEntity<LaboratorioExternoDTO> getLaboratorioExterno(@PathVariable Long id) {
        log.debug("REST request to get LaboratorioExterno : {}", id);
        Optional<LaboratorioExternoDTO> laboratorioExternoDTO = laboratorioExternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(laboratorioExternoDTO);
    }

    /**
     * {@code DELETE  /laboratorio-externos/:id} : delete the "id" laboratorioExterno.
     *
     * @param id the id of the laboratorioExternoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/laboratorio-externos/{id}")
    public ResponseEntity<Void> deleteLaboratorioExterno(@PathVariable Long id) {
        log.debug("REST request to delete LaboratorioExterno : {}", id);
        laboratorioExternoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/laboratorio-externos?query=:query} : search for the laboratorioExterno corresponding
     * to the query.
     *
     * @param query the query of the laboratorioExterno search.
     * @return the result of the search.
     */
    @GetMapping("/_search/laboratorio-externos")
    public List<LaboratorioExternoDTO> searchLaboratorioExternos(@RequestParam String query) {
        log.debug("REST request to search LaboratorioExternos for query {}", query);
        return laboratorioExternoService.search(query);
    }
}
