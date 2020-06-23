package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.AlaService;
import br.com.basis.madre.service.dto.AlaDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Ala}.
 */
@RestController
@RequestMapping("/api")
public class AlaResource {

    private final Logger log = LoggerFactory.getLogger(AlaResource.class);

    private static final String ENTITY_NAME = "internacaoAla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlaService alaService;

    public AlaResource(AlaService alaService) {
        this.alaService = alaService;
    }

    /**
     * {@code POST  /alas} : Create a new ala.
     *
     * @param alaDTO the alaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alaDTO, or with status {@code 400 (Bad Request)} if the ala has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alas")
    public ResponseEntity<AlaDTO> createAla(@RequestBody AlaDTO alaDTO) throws URISyntaxException {
        log.debug("REST request to save Ala : {}", alaDTO);
        if (alaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ala cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlaDTO result = alaService.save(alaDTO);
        return ResponseEntity.created(new URI("/api/alas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alas} : Updates an existing ala.
     *
     * @param alaDTO the alaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alaDTO,
     * or with status {@code 400 (Bad Request)} if the alaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alas")
    public ResponseEntity<AlaDTO> updateAla(@RequestBody AlaDTO alaDTO) throws URISyntaxException {
        log.debug("REST request to update Ala : {}", alaDTO);
        if (alaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlaDTO result = alaService.save(alaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alas} : get all the alas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alas in body.
     */
    @GetMapping("/alas")
    public ResponseEntity<List<AlaDTO>> getAllAlas(Pageable pageable) {
        log.debug("REST request to get a page of Alas");
        Page<AlaDTO> page = alaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alas/:id} : get the "id" ala.
     *
     * @param id the id of the alaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alas/{id}")
    public ResponseEntity<AlaDTO> getAla(@PathVariable Long id) {
        log.debug("REST request to get Ala : {}", id);
        Optional<AlaDTO> alaDTO = alaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alaDTO);
    }

    /**
     * {@code DELETE  /alas/:id} : delete the "id" ala.
     *
     * @param id the id of the alaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alas/{id}")
    public ResponseEntity<Void> deleteAla(@PathVariable Long id) {
        log.debug("REST request to delete Ala : {}", id);
        alaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/alas?query=:query} : search for the ala corresponding
     * to the query.
     *
     * @param query the query of the ala search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/alas")
    public ResponseEntity<List<AlaDTO>> searchAlas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Alas for query {}", query);
        Page<AlaDTO> page = alaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
