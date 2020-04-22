package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CRMService;
import br.com.basis.madre.service.dto.CRMDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
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
import org.springframework.http.HttpStatus;
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
 * REST controller for managing CRM.
 */
@RestController
@RequestMapping("/api")
public class CRMResource {

    private final Logger log = LoggerFactory.getLogger(CRMResource.class);

    private static final String ENTITY_NAME = "pacientesCrm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRMService cRMService;

    public CRMResource(CRMService cRMService) {
        this.cRMService = cRMService;
    }

    /**
     * POST  /crms : Create a new cRM.
     *
     * @param cRMDTO the cRMDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cRMDTO, or with status 400 (Bad Request) if the cRM has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/crms")
    @Timed
    public ResponseEntity<CRMDTO> createCRM(@Valid @RequestBody CRMDTO cRMDTO) throws URISyntaxException {
        log.debug("REST request to save CRM : {}", cRMDTO);
        if (cRMDTO.getId() != null) {
            throw new BadRequestAlertException("A new cRM cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CRMDTO result = cRMService.save(cRMDTO);
        return ResponseEntity.created(new URI("/api/crms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /crms : Updates an existing cRM.
     *
     * @param cRMDTO the cRMDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cRMDTO,
     * or with status 400 (Bad Request) if the cRMDTO is not valid,
     * or with status 500 (Internal Server Error) if the cRMDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/crms")
    @Timed
    public ResponseEntity<CRMDTO> updateCRM(@Valid @RequestBody CRMDTO cRMDTO) throws URISyntaxException {
        log.debug("REST request to update CRM : {}", cRMDTO);
        if (cRMDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CRMDTO result = cRMService.save(cRMDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cRMDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /crms : get all the cRMS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cRMS in body
     */
    @GetMapping("/crms")
    @Timed
    public ResponseEntity<List<CRMDTO>> getAllCRMS(Pageable pageable) {
        log.debug("REST request to get a page of CRMS");
        Page<CRMDTO> page = cRMService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /crms/:id : get the "id" cRM.
     *
     * @param id the id of the cRMDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cRMDTO, or with status 404 (Not Found)
     */
    @GetMapping("/crms/{id}")
    @Timed
    public ResponseEntity<CRMDTO> getCRM(@PathVariable Long id) {
        log.debug("REST request to get CRM : {}", id);
        Optional<CRMDTO> cRMDTO = cRMService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cRMDTO);
    }

    /**
     * DELETE  /crms/:id : delete the "id" cRM.
     *
     * @param id the id of the cRMDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/crms/{id}")
    @Timed
    public ResponseEntity<Void> deleteCRM(@PathVariable Long id) {
        log.debug("REST request to delete CRM : {}", id);
        cRMService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false,ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/crms?query=:query : search for the cRM corresponding
     * to the query.
     *
     * @param query the query of the cRM search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/crms")
    @Timed
    public ResponseEntity<List<CRMDTO>> searchCRMS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CRMS for query {}", query);
        Page<CRMDTO> page = cRMService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
