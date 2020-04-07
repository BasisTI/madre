package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.TelefoneService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.TelefoneDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Telefone}.
 */
@RestController
@RequestMapping("/api")
public class TelefoneResource {

    private final Logger log = LoggerFactory.getLogger(TelefoneResource.class);

    private static final String ENTITY_NAME = "pacientesTelefone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoneService telefoneService;

    public TelefoneResource(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    /**
     * {@code POST  /telefones} : Create a new telefone.
     *
     * @param telefoneDTO the telefoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telefoneDTO, or with status {@code 400 (Bad Request)} if the telefone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telefones")
    public ResponseEntity<TelefoneDTO> createTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        log.debug("REST request to save Telefone : {}", telefoneDTO);
        if (telefoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new telefone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelefoneDTO result = telefoneService.save(telefoneDTO);
        return ResponseEntity.created(new URI("/api/telefones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telefones} : Updates an existing telefone.
     *
     * @param telefoneDTO the telefoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoneDTO,
     * or with status {@code 400 (Bad Request)} if the telefoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telefoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telefones")
    public ResponseEntity<TelefoneDTO> updateTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        log.debug("REST request to update Telefone : {}", telefoneDTO);
        if (telefoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TelefoneDTO result = telefoneService.save(telefoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telefoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /telefones} : get all the telefones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telefones in body.
     */
    @GetMapping("/telefones")
    public ResponseEntity<List<TelefoneDTO>> getAllTelefones(Pageable pageable) {
        log.debug("REST request to get a page of Telefones");
        Page<TelefoneDTO> page = telefoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /telefones/:id} : get the "id" telefone.
     *
     * @param id the id of the telefoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telefoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telefones/{id}")
    public ResponseEntity<TelefoneDTO> getTelefone(@PathVariable Long id) {
        log.debug("REST request to get Telefone : {}", id);
        Optional<TelefoneDTO> telefoneDTO = telefoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telefoneDTO);
    }

    /**
     * {@code DELETE  /telefones/:id} : delete the "id" telefone.
     *
     * @param id the id of the telefoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telefones/{id}")
    public ResponseEntity<Void> deleteTelefone(@PathVariable Long id) {
        log.debug("REST request to delete Telefone : {}", id);
        telefoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/telefones?query=:query} : search for the telefone corresponding
     * to the query.
     *
     * @param query the query of the telefone search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/telefones")
    public ResponseEntity<List<TelefoneDTO>> searchTelefones(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Telefones for query {}", query);
        Page<TelefoneDTO> page = telefoneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
