package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.ExameService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.ExameDTO;

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
 * REST controller for managing {@link br.com.basis.madre.exames.domain.Exame}.
 */
@RestController
@RequestMapping("/api")
public class ExameResource {

    private final Logger log = LoggerFactory.getLogger(ExameResource.class);

    private static final String ENTITY_NAME = "madreexamesExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExameService exameService;

    public ExameResource(ExameService exameService) {
        this.exameService = exameService;
    }

    /**
     * {@code POST  /exames} : Create a new exame.
     *
     * @param exameDTO the exameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exameDTO, or with status {@code 400 (Bad Request)} if the exame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exames")
    public ResponseEntity<ExameDTO> createExame(@Valid @RequestBody ExameDTO exameDTO) throws URISyntaxException {
        log.debug("REST request to save Exame : {}", exameDTO);
        if (exameDTO.getId() != null) {
            throw new BadRequestAlertException("A new exame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExameDTO result = exameService.save(exameDTO);
        return ResponseEntity.created(new URI("/api/exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exames} : Updates an existing exame.
     *
     * @param exameDTO the exameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exameDTO,
     * or with status {@code 400 (Bad Request)} if the exameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exames")
    public ResponseEntity<ExameDTO> updateExame(@Valid @RequestBody ExameDTO exameDTO) throws URISyntaxException {
        log.debug("REST request to update Exame : {}", exameDTO);
        if (exameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExameDTO result = exameService.save(exameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exames} : get all the exames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exames in body.
     */
    @GetMapping("/exames")
    public List<ExameDTO> getAllExames() {
        log.debug("REST request to get all Exames");
        return exameService.findAll();
    }

    /**
     * {@code GET  /exames/:id} : get the "id" exame.
     *
     * @param id the id of the exameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exames/{id}")
    public ResponseEntity<ExameDTO> getExame(@PathVariable Long id) {
        log.debug("REST request to get Exame : {}", id);
        Optional<ExameDTO> exameDTO = exameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exameDTO);
    }

    /**
     * {@code DELETE  /exames/:id} : delete the "id" exame.
     *
     * @param id the id of the exameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exames/{id}")
    public ResponseEntity<Void> deleteExame(@PathVariable Long id) {
        log.debug("REST request to delete Exame : {}", id);
        exameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/exames?query=:query} : search for the exame corresponding
     * to the query.
     *
     * @param query the query of the exame search.
     * @return the result of the search.
     */
    @GetMapping("/_search/exames")
    public List<ExameDTO> searchExames(@RequestParam String query) {
        log.debug("REST request to search Exames for query {}", query);
        return exameService.search(query);
    }
}
