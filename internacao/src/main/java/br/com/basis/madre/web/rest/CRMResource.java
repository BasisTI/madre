package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CRMService;
import br.com.basis.madre.service.dto.CrmDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CRMResource {

    private final Logger log = LoggerFactory.getLogger(CRMResource.class);

    private static final String ENTITY_NAME = "internacaoCrm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRMService crmService;

    /**
     * {@code POST  /crms} : Create a new cRM.
     *
     * @param cRMDTO the cRMDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * cRMDTO, or with status {@code 400 (Bad Request)} if the cRM has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crms")
    public ResponseEntity<CrmDTO> createCRM(@Valid @RequestBody CrmDTO cRMDTO)
        throws URISyntaxException {
        log.debug("REST request to save CRM : {}", cRMDTO);
        if (cRMDTO.getId() != null) {
            throw new BadRequestAlertException("A new cRM cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        CrmDTO result = crmService.save(cRMDTO);
        return ResponseEntity.created(new URI("/api/crms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crms} : Updates an existing cRM.
     *
     * @param cRMDTO the cRMDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * cRMDTO, or with status {@code 400 (Bad Request)} if the cRMDTO is not valid, or with status
     * {@code 500 (Internal Server Error)} if the cRMDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crms")
    public ResponseEntity<CrmDTO> updateCRM(@Valid @RequestBody CrmDTO cRMDTO)
        throws URISyntaxException {
        log.debug("REST request to update CRM : {}", cRMDTO);
        if (cRMDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CrmDTO result = crmService.save(cRMDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                cRMDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /crms} : get all the cRMS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cRMS in body.
     */
    @GetMapping("/crms")
    public ResponseEntity<List<CrmDTO>> getAllCRMS(CrmDTO crmDTO, Pageable pageable) {
        log.debug("REST request to get a page of CRMS");
        Page<CrmDTO> page = crmService.findAll(crmDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crms/:id} : get the "id" cRM.
     *
     * @param id the id of the cRMDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cRMDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crms/{id}")
    public ResponseEntity<CrmDTO> getCRM(@PathVariable Long id) {
        log.debug("REST request to get CRM : {}", id);
        Optional<CrmDTO> cRMDTO = crmService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cRMDTO);
    }

    /**
     * {@code DELETE  /crms/:id} : delete the "id" cRM.
     *
     * @param id the id of the cRMDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crms/{id}")
    public ResponseEntity<Void> deleteCRM(@PathVariable Long id) {
        log.debug("REST request to delete CRM : {}", id);
        crmService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/crms?query=:query} : search for the cRM corresponding to the query.
     *
     * @param query    the query of the cRM search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/crms")
    public ResponseEntity<List<CrmDTO>> searchCRMS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CRMS for query {}", query);
        Page<CrmDTO> page = crmService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
