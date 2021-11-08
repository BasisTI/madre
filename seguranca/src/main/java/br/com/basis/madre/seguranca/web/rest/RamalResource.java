package br.com.basis.madre.seguranca.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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

import br.com.basis.madre.seguranca.service.RamalService;
import br.com.basis.madre.seguranca.service.dto.RamalDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Ramal}.
 */
@RestController
@RequestMapping("/api")
public class RamalResource {

    private final Logger log = LoggerFactory.getLogger(RamalResource.class);

    private static final String ENTITY_NAME = "madresegurancaRamal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RamalService ramalService;

    public RamalResource(RamalService ramalService) {
        this.ramalService = ramalService;
    }

    /**
     * {@code POST  /ramals} : Create a new ramal.
     *
     * @param ramalDTO the ramalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ramalDTO, or with status {@code 400 (Bad Request)} if the ramal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ramals")
    public ResponseEntity<RamalDTO> createRamal(@RequestBody RamalDTO ramalDTO) throws URISyntaxException {
        log.debug("REST request to save Ramal : {}", ramalDTO);
        if (ramalDTO.getId() != null) {
            throw new BadRequestAlertException("A new ramal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RamalDTO result = ramalService.save(ramalDTO);
        return ResponseEntity.created(new URI("/api/ramals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ramals} : Updates an existing ramal.
     *
     * @param ramalDTO the ramalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramalDTO,
     * or with status {@code 400 (Bad Request)} if the ramalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ramalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ramals")
    public ResponseEntity<RamalDTO> updateRamal(@RequestBody RamalDTO ramalDTO) throws URISyntaxException {
        log.debug("REST request to update Ramal : {}", ramalDTO);
        if (ramalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RamalDTO result = ramalService.save(ramalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ramalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ramals} : get all the ramals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ramals in body.
     */
    @GetMapping("/ramals")
    public ResponseEntity<List<RamalDTO>> getAllRamals(Pageable pageable) {
        log.debug("REST request to get a page of Ramals");
        Page<RamalDTO> page = ramalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ramals/:id} : get the "id" ramal.
     *
     * @param id the id of the ramalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ramalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ramals/{id}")
    public ResponseEntity<RamalDTO> getRamal(@PathVariable Long id) {
        log.debug("REST request to get Ramal : {}", id);
        Optional<RamalDTO> ramalDTO = ramalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ramalDTO);
    }

    /**
     * {@code DELETE  /ramals/:id} : delete the "id" ramal.
     *
     * @param id the id of the ramalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ramals/{id}")
    public ResponseEntity<Void> deleteRamal(@PathVariable Long id) {
        log.debug("REST request to delete Ramal : {}", id);
        ramalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ramals?query=:query} : search for the ramal corresponding
     * to the query.
     *
     * @param query the query of the ramal search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ramals")
    public ResponseEntity<List<RamalDTO>> searchRamals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ramals for query {}", query);
        Page<RamalDTO> page = ramalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
