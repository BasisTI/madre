package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.ProcessoAssincronoGradeService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.ProcessoAssincronoGradeDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.ProcessoAssincronoGrade}.
 */
@RestController
@RequestMapping("/api")
public class ProcessoAssincronoGradeResource {

    private final Logger log = LoggerFactory.getLogger(ProcessoAssincronoGradeResource.class);

    private static final String ENTITY_NAME = "madreexamesProcessoAssincronoGrade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessoAssincronoGradeService processoAssincronoGradeService;

    public ProcessoAssincronoGradeResource(ProcessoAssincronoGradeService processoAssincronoGradeService) {
        this.processoAssincronoGradeService = processoAssincronoGradeService;
    }

    /**
     * {@code POST  /processo-assincrono-grades} : Create a new processoAssincronoGrade.
     *
     * @param processoAssincronoGradeDTO the processoAssincronoGradeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processoAssincronoGradeDTO, or with status {@code 400 (Bad Request)} if the processoAssincronoGrade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processo-assincrono-grades")
    public ResponseEntity<ProcessoAssincronoGradeDTO> createProcessoAssincronoGrade(@RequestBody ProcessoAssincronoGradeDTO processoAssincronoGradeDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessoAssincronoGrade : {}", processoAssincronoGradeDTO);
        if (processoAssincronoGradeDTO.getId() != null) {
            throw new BadRequestAlertException("A new processoAssincronoGrade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessoAssincronoGradeDTO result = processoAssincronoGradeService.save(processoAssincronoGradeDTO);
        return ResponseEntity.created(new URI("/api/processo-assincrono-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processo-assincrono-grades} : Updates an existing processoAssincronoGrade.
     *
     * @param processoAssincronoGradeDTO the processoAssincronoGradeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoAssincronoGradeDTO,
     * or with status {@code 400 (Bad Request)} if the processoAssincronoGradeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processoAssincronoGradeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processo-assincrono-grades")
    public ResponseEntity<ProcessoAssincronoGradeDTO> updateProcessoAssincronoGrade(@RequestBody ProcessoAssincronoGradeDTO processoAssincronoGradeDTO) throws URISyntaxException {
        log.debug("REST request to update ProcessoAssincronoGrade : {}", processoAssincronoGradeDTO);
        if (processoAssincronoGradeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessoAssincronoGradeDTO result = processoAssincronoGradeService.save(processoAssincronoGradeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processoAssincronoGradeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processo-assincrono-grades} : get all the processoAssincronoGrades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processoAssincronoGrades in body.
     */
    @GetMapping("/processo-assincrono-grades")
    public ResponseEntity<List<ProcessoAssincronoGradeDTO>> getAllProcessoAssincronoGrades(Pageable pageable) {
        log.debug("REST request to get a page of ProcessoAssincronoGrades");
        Page<ProcessoAssincronoGradeDTO> page = processoAssincronoGradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processo-assincrono-grades/:id} : get the "id" processoAssincronoGrade.
     *
     * @param id the id of the processoAssincronoGradeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processoAssincronoGradeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processo-assincrono-grades/{id}")
    public ResponseEntity<ProcessoAssincronoGradeDTO> getProcessoAssincronoGrade(@PathVariable String id) {
        log.debug("REST request to get ProcessoAssincronoGrade : {}", id);
        Optional<ProcessoAssincronoGradeDTO> processoAssincronoGradeDTO = processoAssincronoGradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processoAssincronoGradeDTO);
    }

    /**
     * {@code DELETE  /processo-assincrono-grades/:id} : delete the "id" processoAssincronoGrade.
     *
     * @param id the id of the processoAssincronoGradeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processo-assincrono-grades/{id}")
    public ResponseEntity<Void> deleteProcessoAssincronoGrade(@PathVariable String id) {
        log.debug("REST request to delete ProcessoAssincronoGrade : {}", id);
        processoAssincronoGradeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/processo-assincrono-grades?query=:query} : search for the processoAssincronoGrade corresponding
     * to the query.
     *
     * @param query the query of the processoAssincronoGrade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/processo-assincrono-grades")
    public ResponseEntity<List<ProcessoAssincronoGradeDTO>> searchProcessoAssincronoGrades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProcessoAssincronoGrades for query {}", query);
        Page<ProcessoAssincronoGradeDTO> page = processoAssincronoGradeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
