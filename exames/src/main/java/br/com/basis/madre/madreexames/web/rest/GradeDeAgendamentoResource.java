package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.GradeDeAgendamentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.GradeDeAgendamento}.
 */
@RestController
@RequestMapping("/api")
public class GradeDeAgendamentoResource {

    private final Logger log = LoggerFactory.getLogger(GradeDeAgendamentoResource.class);

    private static final String ENTITY_NAME = "madreexamesGradeDeAgendamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradeDeAgendamentoService gradeDeAgendamentoService;

    public GradeDeAgendamentoResource(GradeDeAgendamentoService gradeDeAgendamentoService) {
        this.gradeDeAgendamentoService = gradeDeAgendamentoService;
    }

    /**
     * {@code POST  /grade-de-agendamentos} : Create a new gradeDeAgendamento.
     *
     * @param gradeDeAgendamentoDTO the gradeDeAgendamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gradeDeAgendamentoDTO, or with status {@code 400 (Bad Request)} if the gradeDeAgendamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grade-de-agendamentos")
    public ResponseEntity<GradeDeAgendamentoDTO> createGradeDeAgendamento(@Valid @RequestBody GradeDeAgendamentoDTO gradeDeAgendamentoDTO) throws URISyntaxException {
        log.debug("REST request to save GradeDeAgendamento : {}", gradeDeAgendamentoDTO);
        if (gradeDeAgendamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new gradeDeAgendamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GradeDeAgendamentoDTO result = gradeDeAgendamentoService.save(gradeDeAgendamentoDTO);
        return ResponseEntity.created(new URI("/api/grade-de-agendamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grade-de-agendamentos} : Updates an existing gradeDeAgendamento.
     *
     * @param gradeDeAgendamentoDTO the gradeDeAgendamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gradeDeAgendamentoDTO,
     * or with status {@code 400 (Bad Request)} if the gradeDeAgendamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gradeDeAgendamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grade-de-agendamentos")
    public ResponseEntity<GradeDeAgendamentoDTO> updateGradeDeAgendamento(@Valid @RequestBody GradeDeAgendamentoDTO gradeDeAgendamentoDTO) throws URISyntaxException {
        log.debug("REST request to update GradeDeAgendamento : {}", gradeDeAgendamentoDTO);
        if (gradeDeAgendamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GradeDeAgendamentoDTO result = gradeDeAgendamentoService.save(gradeDeAgendamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gradeDeAgendamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grade-de-agendamentos} : get all the gradeDeAgendamentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gradeDeAgendamentos in body.
     */
    @GetMapping("/grade-de-agendamentos")
    public ResponseEntity<List<GradeDeAgendamentoDTO>> getAllGradeDeAgendamentos(Pageable pageable) {
        log.debug("REST request to get a page of GradeDeAgendamentos");
        Page<GradeDeAgendamentoDTO> page = gradeDeAgendamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grade-de-agendamentos/:id} : get the "id" gradeDeAgendamento.
     *
     * @param id the id of the gradeDeAgendamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gradeDeAgendamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grade-de-agendamentos/{id}")
    public ResponseEntity<GradeDeAgendamentoDTO> getGradeDeAgendamento(@PathVariable Long id) {
        log.debug("REST request to get GradeDeAgendamento : {}", id);
        Optional<GradeDeAgendamentoDTO> gradeDeAgendamentoDTO = gradeDeAgendamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gradeDeAgendamentoDTO);
    }

    /**
     * {@code DELETE  /grade-de-agendamentos/:id} : delete the "id" gradeDeAgendamento.
     *
     * @param id the id of the gradeDeAgendamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grade-de-agendamentos/{id}")
    public ResponseEntity<Void> deleteGradeDeAgendamento(@PathVariable Long id) {
        log.debug("REST request to delete GradeDeAgendamento : {}", id);
        gradeDeAgendamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grade-de-agendamentos?query=:query} : search for the gradeDeAgendamento corresponding
     * to the query.
     *
     * @param query the query of the gradeDeAgendamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grade-de-agendamentos")
    public ResponseEntity<List<GradeDeAgendamentoDTO>> searchGradeDeAgendamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GradeDeAgendamentos for query {}", query);
        Page<GradeDeAgendamentoDTO> page = gradeDeAgendamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    @GetMapping("/_search/grades-de-agendamento")
    public ResponseEntity<List<GradeDeAgendamentoDTO>> obterTodasGrades(Pageable pageable,
        @RequestParam(name = "id", required = false) String id,
        @RequestParam(name = "unidadeExecutoraId", required = false) String unidadeExecutoraId,
        @RequestParam(name = "ativo", required = false) String ativo,
        @RequestParam(name = "grupoAgendamentoExameId", required = false) String grupoAgendamentoExameId,
        @RequestParam(name = "exameGradeId", required = false) String exameGradeId,
        @RequestParam(name = "responsavelId", required = false) String responsavelId
    ){
        log.debug("Request REST para obter uma página de Grades de agendamento de exame.");
        Page<GradeDeAgendamentoDTO> page = gradeDeAgendamentoService.filtraGradeAgendamento(
            pageable, id, unidadeExecutoraId, ativo, grupoAgendamentoExameId, exameGradeId, responsavelId);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
