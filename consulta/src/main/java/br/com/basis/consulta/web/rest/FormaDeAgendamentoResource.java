package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.service.FormaDeAgendamentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.consulta.service.dto.FormaDeAgendamentoDTO;

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
 * REST controller for managing {@link br.com.basis.consulta.domain.FormaDeAgendamento}.
 */
@RestController
@RequestMapping("/api")
public class FormaDeAgendamentoResource {

    private final Logger log = LoggerFactory.getLogger(FormaDeAgendamentoResource.class);

    private static final String ENTITY_NAME = "madreconsultaFormaDeAgendamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormaDeAgendamentoService formaDeAgendamentoService;

    public FormaDeAgendamentoResource(FormaDeAgendamentoService formaDeAgendamentoService) {
        this.formaDeAgendamentoService = formaDeAgendamentoService;
    }

    /**
     * {@code POST  /forma-de-agendamentos} : Create a new formaDeAgendamento.
     *
     * @param formaDeAgendamentoDTO the formaDeAgendamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formaDeAgendamentoDTO, or with status {@code 400 (Bad Request)} if the formaDeAgendamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forma-de-agendamentos")
    public ResponseEntity<FormaDeAgendamentoDTO> createFormaDeAgendamento(@Valid @RequestBody FormaDeAgendamentoDTO formaDeAgendamentoDTO) throws URISyntaxException {
        log.debug("REST request to save FormaDeAgendamento : {}", formaDeAgendamentoDTO);
        if (formaDeAgendamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new formaDeAgendamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormaDeAgendamentoDTO result = formaDeAgendamentoService.save(formaDeAgendamentoDTO);
        return ResponseEntity.created(new URI("/api/forma-de-agendamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forma-de-agendamentos} : Updates an existing formaDeAgendamento.
     *
     * @param formaDeAgendamentoDTO the formaDeAgendamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaDeAgendamentoDTO,
     * or with status {@code 400 (Bad Request)} if the formaDeAgendamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaDeAgendamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forma-de-agendamentos")
    public ResponseEntity<FormaDeAgendamentoDTO> updateFormaDeAgendamento(@Valid @RequestBody FormaDeAgendamentoDTO formaDeAgendamentoDTO) throws URISyntaxException {
        log.debug("REST request to update FormaDeAgendamento : {}", formaDeAgendamentoDTO);
        if (formaDeAgendamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormaDeAgendamentoDTO result = formaDeAgendamentoService.save(formaDeAgendamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formaDeAgendamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forma-de-agendamentos} : get all the formaDeAgendamentos.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formaDeAgendamentos in body.
     */
    @GetMapping("/forma-de-agendamentos")
    public ResponseEntity<List<FormaDeAgendamentoDTO>> getAllFormaDeAgendamentos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("emergencia-is-null".equals(filter)) {
            log.debug("REST request to get all FormaDeAgendamentos where emergencia is null");
            return new ResponseEntity<>(formaDeAgendamentoService.findAllWhereEmergenciaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of FormaDeAgendamentos");
        Page<FormaDeAgendamentoDTO> page = formaDeAgendamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forma-de-agendamentos/:id} : get the "id" formaDeAgendamento.
     *
     * @param id the id of the formaDeAgendamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formaDeAgendamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forma-de-agendamentos/{id}")
    public ResponseEntity<FormaDeAgendamentoDTO> getFormaDeAgendamento(@PathVariable Long id) {
        log.debug("REST request to get FormaDeAgendamento : {}", id);
        Optional<FormaDeAgendamentoDTO> formaDeAgendamentoDTO = formaDeAgendamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaDeAgendamentoDTO);
    }

    /**
     * {@code DELETE  /forma-de-agendamentos/:id} : delete the "id" formaDeAgendamento.
     *
     * @param id the id of the formaDeAgendamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forma-de-agendamentos/{id}")
    public ResponseEntity<Void> deleteFormaDeAgendamento(@PathVariable Long id) {
        log.debug("REST request to delete FormaDeAgendamento : {}", id);
        formaDeAgendamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/forma-de-agendamentos?query=:query} : search for the formaDeAgendamento corresponding
     * to the query.
     *
     * @param query the query of the formaDeAgendamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/forma-de-agendamentos")
    public ResponseEntity<List<FormaDeAgendamentoDTO>> searchFormaDeAgendamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FormaDeAgendamentos for query {}", query);
        Page<FormaDeAgendamentoDTO> page = formaDeAgendamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
