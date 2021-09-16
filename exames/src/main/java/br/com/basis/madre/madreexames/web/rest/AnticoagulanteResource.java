package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.AnticoagulanteService;
import br.com.basis.madre.madreexames.service.dto.AnticoagulanteDTO;
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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.Anticoagulante}.
 */
@RestController
@RequestMapping("/api")
public class AnticoagulanteResource {

    private final Logger log = LoggerFactory.getLogger(AnticoagulanteResource.class);

    private static final String ENTITY_NAME = "madreexamesAnticoagulante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnticoagulanteService anticoagulanteService;

    public AnticoagulanteResource(AnticoagulanteService anticoagulanteService) {
        this.anticoagulanteService = anticoagulanteService;
    }

    /**
     * {@code POST  /anticoagulantes} : Create a new anticoagulante.
     *
     * @param anticoagulanteDTO the anticoagulanteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anticoagulanteDTO, or with status {@code 400 (Bad Request)} if the anticoagulante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anticoagulantes")
    public ResponseEntity<AnticoagulanteDTO> createAnticoagulante(@Valid @RequestBody AnticoagulanteDTO anticoagulanteDTO) throws URISyntaxException {
        log.debug("REST request to save Anticoagulante : {}", anticoagulanteDTO);
        if (anticoagulanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new anticoagulante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnticoagulanteDTO result = anticoagulanteService.save(anticoagulanteDTO);
        return ResponseEntity.created(new URI("/api/anticoagulantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anticoagulantes} : Updates an existing anticoagulante.
     *
     * @param anticoagulanteDTO the anticoagulanteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anticoagulanteDTO,
     * or with status {@code 400 (Bad Request)} if the anticoagulanteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anticoagulanteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anticoagulantes")
    public ResponseEntity<AnticoagulanteDTO> updateAnticoagulante(@Valid @RequestBody AnticoagulanteDTO anticoagulanteDTO) throws URISyntaxException {
        log.debug("REST request to update Anticoagulante : {}", anticoagulanteDTO);
        if (anticoagulanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnticoagulanteDTO result = anticoagulanteService.save(anticoagulanteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anticoagulanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anticoagulantes} : get all the anticoagulantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anticoagulantes in body.
     */
    @GetMapping("/anticoagulantes")
    public ResponseEntity<List<AnticoagulanteDTO>> getAllAnticoagulantes(Pageable pageable) {
        log.debug("REST request to get a page of Anticoagulantes");
        Page<AnticoagulanteDTO> page = anticoagulanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anticoagulantes/:id} : get the "id" anticoagulante.
     *
     * @param id the id of the anticoagulanteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anticoagulanteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anticoagulantes/{id}")
    public ResponseEntity<AnticoagulanteDTO> getAnticoagulante(@PathVariable Long id) {
        log.debug("REST request to get Anticoagulante : {}", id);
        Optional<AnticoagulanteDTO> anticoagulanteDTO = anticoagulanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anticoagulanteDTO);
    }

    /**
     * {@code DELETE  /anticoagulantes/:id} : delete the "id" anticoagulante.
     *
     * @param id the id of the anticoagulanteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anticoagulantes/{id}")
    public ResponseEntity<Void> deleteAnticoagulante(@PathVariable Long id) {
        log.debug("REST request to delete Anticoagulante : {}", id);
        anticoagulanteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/anticoagulantes?query=:query} : search for the anticoagulante corresponding
     * to the query.
     *
     * @param query the query of the anticoagulante search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/anticoagulantes")
    public ResponseEntity<List<AnticoagulanteDTO>> searchAnticoagulantes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Anticoagulantes for query {}", query);
        Page<AnticoagulanteDTO> page = anticoagulanteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
