package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.HorarioExameService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.HorarioExameDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.HorarioExame}.
 */
@RestController
@RequestMapping("/api")
public class HorarioExameResource {

    private final Logger log = LoggerFactory.getLogger(HorarioExameResource.class);

    private static final String ENTITY_NAME = "madreexamesHorarioExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorarioExameService horarioExameService;

    public HorarioExameResource(HorarioExameService horarioExameService) {
        this.horarioExameService = horarioExameService;
    }

    /**
     * {@code POST  /horario-exames} : Create a new horarioExame.
     *
     * @param horarioExameDTO the horarioExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horarioExameDTO, or with status {@code 400 (Bad Request)} if the horarioExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horario-exames")
    public ResponseEntity<HorarioExameDTO> createHorarioExame(@Valid @RequestBody HorarioExameDTO horarioExameDTO) throws URISyntaxException {
        log.debug("REST request to save HorarioExame : {}", horarioExameDTO);
        if (horarioExameDTO.getId() != null) {
            throw new BadRequestAlertException("A new horarioExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioExameDTO result = horarioExameService.save(horarioExameDTO);
        return ResponseEntity.created(new URI("/api/horario-exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horario-exames} : Updates an existing horarioExame.
     *
     * @param horarioExameDTO the horarioExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horarioExameDTO,
     * or with status {@code 400 (Bad Request)} if the horarioExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horarioExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horario-exames")
    public ResponseEntity<HorarioExameDTO> updateHorarioExame(@Valid @RequestBody HorarioExameDTO horarioExameDTO) throws URISyntaxException {
        log.debug("REST request to update HorarioExame : {}", horarioExameDTO);
        if (horarioExameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioExameDTO result = horarioExameService.save(horarioExameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horarioExameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horario-exames} : get all the horarioExames.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horarioExames in body.
     */
    @GetMapping("/horario-exames")
    public ResponseEntity<List<HorarioExameDTO>> getAllHorarioExames(Pageable pageable) {
        log.debug("REST request to get a page of HorarioExames");
        Page<HorarioExameDTO> page = horarioExameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horario-exames/:id} : get the "id" horarioExame.
     *
     * @param id the id of the horarioExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horarioExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horario-exames/{id}")
    public ResponseEntity<HorarioExameDTO> getHorarioExame(@PathVariable Long id) {
        log.debug("REST request to get HorarioExame : {}", id);
        Optional<HorarioExameDTO> horarioExameDTO = horarioExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioExameDTO);
    }

    /**
     * {@code DELETE  /horario-exames/:id} : delete the "id" horarioExame.
     *
     * @param id the id of the horarioExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horario-exames/{id}")
    public ResponseEntity<Void> deleteHorarioExame(@PathVariable Long id) {
        log.debug("REST request to delete HorarioExame : {}", id);
        horarioExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/horario-exames?query=:query} : search for the horarioExame corresponding
     * to the query.
     *
     * @param query the query of the horarioExame search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/horario-exames")
    public ResponseEntity<List<HorarioExameDTO>> searchHorarioExames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HorarioExames for query {}", query);
        Page<HorarioExameDTO> page = horarioExameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
