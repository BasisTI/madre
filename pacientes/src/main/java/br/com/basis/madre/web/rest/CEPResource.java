package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.CEP;
import br.com.basis.madre.service.CEPService;
import br.com.basis.madre.service.dto.CEPDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.CEP}.
 */
@RestController
@RequestMapping("/api")
public class CEPResource {

    private final Logger log = LoggerFactory.getLogger(CEPResource.class);

    private static final String ENTITY_NAME = "pacientesCep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEPService cEPService;

    public CEPResource(CEPService cEPService) {
        this.cEPService = cEPService;
    }

    /**
     * {@code POST  /ceps} : Create a new cEP.
     *
     * @param cEPDTO the cEPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEPDTO, or with status {@code 400 (Bad Request)} if the cEP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ceps")
    public ResponseEntity<CEPDTO> createCEP(@RequestBody CEPDTO cEPDTO) throws URISyntaxException {
        log.debug("REST request to save CEP : {}", cEPDTO);
        if (cEPDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEPDTO result = cEPService.save(cEPDTO);
        return ResponseEntity.created(new URI("/api/ceps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/ceps/filtragem")
    public ResponseEntity<List<CEP>> findAllProjectedCepBy(@RequestParam(required = false)String cep, Pageable pageable) {
        Page<CEP> page = cEPService
            .findAllCEP(cep ,pageable);
        return ResponseEntity.ok(page.getContent());
    }

    /**
     * {@code PUT  /ceps} : Updates an existing cEP.
     *
     * @param cEPDTO the cEPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEPDTO,
     * or with status {@code 400 (Bad Request)} if the cEPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ceps")
    public ResponseEntity<CEPDTO> updateCEP(@RequestBody CEPDTO cEPDTO) throws URISyntaxException {
        log.debug("REST request to update CEP : {}", cEPDTO);
        if (cEPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEPDTO result = cEPService.save(cEPDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cEPDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ceps} : get all the cEPS.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEPS in body.
     */
    @GetMapping("/ceps")
    public ResponseEntity<List<CEPDTO>> getAllCEPS(Pageable pageable) {
        log.debug("REST request to get a page of CEPS");
        Page<CEPDTO> page = cEPService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ceps/:id} : get the "id" cEP.
     *
     * @param id the id of the cEPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ceps/{id}")
    public ResponseEntity<CEPDTO> getCEP(@PathVariable Long id) {
        log.debug("REST request to get CEP : {}", id);
        Optional<CEPDTO> cEPDTO = cEPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEPDTO);
    }

    @GetMapping("/ceps/buscar/{cep}")
    public ResponseEntity<CEPDTO> getByCEP(@PathVariable String cep) {
        log.debug("REST request to get CEP : {}", cep);
        Optional<CEPDTO> cEPDTO = cEPService.findOneByCep(cep);
        return ResponseUtil.wrapOrNotFound(cEPDTO);
    }

    /**
     * {@code DELETE  /ceps/:id} : delete the "id" cEP.
     *
     * @param id the id of the cEPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ceps/{id}")
    public ResponseEntity<Void> deleteCEP(@PathVariable Long id) {
        log.debug("REST request to delete CEP : {}", id);
        cEPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ceps?query=:query} : search for the cEP corresponding
     * to the query.
     *
     * @param query the query of the cEP search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ceps")
    public ResponseEntity<List<CEPDTO>> searchCEPS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CEPS for query {}", query);
        Page<CEPDTO> page = cEPService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
