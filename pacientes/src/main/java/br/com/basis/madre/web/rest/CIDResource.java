package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.CID;
import br.com.basis.madre.service.CIDService;
import br.com.basis.madre.service.dto.CIDDTO;
import br.com.basis.madre.service.projection.CidParent;
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
 * REST controller for managing CID.
 */
@RestController
@RequestMapping("/api")
public class CIDResource {

    private final Logger log = LoggerFactory.getLogger(CIDResource.class);

    private static final String ENTITY_NAME = "pacientesCid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIDService cIDService;

    public CIDResource(CIDService cIDService) {
        this.cIDService = cIDService;
    }

    /**
     * POST  /cids : Create a new cID.
     *
     * @param cIDDTO the cIDDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cIDDTO, or with
     * status 400 (Bad Request) if the cID has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cids")
    @Timed
    public ResponseEntity<CIDDTO> createCID(@Valid @RequestBody CIDDTO cIDDTO)
        throws URISyntaxException {
        log.debug("REST request to save CID : {}", cIDDTO);
        if (cIDDTO.getId() != null) {
            throw new BadRequestAlertException("A new cID cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        CIDDTO result = cIDService.save(cIDDTO);
        return ResponseEntity.created(new URI("/api/cids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cids : Updates an existing cID.
     *
     * @param cIDDTO the cIDDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cIDDTO, or with
     * status 400 (Bad Request) if the cIDDTO is not valid, or with status 500 (Internal Server
     * Error) if the cIDDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cids")
    @Timed
    public ResponseEntity<CIDDTO> updateCID(@Valid @RequestBody CIDDTO cIDDTO)
        throws URISyntaxException {
        log.debug("REST request to update CID : {}", cIDDTO);
        if (cIDDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CIDDTO result = cIDService.save(cIDDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                cIDDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cids : get all the cIDS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cIDS in body
     */
    @GetMapping("/cids")
    @Timed
    public ResponseEntity<List<CIDDTO>> getAllCIDS(CIDDTO cidDTO, Pageable pageable) {
        log.debug("REST request to get a page of CIDS");
        Page<CIDDTO> page = cIDService.findAll(cidDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/cids/tree/parents")
    public ResponseEntity<List<CIDDTO>> findAllParents(CIDDTO cidDTO, Pageable pageable) {
        log.debug("REST request to get a page of parent CIDS");
        Page<CIDDTO> page = cIDService.findAllParents(cidDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/cids/tree/parents/{id}/children")
    public ResponseEntity<List<CIDDTO>> findAllChindrenFromParentId(@PathVariable(name = "id", required = true) Long parentId, Pageable pageable) {
        log.debug("REST request to get a page of parent CIDS");
        Page<CIDDTO> page = cIDService.findAllChindrenFromParentId(parentId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cids/:id : get the "id" cID.
     *
     * @param id the id of the cIDDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cIDDTO, or with status 404
     * (Not Found)
     */
    @GetMapping("/cids/{id}")
    @Timed
    public ResponseEntity<CIDDTO> getCID(@PathVariable Long id) {
        log.debug("REST request to get CID : {}", id);
        Optional<CIDDTO> cIDDTO = cIDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIDDTO);
    }

    /**
     * DELETE  /cids/:id : delete the "id" cID.
     *
     * @param id the id of the cIDDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cids/{id}")
    @Timed
    public ResponseEntity<Void> deleteCID(@PathVariable Long id) {
        log.debug("REST request to delete CID : {}", id);
        cIDService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cids?query=:query : search for the cID corresponding to the query.
     *
     * @param query    the query of the cID search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cids")
    @Timed
    public ResponseEntity<List<CIDDTO>> searchCIDS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CIDS for query {}", query);
        Page<CIDDTO> page = cIDService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
