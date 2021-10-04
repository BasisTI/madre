package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.SalaService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.SalaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.Sala}.
 */
@RestController
@RequestMapping("/api")
public class SalaResource {

    private final Logger log = LoggerFactory.getLogger(SalaResource.class);

    private static final String ENTITY_NAME = "madreexamesSala";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalaService salaService;

    public SalaResource(SalaService salaService) {
        this.salaService = salaService;
    }

    /**
     * {@code POST  /salas} : Create a new sala.
     *
     * @param salaDTO the salaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salaDTO, or with status {@code 400 (Bad Request)} if the sala has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salas")
    public ResponseEntity<SalaDTO> createSala(@Valid @RequestBody SalaDTO salaDTO) throws URISyntaxException {
        log.debug("REST request to save Sala : {}", salaDTO);
        if (salaDTO.getId() != null) {
            throw new BadRequestAlertException("A new sala cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaDTO result = salaService.save(salaDTO);
        return ResponseEntity.created(new URI("/api/salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salas} : Updates an existing sala.
     *
     * @param salaDTO the salaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salaDTO,
     * or with status {@code 400 (Bad Request)} if the salaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salas")
    public ResponseEntity<SalaDTO> updateSala(@Valid @RequestBody SalaDTO salaDTO) throws URISyntaxException {
        log.debug("REST request to update Sala : {}", salaDTO);
        if (salaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaDTO result = salaService.save(salaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /salas} : get all the salas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salas in body.
     */
    @GetMapping("/salas")
    public ResponseEntity<List<SalaDTO>> getAllSalas(Pageable pageable) {
        log.debug("REST request to get a page of Salas");
        Page<SalaDTO> page = salaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salas/:id} : get the "id" sala.
     *
     * @param id the id of the salaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salas/{id}")
    public ResponseEntity<SalaDTO> getSala(@PathVariable Long id) {
        log.debug("REST request to get Sala : {}", id);
        Optional<SalaDTO> salaDTO = salaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salaDTO);
    }

    /**
     * {@code DELETE  /salas/:id} : delete the "id" sala.
     *
     * @param id the id of the salaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salas/{id}")
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        log.debug("REST request to delete Sala : {}", id);
        salaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/salas?query=:query} : search for the sala corresponding
     * to the query.
     *
     * @param query the query of the sala search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/salas")
    public ResponseEntity<List<SalaDTO>> searchSalas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Salas for query {}", query);
        Page<SalaDTO> page = salaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    @GetMapping("/_search/salas-por-unidade")
    public ResponseEntity<List<SalaDTO>> filtrarSalasPorUnidade(Pageable pageable,
        @RequestParam(name = "unidadeExecutoraId", required = true) String unidadeExecutoraId,
        @RequestParam(name = "ativo", required = true) String ativo) {
        log.debug("Request REST para obter uma página de salas filtradas por unidadeExecutoraId");
        Page<SalaDTO> page = salaService.filtrarSalasPorUnidade(pageable, unidadeExecutoraId, ativo);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
