package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.OrgaoEmissorService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.OrgaoEmissorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.OrgaoEmissor}.
 */
@RestController
@RequestMapping("/api")
public class OrgaoEmissorResource {

    private final Logger log = LoggerFactory.getLogger(OrgaoEmissorResource.class);

    private static final String ENTITY_NAME = "snffaturaOrgaoEmissor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgaoEmissorService orgaoEmissorService;

    public OrgaoEmissorResource(OrgaoEmissorService orgaoEmissorService) {
        this.orgaoEmissorService = orgaoEmissorService;
    }

    /**
     * {@code POST  /orgao-emissors} : Create a new orgaoEmissor.
     *
     * @param orgaoEmissorDTO the orgaoEmissorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgaoEmissorDTO, or with status {@code 400 (Bad Request)} if the orgaoEmissor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orgao-emissors")
    public ResponseEntity<OrgaoEmissorDTO> createOrgaoEmissor(@Valid @RequestBody OrgaoEmissorDTO orgaoEmissorDTO) throws URISyntaxException {
        log.debug("REST request to save OrgaoEmissor : {}", orgaoEmissorDTO);
        if (orgaoEmissorDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgaoEmissor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgaoEmissorDTO result = orgaoEmissorService.save(orgaoEmissorDTO);
        return ResponseEntity.created(new URI("/api/orgao-emissors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orgao-emissors} : Updates an existing orgaoEmissor.
     *
     * @param orgaoEmissorDTO the orgaoEmissorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgaoEmissorDTO,
     * or with status {@code 400 (Bad Request)} if the orgaoEmissorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgaoEmissorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orgao-emissors")
    public ResponseEntity<OrgaoEmissorDTO> updateOrgaoEmissor(@Valid @RequestBody OrgaoEmissorDTO orgaoEmissorDTO) throws URISyntaxException {
        log.debug("REST request to update OrgaoEmissor : {}", orgaoEmissorDTO);
        if (orgaoEmissorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrgaoEmissorDTO result = orgaoEmissorService.save(orgaoEmissorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orgaoEmissorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /orgao-emissors} : get all the orgaoEmissors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgaoEmissors in body.
     */
    @GetMapping("/orgao-emissors")
    public ResponseEntity<List<OrgaoEmissorDTO>> getAllOrgaoEmissors(Pageable pageable) {
        log.debug("REST request to get a page of OrgaoEmissors");
        Page<OrgaoEmissorDTO> page = orgaoEmissorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /orgao-emissors/:id} : get the "id" orgaoEmissor.
     *
     * @param id the id of the orgaoEmissorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgaoEmissorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orgao-emissors/{id}")
    public ResponseEntity<OrgaoEmissorDTO> getOrgaoEmissor(@PathVariable Long id) {
        log.debug("REST request to get OrgaoEmissor : {}", id);
        Optional<OrgaoEmissorDTO> orgaoEmissorDTO = orgaoEmissorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgaoEmissorDTO);
    }

    /**
     * {@code DELETE  /orgao-emissors/:id} : delete the "id" orgaoEmissor.
     *
     * @param id the id of the orgaoEmissorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orgao-emissors/{id}")
    public ResponseEntity<Void> deleteOrgaoEmissor(@PathVariable Long id) {
        log.debug("REST request to delete OrgaoEmissor : {}", id);
        orgaoEmissorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/orgao-emissors?query=:query} : search for the orgaoEmissor corresponding
     * to the query.
     *
     * @param query the query of the orgaoEmissor search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/orgao-emissors")
    public ResponseEntity<List<OrgaoEmissorDTO>> searchOrgaoEmissors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrgaoEmissors for query {}", query);
        Page<OrgaoEmissorDTO> page = orgaoEmissorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
