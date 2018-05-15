package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.service.PerfilService;
import br.com.basis.madre.cadastros.service.dto.PerfilDTO;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Perfil.
 */
@RestController
@RequestMapping("/api")
public class PerfilResource {

    private static final String ENTITY_NAME = "perfil";

    private final Logger log = LoggerFactory.getLogger(PerfilResource.class);

    private final PerfilService perfilService;

    public PerfilResource(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    /**
     * POST  /perfils : Create a new perfil.
     *
     * @param perfilDTO the perfilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perfilDTO, or with status 400 (Bad Request) if the perfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/perfils")
    @Timed
    public ResponseEntity<PerfilDTO> createPerfil(@Valid @RequestBody PerfilDTO perfilDTO) throws URISyntaxException {
        log.debug("REST request to save Perfil : {}", perfilDTO);
        if (perfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerfilDTO result = perfilService.save(perfilDTO);
        return ResponseEntity.created(new URI("/api/perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /perfils : Updates an existing perfil.
     *
     * @param perfilDTO the perfilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perfilDTO,
     * or with status 400 (Bad Request) if the perfilDTO is not valid,
     * or with status 500 (Internal Server Error) if the perfilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/perfils")
    @Timed
    public ResponseEntity<PerfilDTO> updatePerfil(@Valid @RequestBody PerfilDTO perfilDTO) throws URISyntaxException {
        log.debug("REST request to update Perfil : {}", perfilDTO);
        if (perfilDTO.getId() == null) {
            return createPerfil(perfilDTO);
        }
        PerfilDTO result = perfilService.save(perfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, perfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /perfils : get all the perfils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of perfils in body
     */
    @GetMapping("/perfils")
    @Timed
    public ResponseEntity<List<PerfilDTO>> getAllPerfils(Pageable pageable) {
        log.debug("REST request to get a page of Perfils");
        Page<PerfilDTO> page = perfilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/perfils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /perfils/:id : get the "id" perfil.
     *
     * @param id the id of the perfilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perfilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/perfils/{id}")
    @Timed
    public ResponseEntity<PerfilDTO> getPerfil(@PathVariable Long id) {
        log.debug("REST request to get Perfil : {}", id);
        PerfilDTO perfilDTO = perfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(perfilDTO));
    }

    /**
     * DELETE  /perfils/:id : delete the "id" perfil.
     *
     * @param id the id of the perfilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/perfils/{id}")
    @Timed
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        log.debug("REST request to delete Perfil : {}", id);
        perfilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/perfils?query=:query : search for the perfil corresponding
     * to the query.
     *
     * @param query the query of the perfil search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/perfils")
    @Timed
    public ResponseEntity<List<PerfilDTO>> searchPerfils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Perfils for query {}", query);
        Page<PerfilDTO> page = perfilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/perfils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
