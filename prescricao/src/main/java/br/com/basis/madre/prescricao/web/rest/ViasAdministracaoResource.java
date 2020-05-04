package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.domain.ViasAdministracao;
import br.com.basis.madre.prescricao.repository.ViasAdministracaoRepository;
import br.com.basis.madre.prescricao.repository.search.ViasAdministracaoSearchRepository;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ViasAdministracao}.
 */
@RestController
@RequestMapping("/api")
public class ViasAdministracaoResource {

    private final Logger log = LoggerFactory.getLogger(ViasAdministracaoResource.class);

    private static final String ENTITY_NAME = "prescricaoViasAdministracao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViasAdministracaoRepository viasAdministracaoRepository;

    private final ViasAdministracaoSearchRepository viasAdministracaoSearchRepository;

    public ViasAdministracaoResource(ViasAdministracaoRepository viasAdministracaoRepository, ViasAdministracaoSearchRepository viasAdministracaoSearchRepository) {
        this.viasAdministracaoRepository = viasAdministracaoRepository;
        this.viasAdministracaoSearchRepository = viasAdministracaoSearchRepository;
    }

    /**
     * {@code POST  /vias-administracaos} : Create a new viasAdministracao.
     *
     * @param viasAdministracao the viasAdministracao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viasAdministracao, or with status {@code 400 (Bad Request)} if the viasAdministracao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vias-administracaos")
    public ResponseEntity<ViasAdministracao> createViasAdministracao(@Valid @RequestBody ViasAdministracao viasAdministracao) throws URISyntaxException {
        log.debug("REST request to save ViasAdministracao : {}", viasAdministracao);
        if (viasAdministracao.getId() != null) {
            throw new BadRequestAlertException("A new viasAdministracao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViasAdministracao result = viasAdministracaoRepository.save(viasAdministracao);
        viasAdministracaoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vias-administracaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vias-administracaos} : Updates an existing viasAdministracao.
     *
     * @param viasAdministracao the viasAdministracao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viasAdministracao,
     * or with status {@code 400 (Bad Request)} if the viasAdministracao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viasAdministracao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vias-administracaos")
    public ResponseEntity<ViasAdministracao> updateViasAdministracao(@Valid @RequestBody ViasAdministracao viasAdministracao) throws URISyntaxException {
        log.debug("REST request to update ViasAdministracao : {}", viasAdministracao);
        if (viasAdministracao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ViasAdministracao result = viasAdministracaoRepository.save(viasAdministracao);
        viasAdministracaoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, viasAdministracao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vias-administracaos} : get all the viasAdministracaos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viasAdministracaos in body.
     */
    @GetMapping("/vias-administracaos")
    public List<ViasAdministracao> getAllViasAdministracaos() {
        log.debug("REST request to get all ViasAdministracaos");
        return viasAdministracaoRepository.findAll();
    }

    /**
     * {@code GET  /vias-administracaos/:id} : get the "id" viasAdministracao.
     *
     * @param id the id of the viasAdministracao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viasAdministracao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vias-administracaos/{id}")
    public ResponseEntity<ViasAdministracao> getViasAdministracao(@PathVariable Long id) {
        log.debug("REST request to get ViasAdministracao : {}", id);
        Optional<ViasAdministracao> viasAdministracao = viasAdministracaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(viasAdministracao);
    }

    /**
     * {@code DELETE  /vias-administracaos/:id} : delete the "id" viasAdministracao.
     *
     * @param id the id of the viasAdministracao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vias-administracaos/{id}")
    public ResponseEntity<Void> deleteViasAdministracao(@PathVariable Long id) {
        log.debug("REST request to delete ViasAdministracao : {}", id);
        viasAdministracaoRepository.deleteById(id);
        viasAdministracaoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/vias-administracaos?query=:query} : search for the viasAdministracao corresponding
     * to the query.
     *
     * @param query the query of the viasAdministracao search.
     * @return the result of the search.
     */
    @GetMapping("/_search/vias-administracaos")
    public List<ViasAdministracao> searchViasAdministracaos(@RequestParam String query) {
        log.debug("REST request to search ViasAdministracaos for query {}", query);
        return StreamSupport
            .stream(viasAdministracaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
