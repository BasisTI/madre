package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.GrupoAgendamentoExameService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.GrupoAgendamentoExameDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.GrupoAgendamentoExame}.
 */
@RestController
@RequestMapping("/api")
public class GrupoAgendamentoExameResource {

    private final Logger log = LoggerFactory.getLogger(GrupoAgendamentoExameResource.class);

    private static final String ENTITY_NAME = "madreexamesGrupoAgendamentoExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoAgendamentoExameService grupoAgendamentoExameService;

    public GrupoAgendamentoExameResource(GrupoAgendamentoExameService grupoAgendamentoExameService) {
        this.grupoAgendamentoExameService = grupoAgendamentoExameService;
    }

    /**
     * {@code POST  /grupo-agendamento-exames} : Create a new grupoAgendamentoExame.
     *
     * @param grupoAgendamentoExameDTO the grupoAgendamentoExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoAgendamentoExameDTO, or with status {@code 400 (Bad Request)} if the grupoAgendamentoExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-agendamento-exames")
    public ResponseEntity<GrupoAgendamentoExameDTO> createGrupoAgendamentoExame(@Valid @RequestBody GrupoAgendamentoExameDTO grupoAgendamentoExameDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoAgendamentoExame : {}", grupoAgendamentoExameDTO);
        if (grupoAgendamentoExameDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoAgendamentoExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoAgendamentoExameDTO result = grupoAgendamentoExameService.save(grupoAgendamentoExameDTO);
        return ResponseEntity.created(new URI("/api/grupo-agendamento-exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-agendamento-exames} : Updates an existing grupoAgendamentoExame.
     *
     * @param grupoAgendamentoExameDTO the grupoAgendamentoExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoAgendamentoExameDTO,
     * or with status {@code 400 (Bad Request)} if the grupoAgendamentoExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoAgendamentoExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-agendamento-exames")
    public ResponseEntity<GrupoAgendamentoExameDTO> updateGrupoAgendamentoExame(@Valid @RequestBody GrupoAgendamentoExameDTO grupoAgendamentoExameDTO) throws URISyntaxException {
        log.debug("REST request to update GrupoAgendamentoExame : {}", grupoAgendamentoExameDTO);
        if (grupoAgendamentoExameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoAgendamentoExameDTO result = grupoAgendamentoExameService.save(grupoAgendamentoExameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoAgendamentoExameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupo-agendamento-exames} : get all the grupoAgendamentoExames.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoAgendamentoExames in body.
     */
    @GetMapping("/grupo-agendamento-exames")
    public ResponseEntity<List<GrupoAgendamentoExameDTO>> getAllGrupoAgendamentoExames(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of GrupoAgendamentoExames");
        Page<GrupoAgendamentoExameDTO> page;
        if (eagerload) {
            page = grupoAgendamentoExameService.findAllWithEagerRelationships(pageable);
        } else {
            page = grupoAgendamentoExameService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupo-agendamento-exames/:id} : get the "id" grupoAgendamentoExame.
     *
     * @param id the id of the grupoAgendamentoExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoAgendamentoExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-agendamento-exames/{id}")
    public ResponseEntity<GrupoAgendamentoExameDTO> getGrupoAgendamentoExame(@PathVariable Long id) {
        log.debug("REST request to get GrupoAgendamentoExame : {}", id);
        Optional<GrupoAgendamentoExameDTO> grupoAgendamentoExameDTO = grupoAgendamentoExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoAgendamentoExameDTO);
    }

    /**
     * {@code DELETE  /grupo-agendamento-exames/:id} : delete the "id" grupoAgendamentoExame.
     *
     * @param id the id of the grupoAgendamentoExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-agendamento-exames/{id}")
    public ResponseEntity<Void> deleteGrupoAgendamentoExame(@PathVariable Long id) {
        log.debug("REST request to delete GrupoAgendamentoExame : {}", id);
        grupoAgendamentoExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grupo-agendamento-exames?query=:query} : search for the grupoAgendamentoExame corresponding
     * to the query.
     *
     * @param query the query of the grupoAgendamentoExame search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grupo-agendamento-exames")
    public ResponseEntity<List<GrupoAgendamentoExameDTO>> searchGrupoAgendamentoExames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrupoAgendamentoExames for query {}", query);
        Page<GrupoAgendamentoExameDTO> page = grupoAgendamentoExameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
