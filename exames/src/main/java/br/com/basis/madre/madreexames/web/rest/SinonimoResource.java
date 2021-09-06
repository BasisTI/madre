package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.SinonimoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.SinonimoDTO;

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
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.Sinonimo}.
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
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sinonimos in body.
     */
    @GetMapping("/sinonimos")
    public ResponseEntity<List<SinonimoDTO>> getAllSinonimos(Pageable pageable) {
        log.debug("REST request to get a page of Sinonimos");
        Page<SinonimoDTO> page = sinonimoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/sinonimos")
    public ResponseEntity<List<SinonimoDTO>> searchSinonimos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sinonimos for query {}", query);
        Page<SinonimoDTO> page = sinonimoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
