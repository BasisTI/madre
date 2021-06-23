package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.SinonimoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.SinonimoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.exames.domain.Sinonimo}.
 */
@RestController
@RequestMapping("/api")
public class SinonimoResource {

    private final Logger log = LoggerFactory.getLogger(SinonimoResource.class);

    private static final String ENTITY_NAME = "madreexamesSinonimo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SinonimoService sinonimoService;

    public SinonimoResource(SinonimoService sinonimoService) {
        this.sinonimoService = sinonimoService;
    }

    /**
     * {@code POST  /sinonimos} : Create a new sinonimo.
     *
     * @param sinonimoDTO the sinonimoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sinonimoDTO, or with status {@code 400 (Bad Request)} if the sinonimo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sinonimos")
    public ResponseEntity<SinonimoDTO> createSinonimo(@Valid @RequestBody SinonimoDTO sinonimoDTO) throws URISyntaxException {
        log.debug("REST request to save Sinonimo : {}", sinonimoDTO);
        if (sinonimoDTO.getId() != null) {
            throw new BadRequestAlertException("A new sinonimo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SinonimoDTO result = sinonimoService.save(sinonimoDTO);
        return ResponseEntity.created(new URI("/api/sinonimos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sinonimos} : Updates an existing sinonimo.
     *
     * @param sinonimoDTO the sinonimoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sinonimoDTO,
     * or with status {@code 400 (Bad Request)} if the sinonimoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sinonimoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sinonimos")
    public ResponseEntity<SinonimoDTO> updateSinonimo(@Valid @RequestBody SinonimoDTO sinonimoDTO) throws URISyntaxException {
        log.debug("REST request to update Sinonimo : {}", sinonimoDTO);
        if (sinonimoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SinonimoDTO result = sinonimoService.save(sinonimoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sinonimoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sinonimos} : get all the sinonimos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sinonimos in body.
     */
    @GetMapping("/sinonimos")
    public List<SinonimoDTO> getAllSinonimos() {
        log.debug("REST request to get all Sinonimos");
        return sinonimoService.findAll();
    }

    /**
     * {@code GET  /sinonimos/:id} : get the "id" sinonimo.
     *
     * @param id the id of the sinonimoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sinonimoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sinonimos/{id}")
    public ResponseEntity<SinonimoDTO> getSinonimo(@PathVariable Long id) {
        log.debug("REST request to get Sinonimo : {}", id);
        Optional<SinonimoDTO> sinonimoDTO = sinonimoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sinonimoDTO);
    }

    /**
     * {@code DELETE  /sinonimos/:id} : delete the "id" sinonimo.
     *
     * @param id the id of the sinonimoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sinonimos/{id}")
    public ResponseEntity<Void> deleteSinonimo(@PathVariable Long id) {
        log.debug("REST request to delete Sinonimo : {}", id);
        sinonimoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/sinonimos?query=:query} : search for the sinonimo corresponding
     * to the query.
     *
     * @param query the query of the sinonimo search.
     * @return the result of the search.
     */
    @GetMapping("/_search/sinonimos")
    public List<SinonimoDTO> searchSinonimos(@RequestParam String query) {
        log.debug("REST request to search Sinonimos for query {}", query);
        return sinonimoService.search(query);
    }
}
