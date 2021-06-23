package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.SolicitacaoExameService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.SolicitacaoExameDTO;

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
 * REST controller for managing {@link br.com.basis.madre.exames.domain.SolicitacaoExame}.
 */
@RestController
@RequestMapping("/api")
public class SolicitacaoExameResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoExameResource.class);

    private static final String ENTITY_NAME = "madreexamesSolicitacaoExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoExameService solicitacaoExameService;

    public SolicitacaoExameResource(SolicitacaoExameService solicitacaoExameService) {
        this.solicitacaoExameService = solicitacaoExameService;
    }

    /**
     * {@code POST  /solicitacao-exames} : Create a new solicitacaoExame.
     *
     * @param solicitacaoExameDTO the solicitacaoExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitacaoExameDTO, or with status {@code 400 (Bad Request)} if the solicitacaoExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacao-exames")
    public ResponseEntity<SolicitacaoExameDTO> createSolicitacaoExame(@Valid @RequestBody SolicitacaoExameDTO solicitacaoExameDTO) throws URISyntaxException {
        log.debug("REST request to save SolicitacaoExame : {}", solicitacaoExameDTO);
        if (solicitacaoExameDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitacaoExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoExameDTO result = solicitacaoExameService.save(solicitacaoExameDTO);
        return ResponseEntity.created(new URI("/api/solicitacao-exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacao-exames} : Updates an existing solicitacaoExame.
     *
     * @param solicitacaoExameDTO the solicitacaoExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacaoExameDTO,
     * or with status {@code 400 (Bad Request)} if the solicitacaoExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitacaoExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacao-exames")
    public ResponseEntity<SolicitacaoExameDTO> updateSolicitacaoExame(@Valid @RequestBody SolicitacaoExameDTO solicitacaoExameDTO) throws URISyntaxException {
        log.debug("REST request to update SolicitacaoExame : {}", solicitacaoExameDTO);
        if (solicitacaoExameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitacaoExameDTO result = solicitacaoExameService.save(solicitacaoExameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoExameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicitacao-exames} : get all the solicitacaoExames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitacaoExames in body.
     */
    @GetMapping("/solicitacao-exames")
    public List<SolicitacaoExameDTO> getAllSolicitacaoExames() {
        log.debug("REST request to get all SolicitacaoExames");
        return solicitacaoExameService.findAll();
    }

    /**
     * {@code GET  /solicitacao-exames/:id} : get the "id" solicitacaoExame.
     *
     * @param id the id of the solicitacaoExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitacaoExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacao-exames/{id}")
    public ResponseEntity<SolicitacaoExameDTO> getSolicitacaoExame(@PathVariable Long id) {
        log.debug("REST request to get SolicitacaoExame : {}", id);
        Optional<SolicitacaoExameDTO> solicitacaoExameDTO = solicitacaoExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoExameDTO);
    }

    /**
     * {@code DELETE  /solicitacao-exames/:id} : delete the "id" solicitacaoExame.
     *
     * @param id the id of the solicitacaoExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacao-exames/{id}")
    public ResponseEntity<Void> deleteSolicitacaoExame(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoExame : {}", id);
        solicitacaoExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/solicitacao-exames?query=:query} : search for the solicitacaoExame corresponding
     * to the query.
     *
     * @param query the query of the solicitacaoExame search.
     * @return the result of the search.
     */
    @GetMapping("/_search/solicitacao-exames")
    public List<SolicitacaoExameDTO> searchSolicitacaoExames(@RequestParam String query) {
        log.debug("REST request to search SolicitacaoExames for query {}", query);
        return solicitacaoExameService.search(query);
    }
}
