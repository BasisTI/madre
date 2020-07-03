package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.MotivoDoCadastroService;
import br.com.basis.madre.service.dto.MotivoDoCadastroDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.MotivoDoCadastro}.
 */
@RestController
@RequestMapping("/api")
public class MotivoDoCadastroResource {

    private final Logger log = LoggerFactory.getLogger(MotivoDoCadastroResource.class);

    private static final String ENTITY_NAME = "pacientesMotivoDoCadastro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivoDoCadastroService motivoDoCadastroService;

    public MotivoDoCadastroResource(MotivoDoCadastroService motivoDoCadastroService) {
        this.motivoDoCadastroService = motivoDoCadastroService;
    }

    /**
     * {@code POST  /motivo-do-cadastros} : Create a new motivoDoCadastro.
     *
     * @param motivoDoCadastroDTO the motivoDoCadastroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivoDoCadastroDTO, or with status {@code 400 (Bad Request)} if the motivoDoCadastro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivo-do-cadastros")
    public ResponseEntity<MotivoDoCadastroDTO> createMotivoDoCadastro(@Valid @RequestBody MotivoDoCadastroDTO motivoDoCadastroDTO) throws URISyntaxException {
        log.debug("REST request to save MotivoDoCadastro : {}", motivoDoCadastroDTO);
        if (motivoDoCadastroDTO.getId() != null) {
            throw new BadRequestAlertException("A new motivoDoCadastro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotivoDoCadastroDTO result = motivoDoCadastroService.save(motivoDoCadastroDTO);
        return ResponseEntity.created(new URI("/api/motivo-do-cadastros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motivo-do-cadastros} : Updates an existing motivoDoCadastro.
     *
     * @param motivoDoCadastroDTO the motivoDoCadastroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivoDoCadastroDTO,
     * or with status {@code 400 (Bad Request)} if the motivoDoCadastroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivoDoCadastroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivo-do-cadastros")
    public ResponseEntity<MotivoDoCadastroDTO> updateMotivoDoCadastro(@Valid @RequestBody MotivoDoCadastroDTO motivoDoCadastroDTO) throws URISyntaxException {
        log.debug("REST request to update MotivoDoCadastro : {}", motivoDoCadastroDTO);
        if (motivoDoCadastroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MotivoDoCadastroDTO result = motivoDoCadastroService.save(motivoDoCadastroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, motivoDoCadastroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motivo-do-cadastros} : get all the motivoDoCadastros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motivoDoCadastros in body.
     */
    @GetMapping("/motivo-do-cadastros")
    public ResponseEntity<List<MotivoDoCadastroDTO>> getAllMotivoDoCadastros(MotivoDoCadastroDTO motivoDoCadastroDTO, Pageable pageable) {
        log.debug("REST request to get a page of MotivoDoCadastros");
        Page<MotivoDoCadastroDTO> page = motivoDoCadastroService.findAll(motivoDoCadastroDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /motivo-do-cadastros/:id} : get the "id" motivoDoCadastro.
     *
     * @param id the id of the motivoDoCadastroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivoDoCadastroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivo-do-cadastros/{id}")
    public ResponseEntity<MotivoDoCadastroDTO> getMotivoDoCadastro(@PathVariable Long id) {
        log.debug("REST request to get MotivoDoCadastro : {}", id);
        Optional<MotivoDoCadastroDTO> motivoDoCadastroDTO = motivoDoCadastroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motivoDoCadastroDTO);
    }

    /**
     * {@code DELETE  /motivo-do-cadastros/:id} : delete the "id" motivoDoCadastro.
     *
     * @param id the id of the motivoDoCadastroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivo-do-cadastros/{id}")
    public ResponseEntity<Void> deleteMotivoDoCadastro(@PathVariable Long id) {
        log.debug("REST request to delete MotivoDoCadastro : {}", id);
        motivoDoCadastroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/motivo-do-cadastros?query=:query} : search for the motivoDoCadastro corresponding
     * to the query.
     *
     * @param query the query of the motivoDoCadastro search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/motivo-do-cadastros")
    public ResponseEntity<List<MotivoDoCadastroDTO>> searchMotivoDoCadastros(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MotivoDoCadastros for query {}", query);
        Page<MotivoDoCadastroDTO> page = motivoDoCadastroService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
