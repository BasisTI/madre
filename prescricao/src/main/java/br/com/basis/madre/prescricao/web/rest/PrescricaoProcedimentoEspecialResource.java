package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.PrescricaoProcedimentoEspecialService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoEspecialDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.PrescricaoProcedimentoEspecial}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoProcedimentoEspecialResource {

    private final Logger log = LoggerFactory.getLogger(PrescricaoProcedimentoEspecialResource.class);

    private static final String ENTITY_NAME = "prescricaoPrescricaoProcedimentoEspecial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescricaoProcedimentoEspecialService prescricaoProcedimentoEspecialService;

    public PrescricaoProcedimentoEspecialResource(PrescricaoProcedimentoEspecialService prescricaoProcedimentoEspecialService) {
        this.prescricaoProcedimentoEspecialService = prescricaoProcedimentoEspecialService;
    }

    /**
     * {@code POST  /prescricao-procedimento-especials} : Create a new prescricaoProcedimentoEspecial.
     *
     * @param prescricaoProcedimentoEspecialDTO the prescricaoProcedimentoEspecialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescricaoProcedimentoEspecialDTO, or with status {@code 400 (Bad Request)} if the prescricaoProcedimentoEspecial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prescricao-procedimento-especials")
    public ResponseEntity<PrescricaoProcedimentoEspecialDTO> createPrescricaoProcedimentoEspecial(@Valid @RequestBody PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO) throws URISyntaxException {
        log.debug("REST request to save PrescricaoProcedimentoEspecial : {}", prescricaoProcedimentoEspecialDTO);
        if (prescricaoProcedimentoEspecialDTO.getId() != null) {
            throw new BadRequestAlertException("A new prescricaoProcedimentoEspecial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrescricaoProcedimentoEspecialDTO result = prescricaoProcedimentoEspecialService.save(prescricaoProcedimentoEspecialDTO);
        return ResponseEntity.created(new URI("/api/prescricao-procedimento-especials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prescricao-procedimento-especials} : Updates an existing prescricaoProcedimentoEspecial.
     *
     * @param prescricaoProcedimentoEspecialDTO the prescricaoProcedimentoEspecialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prescricaoProcedimentoEspecialDTO,
     * or with status {@code 400 (Bad Request)} if the prescricaoProcedimentoEspecialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prescricaoProcedimentoEspecialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prescricao-procedimento-especials")
    public ResponseEntity<PrescricaoProcedimentoEspecialDTO> updatePrescricaoProcedimentoEspecial(@Valid @RequestBody PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO) throws URISyntaxException {
        log.debug("REST request to update PrescricaoProcedimentoEspecial : {}", prescricaoProcedimentoEspecialDTO);
        if (prescricaoProcedimentoEspecialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrescricaoProcedimentoEspecialDTO result = prescricaoProcedimentoEspecialService.save(prescricaoProcedimentoEspecialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricaoProcedimentoEspecialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prescricao-procedimento-especials} : get all the prescricaoProcedimentoEspecials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prescricaoProcedimentoEspecials in body.
     */
    @GetMapping("/prescricao-procedimento-especials")
    public ResponseEntity<List<PrescricaoProcedimentoEspecialDTO>> getAllPrescricaoProcedimentoEspecials(Pageable pageable) {
        log.debug("REST request to get a page of PrescricaoProcedimentoEspecials");
        Page<PrescricaoProcedimentoEspecialDTO> page = prescricaoProcedimentoEspecialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescricao-procedimento-especials/:id} : get the "id" prescricaoProcedimentoEspecial.
     *
     * @param id the id of the prescricaoProcedimentoEspecialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prescricaoProcedimentoEspecialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prescricao-procedimento-especials/{id}")
    public ResponseEntity<PrescricaoProcedimentoEspecialDTO> getPrescricaoProcedimentoEspecial(@PathVariable Long id) {
        log.debug("REST request to get PrescricaoProcedimentoEspecial : {}", id);
        Optional<PrescricaoProcedimentoEspecialDTO> prescricaoProcedimentoEspecialDTO = prescricaoProcedimentoEspecialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescricaoProcedimentoEspecialDTO);
    }

    /**
     * {@code DELETE  /prescricao-procedimento-especials/:id} : delete the "id" prescricaoProcedimentoEspecial.
     *
     * @param id the id of the prescricaoProcedimentoEspecialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prescricao-procedimento-especials/{id}")
    public ResponseEntity<Void> deletePrescricaoProcedimentoEspecial(@PathVariable Long id) {
        log.debug("REST request to delete PrescricaoProcedimentoEspecial : {}", id);
        prescricaoProcedimentoEspecialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prescricao-procedimento-especials?query=:query} : search for the prescricaoProcedimentoEspecial corresponding
     * to the query.
     *
     * @param query the query of the prescricaoProcedimentoEspecial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prescricao-procedimento-especials")
    public ResponseEntity<List<PrescricaoProcedimentoEspecialDTO>> searchPrescricaoProcedimentoEspecials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrescricaoProcedimentoEspecials for query {}", query);
        Page<PrescricaoProcedimentoEspecialDTO> page = prescricaoProcedimentoEspecialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
