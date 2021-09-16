package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.domain.HorarioAgendado;
import br.com.basis.madre.madreexames.service.HorarioAgendadoService;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.HorarioAgendado}.
 */
@RestController
@RequestMapping("/api")
public class HorarioAgendadoResource {

    private final Logger log = LoggerFactory.getLogger(HorarioAgendadoResource.class);

    private static final String ENTITY_NAME = "madreexamesHorarioAgendado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorarioAgendadoService horarioAgendadoService;

    public HorarioAgendadoResource(HorarioAgendadoService horarioAgendadoService) {
        this.horarioAgendadoService = horarioAgendadoService;
    }

    /**
     * {@code POST  /horario-agendados} : Create a new horarioAgendado.
     *
     * @param horarioAgendadoDTO the horarioAgendadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horarioAgendadoDTO, or with status {@code 400 (Bad Request)} if the horarioAgendado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horario-agendados")
    public ResponseEntity<HorarioAgendadoDTO> createHorarioAgendado(@Valid @RequestBody HorarioAgendadoDTO horarioAgendadoDTO) throws URISyntaxException {
        log.debug("REST request to save HorarioAgendado : {}", horarioAgendadoDTO);
        if (horarioAgendadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new horarioAgendado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioAgendadoDTO result = horarioAgendadoService.save(horarioAgendadoDTO);
        return ResponseEntity.created(new URI("/api/horario-agendados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horario-agendados} : Updates an existing horarioAgendado.
     *
     * @param horarioAgendadoDTO the horarioAgendadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horarioAgendadoDTO,
     * or with status {@code 400 (Bad Request)} if the horarioAgendadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horarioAgendadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horario-agendados")
    public ResponseEntity<HorarioAgendadoDTO> updateHorarioAgendado(@Valid @RequestBody HorarioAgendadoDTO horarioAgendadoDTO) throws URISyntaxException {
        log.debug("REST request to update HorarioAgendado : {}", horarioAgendadoDTO);
        if (horarioAgendadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioAgendadoDTO result = horarioAgendadoService.save(horarioAgendadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horarioAgendadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horario-agendados} : get all the horarioAgendados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horarioAgendados in body.
     */
    @GetMapping("/horario-agendados")
    public ResponseEntity<List<HorarioAgendadoDTO>> getAllHorarioAgendados(Pageable pageable) {
        log.debug("REST request to get a page of HorarioAgendados");
        Page<HorarioAgendadoDTO> page = horarioAgendadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horario-agendados/:id} : get the "id" horarioAgendado.
     *
     * @param id the id of the horarioAgendadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horarioAgendadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horario-agendados/{id}")
    public ResponseEntity<HorarioAgendadoDTO> getHorarioAgendado(@PathVariable Long id) {
        log.debug("REST request to get HorarioAgendado : {}", id);
        Optional<HorarioAgendadoDTO> horarioAgendadoDTO = horarioAgendadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioAgendadoDTO);
    }

    /**
     * {@code DELETE  /horario-agendados/:id} : delete the "id" horarioAgendado.
     *
     * @param id the id of the horarioAgendadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horario-agendados/{id}")
    public ResponseEntity<Void> deleteHorarioAgendado(@PathVariable Long id) {
        log.debug("REST request to delete HorarioAgendado : {}", id);
        horarioAgendadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/horario-agendados?query=:query} : search for the horarioAgendado corresponding
     * to the query.
     *
     * @param query the query of the horarioAgendado search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/horario-agendados")
    public ResponseEntity<List<HorarioAgendadoDTO>> searchHorarioAgendados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HorarioAgendados for query {}", query);
        Page<HorarioAgendadoDTO> page = horarioAgendadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    @GetMapping("/_search/horarios-agendados")
    public ResponseEntity<List<HorarioAgendadoDTO>> obterTodosHorarios(Pageable pageable,
        @RequestParam(name = "id", required = false) String id,
        @RequestParam(name = "horaInicio", required = false) String horaInicio,
        @RequestParam(name = "numeroDeHorarios", required = false) String numeroDeHorarios,
        @RequestParam(name = "dia", required = false) String dia,
        @RequestParam(name = "ativo", required = false) String ativo,
        @RequestParam(name = "exclusivo", required = false) String exclusivo
    ) {
        log.debug("Request REST para obter uma página de solicitações de exame.");
        Page<HorarioAgendadoDTO> page = horarioAgendadoService.filtraHorarioAgendado(pageable, id, horaInicio,
            numeroDeHorarios, dia, ativo, exclusivo);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
