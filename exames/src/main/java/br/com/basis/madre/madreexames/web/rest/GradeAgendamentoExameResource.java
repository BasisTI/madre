package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.domain.GradeAgendamentoExame;
import br.com.basis.madre.madreexames.service.GeraHorariosGradeService;
import br.com.basis.madre.madreexames.service.GradeAgendamentoExameService;
import br.com.basis.madre.madreexames.service.HorarioExameService;
import br.com.basis.madre.madreexames.service.mapper.GradeAgendamentoExameMapper;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.GradeAgendamentoExame}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GradeAgendamentoExameResource {

    private final Logger log = LoggerFactory.getLogger(GradeAgendamentoExameResource.class);

    private static final String ENTITY_NAME = "madreexamesGradeAgendamentoExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradeAgendamentoExameService gradeAgendamentoExameService;

    private final HorarioExameService horarioExameService;

    private final GeraHorariosGradeService geraHorariosGradeService;

    private final GradeAgendamentoExameMapper gradeAgendamentoExameMapper;

    /**
     * {@code POST  /grade-agendamento-exames} : Create a new gradeAgendamentoExame.
     *
     * @param gradeAgendamentoExameDTO the gradeAgendamentoExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gradeAgendamentoExameDTO, or with status {@code 400 (Bad Request)} if the gradeAgendamentoExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grade-agendamento-exames")
    public ResponseEntity<GradeAgendamentoExameDTO> createGradeAgendamentoExame(@Valid @RequestBody GradeAgendamentoExameDTO gradeAgendamentoExameDTO) throws URISyntaxException {
        log.debug("REST request to save GradeAgendamentoExame : {}", gradeAgendamentoExameDTO);
        if (gradeAgendamentoExameDTO.getId() != null) {
            throw new BadRequestAlertException("A new gradeAgendamentoExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GradeAgendamentoExameDTO result = gradeAgendamentoExameService.save(gradeAgendamentoExameDTO);
        GradeAgendamentoExame gradeAgendamentoExame = gradeAgendamentoExameMapper.toEntity(result);
        horarioExameService.gerarHorariosDaGrade(gradeAgendamentoExame);
        geraHorariosGradeService.buscarDiasCompativeis(result);
        return ResponseEntity.created(new URI("/api/grade-agendamento-exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grade-agendamento-exames} : Updates an existing gradeAgendamentoExame.
     *
     * @param gradeAgendamentoExameDTO the gradeAgendamentoExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gradeAgendamentoExameDTO,
     * or with status {@code 400 (Bad Request)} if the gradeAgendamentoExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gradeAgendamentoExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grade-agendamento-exames")
    public ResponseEntity<GradeAgendamentoExameDTO> updateGradeAgendamentoExame(@Valid @RequestBody GradeAgendamentoExameDTO gradeAgendamentoExameDTO) throws URISyntaxException {
        log.debug("REST request to update GradeAgendamentoExame : {}", gradeAgendamentoExameDTO);
        if (gradeAgendamentoExameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GradeAgendamentoExameDTO result = gradeAgendamentoExameService.save(gradeAgendamentoExameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gradeAgendamentoExameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grade-agendamento-exames} : get all the gradeAgendamentoExames.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gradeAgendamentoExames in body.
     */
    @GetMapping("/grade-agendamento-exames")
    public ResponseEntity<List<GradeAgendamentoExameDTO>> getAllGradeAgendamentoExames(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of GradeAgendamentoExames");
        Page<GradeAgendamentoExameDTO> page;
        if (eagerload) {
            page = gradeAgendamentoExameService.findAllWithEagerRelationships(pageable);
        } else {
            page = gradeAgendamentoExameService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grade-agendamento-exames/:id} : get the "id" gradeAgendamentoExame.
     *
     * @param id the id of the gradeAgendamentoExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gradeAgendamentoExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grade-agendamento-exames/{id}")
    public ResponseEntity<GradeAgendamentoExameDTO> getGradeAgendamentoExame(@PathVariable Long id) {
        log.debug("REST request to get GradeAgendamentoExame : {}", id);
        Optional<GradeAgendamentoExameDTO> gradeAgendamentoExameDTO = gradeAgendamentoExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gradeAgendamentoExameDTO);
    }

    /**
     * {@code DELETE  /grade-agendamento-exames/:id} : delete the "id" gradeAgendamentoExame.
     *
     * @param id the id of the gradeAgendamentoExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grade-agendamento-exames/{id}")
    public ResponseEntity<Void> deleteGradeAgendamentoExame(@PathVariable Long id) {
        log.debug("REST request to delete GradeAgendamentoExame : {}", id);
        gradeAgendamentoExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grade-agendamento-exames?query=:query} : search for the gradeAgendamentoExame corresponding
     * to the query.
     *
     * @param query the query of the gradeAgendamentoExame search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grade-agendamento-exames")
    public ResponseEntity<List<GradeAgendamentoExameDTO>> searchGradeAgendamentoExames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GradeAgendamentoExames for query {}", query);
        Page<GradeAgendamentoExameDTO> page = gradeAgendamentoExameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    @GetMapping("/_search/grades-de-agendamento")
    public ResponseEntity<List<GradeAgendamentoExameDTO>> obterTodasAsGrades(Pageable pageable,
         @RequestParam(name = "id", required = false) String id,
         @RequestParam(name = "unidadeExecutoraId", required = false) String unidadeExecutoraId,
         @RequestParam(name = "ativo", required = false) String ativo,
         @RequestParam(name = "duracao", required = false) String duracao,
         @RequestParam(name = "responsavelId", required = false) String responsavelId,
         @RequestParam(name = "exameId", required = false) String exameId,
         @RequestParam(name = "salaId", required = false) String salaId
         ) {
        log.debug("Request REST para obter uma lista de grades de agendamento de exames");
        Page<GradeAgendamentoExameDTO> page = gradeAgendamentoExameService.filtrarGradeAgendamento(
          pageable, id, unidadeExecutoraId, ativo, duracao, responsavelId, exameId, salaId);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }
}
