package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.RacaService;
import br.com.basis.madre.service.dto.RacaDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Raca}.
 */
@RestController
@RequestMapping("/api")
public class RacaResource {

    private final Logger log = LoggerFactory.getLogger(RacaResource.class);

    private static final String ENTITY_NAME = "pacientesRaca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacaService racaService;

    public RacaResource(RacaService racaService) {
        this.racaService = racaService;
    }

    /**
     * {@code POST  /racas} : Create a new raca.
     *
     * @param racaDTO the racaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racaDTO, or with status {@code 400 (Bad Request)} if the raca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racas")
    public ResponseEntity<RacaDTO> createRaca(@Valid @RequestBody RacaDTO racaDTO) throws URISyntaxException {
        log.debug("REST request to save Raca : {}", racaDTO);
        if (racaDTO.getId() != null) {
            throw new BadRequestAlertException("A new raca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacaDTO result = racaService.save(racaDTO);
        return ResponseEntity.created(new URI("/api/racas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racas} : Updates an existing raca.
     *
     * @param racaDTO the racaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racaDTO,
     * or with status {@code 400 (Bad Request)} if the racaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racas")
    public ResponseEntity<RacaDTO> updateRaca(@Valid @RequestBody RacaDTO racaDTO) throws URISyntaxException {
        log.debug("REST request to update Raca : {}", racaDTO);
        if (racaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacaDTO result = racaService.save(racaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, racaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racas} : get all the racas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racas in body.
     */
    @GetMapping("/racas")
    public ResponseEntity<List<RacaDTO>> getAllRacas(RacaDTO racaDTO, Pageable pageable) {
        log.debug("REST request to get a page of Racas");
        Page<RacaDTO> page = racaService.findAll(racaDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /racas/:id} : get the "id" raca.
     *
     * @param id the id of the racaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racas/{id}")
    public ResponseEntity<RacaDTO> getRaca(@PathVariable Long id) {
        log.debug("REST request to get Raca : {}", id);
        Optional<RacaDTO> racaDTO = racaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racaDTO);
    }

    /**
     * {@code DELETE  /racas/:id} : delete the "id" raca.
     *
     * @param id the id of the racaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racas/{id}")
    public ResponseEntity<Void> deleteRaca(@PathVariable Long id) {
        log.debug("REST request to delete Raca : {}", id);
        racaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/racas?query=:query} : search for the raca corresponding
     * to the query.
     *
     * @param query the query of the raca search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/racas")
    public ResponseEntity<List<RacaDTO>> searchRacas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Racas for query {}", query);
        Page<RacaDTO> page = racaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
