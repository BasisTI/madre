package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ProcedimentoService;
import br.com.basis.madre.service.dto.ProcedimentoDTO;
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
 * REST controller for managing Procedimento.
 */
@RestController
@RequestMapping("/api")
public class ProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoResource.class);

    private static final String ENTITY_NAME = "pacientesProcedimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcedimentoService procedimentoService;

    public ProcedimentoResource(ProcedimentoService procedimentoService) {
        this.procedimentoService = procedimentoService;
    }

    /**
     * POST  /procedimentos : Create a new procedimento.
     *
     * @param procedimentoDTO the procedimentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedimentoDTO,
     * or with status 400 (Bad Request) if the procedimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedimentos")
    @Timed
    public ResponseEntity<ProcedimentoDTO> createProcedimento(
        @Valid @RequestBody ProcedimentoDTO procedimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Procedimento : {}", procedimentoDTO);
        if (procedimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedimento cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        ProcedimentoDTO result = procedimentoService.save(procedimentoDTO);
        return ResponseEntity.created(new URI("/api/procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedimentos : Updates an existing procedimento.
     *
     * @param procedimentoDTO the procedimentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedimentoDTO, or
     * with status 400 (Bad Request) if the procedimentoDTO is not valid, or with status 500
     * (Internal Server Error) if the procedimentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedimentos")
    @Timed
    public ResponseEntity<ProcedimentoDTO> updateProcedimento(
        @Valid @RequestBody ProcedimentoDTO procedimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Procedimento : {}", procedimentoDTO);
        if (procedimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcedimentoDTO result = procedimentoService.save(procedimentoDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procedimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedimentos : get all the procedimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procedimentos in body
     */
    @GetMapping("/procedimentos")
    @Timed
    public ResponseEntity<List<ProcedimentoDTO>> getAllProcedimentos(Pageable pageable) {
        log.debug("REST request to get a page of Procedimentos");
        Page<ProcedimentoDTO> page = procedimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /procedimentos/:id : get the "id" procedimento.
     *
     * @param id the id of the procedimentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedimentoDTO, or with
     * status 404 (Not Found)
     */
    @GetMapping("/procedimentos/{id}")
    @Timed
    public ResponseEntity<ProcedimentoDTO> getProcedimento(@PathVariable Long id) {
        log.debug("REST request to get Procedimento : {}", id);
        Optional<ProcedimentoDTO> procedimentoDTO = procedimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedimentoDTO);
    }

    /**
     * DELETE  /procedimentos/:id : delete the "id" procedimento.
     *
     * @param id the id of the procedimentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimento : {}", id);
        procedimentoService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/procedimentos?query=:query : search for the procedimento corresponding to
     * the query.
     *
     * @param query    the query of the procedimento search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/procedimentos")
    @Timed
    public ResponseEntity<List<ProcedimentoDTO>> searchProcedimentos(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of Procedimentos for query {}", query);
        Page<ProcedimentoDTO> page = procedimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
