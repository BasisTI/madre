package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.ProjetoDePesquisaService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.ProjetoDePesquisaDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link br.com.basis.madre.exames.domain.ProjetoDePesquisa}.
 */
@RestController
@RequestMapping("/api")
public class ProjetoDePesquisaResource {

    private final Logger log = LoggerFactory.getLogger(ProjetoDePesquisaResource.class);

    private static final String ENTITY_NAME = "madreexamesProjetoDePesquisa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjetoDePesquisaService projetoDePesquisaService;

    public ProjetoDePesquisaResource(ProjetoDePesquisaService projetoDePesquisaService) {
        this.projetoDePesquisaService = projetoDePesquisaService;
    }

    /**
     * {@code POST  /projeto-de-pesquisas} : Create a new projetoDePesquisa.
     *
     * @param projetoDePesquisaDTO the projetoDePesquisaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projetoDePesquisaDTO, or with status {@code 400 (Bad Request)} if the projetoDePesquisa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projeto-de-pesquisas")
    public ResponseEntity<ProjetoDePesquisaDTO> createProjetoDePesquisa(@Valid @RequestBody ProjetoDePesquisaDTO projetoDePesquisaDTO) throws URISyntaxException {
        log.debug("REST request to save ProjetoDePesquisa : {}", projetoDePesquisaDTO);
        if (projetoDePesquisaDTO.getId() != null) {
            throw new BadRequestAlertException("A new projetoDePesquisa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjetoDePesquisaDTO result = projetoDePesquisaService.save(projetoDePesquisaDTO);
        return ResponseEntity.created(new URI("/api/projeto-de-pesquisas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projeto-de-pesquisas} : Updates an existing projetoDePesquisa.
     *
     * @param projetoDePesquisaDTO the projetoDePesquisaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projetoDePesquisaDTO,
     * or with status {@code 400 (Bad Request)} if the projetoDePesquisaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projetoDePesquisaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projeto-de-pesquisas")
    public ResponseEntity<ProjetoDePesquisaDTO> updateProjetoDePesquisa(@Valid @RequestBody ProjetoDePesquisaDTO projetoDePesquisaDTO) throws URISyntaxException {
        log.debug("REST request to update ProjetoDePesquisa : {}", projetoDePesquisaDTO);
        if (projetoDePesquisaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjetoDePesquisaDTO result = projetoDePesquisaService.save(projetoDePesquisaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projetoDePesquisaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projeto-de-pesquisas} : get all the projetoDePesquisas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projetoDePesquisas in body.
     */
    @GetMapping("/projeto-de-pesquisas")
    public List<ProjetoDePesquisaDTO> getAllProjetoDePesquisas() {
        log.debug("REST request to get all ProjetoDePesquisas");
        return projetoDePesquisaService.findAll();
    }

    /**
     * {@code GET  /projeto-de-pesquisas/:id} : get the "id" projetoDePesquisa.
     *
     * @param id the id of the projetoDePesquisaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projetoDePesquisaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projeto-de-pesquisas/{id}")
    public ResponseEntity<ProjetoDePesquisaDTO> getProjetoDePesquisa(@PathVariable Long id) {
        log.debug("REST request to get ProjetoDePesquisa : {}", id);
        Optional<ProjetoDePesquisaDTO> projetoDePesquisaDTO = projetoDePesquisaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projetoDePesquisaDTO);
    }

    /**
     * {@code DELETE  /projeto-de-pesquisas/:id} : delete the "id" projetoDePesquisa.
     *
     * @param id the id of the projetoDePesquisaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projeto-de-pesquisas/{id}")
    public ResponseEntity<Void> deleteProjetoDePesquisa(@PathVariable Long id) {
        log.debug("REST request to delete ProjetoDePesquisa : {}", id);
        projetoDePesquisaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/projeto-de-pesquisas?query=:query} : search for the projetoDePesquisa corresponding
     * to the query.
     *
     * @param query the query of the projetoDePesquisa search.
     * @return the result of the search.
     */
    @GetMapping("/_search/projeto-de-pesquisas")
    public List<ProjetoDePesquisaDTO> searchProjetoDePesquisas(@RequestParam String query) {
        log.debug("REST request to search ProjetoDePesquisas for query {}", query);
        return projetoDePesquisaService.search(query);
    }
}
