package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.HorarioLivreService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.HorarioLivreDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.HorarioLivre}.
 */
@RestController
@RequestMapping("/api")
public class HorarioLivreResource {

    private final Logger log = LoggerFactory.getLogger(HorarioLivreResource.class);

    private static final String ENTITY_NAME = "madreexamesHorarioLivre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorarioLivreService horarioLivreService;

    public HorarioLivreResource(HorarioLivreService horarioLivreService) {
        this.horarioLivreService = horarioLivreService;
    }

    /**
     * {@code POST  /horario-livres} : Create a new horarioLivre.
     *
     * @param horarioLivreDTO the horarioLivreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horarioLivreDTO, or with status {@code 400 (Bad Request)} if the horarioLivre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/horario-livres")
    public ResponseEntity<HorarioLivreDTO> createHorarioLivre(@Valid @RequestBody HorarioLivreDTO horarioLivreDTO) throws URISyntaxException {
        log.debug("REST request to save HorarioLivre : {}", horarioLivreDTO);
        if (horarioLivreDTO.getId() != null) {
            throw new BadRequestAlertException("A new horarioLivre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioLivreDTO result = horarioLivreService.save(horarioLivreDTO);
        return ResponseEntity.created(new URI("/api/horario-livres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /horario-livres} : Updates an existing horarioLivre.
     *
     * @param horarioLivreDTO the horarioLivreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horarioLivreDTO,
     * or with status {@code 400 (Bad Request)} if the horarioLivreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horarioLivreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/horario-livres")
    public ResponseEntity<HorarioLivreDTO> updateHorarioLivre(@Valid @RequestBody HorarioLivreDTO horarioLivreDTO) throws URISyntaxException {
        log.debug("REST request to update HorarioLivre : {}", horarioLivreDTO);
        if (horarioLivreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioLivreDTO result = horarioLivreService.save(horarioLivreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, horarioLivreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /horario-livres} : get all the horarioLivres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horarioLivres in body.
     */
    @GetMapping("/horario-livres")
    public ResponseEntity<List<HorarioLivreDTO>> getAllHorarioLivres(Pageable pageable) {
        log.debug("REST request to get a page of HorarioLivres");
        Page<HorarioLivreDTO> page = horarioLivreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horario-livres/:id} : get the "id" horarioLivre.
     *
     * @param id the id of the horarioLivreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horarioLivreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/horario-livres/{id}")
    public ResponseEntity<HorarioLivreDTO> getHorarioLivre(@PathVariable Long id) {
        log.debug("REST request to get HorarioLivre : {}", id);
        Optional<HorarioLivreDTO> horarioLivreDTO = horarioLivreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioLivreDTO);
    }

    /**
     * {@code DELETE  /horario-livres/:id} : delete the "id" horarioLivre.
     *
     * @param id the id of the horarioLivreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/horario-livres/{id}")
    public ResponseEntity<Void> deleteHorarioLivre(@PathVariable Long id) {
        log.debug("REST request to delete HorarioLivre : {}", id);
        horarioLivreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/horario-livres?query=:query} : search for the horarioLivre corresponding
     * to the query.
     *
     * @param query the query of the horarioLivre search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/horario-livres")
    public ResponseEntity<List<HorarioLivreDTO>> searchHorarioLivres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of HorarioLivres for query {}", query);
        Page<HorarioLivreDTO> page = horarioLivreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
