package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.AtendimentoDiversoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.AtendimentoDiversoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.exames.domain.AtendimentoDiverso}.
 */
@RestController
@RequestMapping("/api")
public class AtendimentoDiversoResource {

    private final Logger log = LoggerFactory.getLogger(AtendimentoDiversoResource.class);

    private static final String ENTITY_NAME = "madreexamesAtendimentoDiverso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtendimentoDiversoService atendimentoDiversoService;

    public AtendimentoDiversoResource(AtendimentoDiversoService atendimentoDiversoService) {
        this.atendimentoDiversoService = atendimentoDiversoService;
    }

    /**
     * {@code POST  /atendimento-diversos} : Create a new atendimentoDiverso.
     *
     * @param atendimentoDiversoDTO the atendimentoDiversoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atendimentoDiversoDTO, or with status {@code 400 (Bad Request)} if the atendimentoDiverso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/atendimento-diversos")
    public ResponseEntity<AtendimentoDiversoDTO> createAtendimentoDiverso(@Valid @RequestBody AtendimentoDiversoDTO atendimentoDiversoDTO) throws URISyntaxException {
        log.debug("REST request to save AtendimentoDiverso : {}", atendimentoDiversoDTO);
        if (atendimentoDiversoDTO.getId() != null) {
            throw new BadRequestAlertException("A new atendimentoDiverso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtendimentoDiversoDTO result = atendimentoDiversoService.save(atendimentoDiversoDTO);
        return ResponseEntity.created(new URI("/api/atendimento-diversos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /atendimento-diversos} : Updates an existing atendimentoDiverso.
     *
     * @param atendimentoDiversoDTO the atendimentoDiversoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atendimentoDiversoDTO,
     * or with status {@code 400 (Bad Request)} if the atendimentoDiversoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atendimentoDiversoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/atendimento-diversos")
    public ResponseEntity<AtendimentoDiversoDTO> updateAtendimentoDiverso(@Valid @RequestBody AtendimentoDiversoDTO atendimentoDiversoDTO) throws URISyntaxException {
        log.debug("REST request to update AtendimentoDiverso : {}", atendimentoDiversoDTO);
        if (atendimentoDiversoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AtendimentoDiversoDTO result = atendimentoDiversoService.save(atendimentoDiversoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, atendimentoDiversoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /atendimento-diversos} : get all the atendimentoDiversos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atendimentoDiversos in body.
     */
    @GetMapping("/atendimento-diversos")
    public List<AtendimentoDiversoDTO> getAllAtendimentoDiversos() {
        log.debug("REST request to get all AtendimentoDiversos");
        return atendimentoDiversoService.findAll();
    }

    /**
     * {@code GET  /atendimento-diversos/:id} : get the "id" atendimentoDiverso.
     *
     * @param id the id of the atendimentoDiversoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atendimentoDiversoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/atendimento-diversos/{id}")
    public ResponseEntity<AtendimentoDiversoDTO> getAtendimentoDiverso(@PathVariable Long id) {
        log.debug("REST request to get AtendimentoDiverso : {}", id);
        Optional<AtendimentoDiversoDTO> atendimentoDiversoDTO = atendimentoDiversoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atendimentoDiversoDTO);
    }

    /**
     * {@code DELETE  /atendimento-diversos/:id} : delete the "id" atendimentoDiverso.
     *
     * @param id the id of the atendimentoDiversoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/atendimento-diversos/{id}")
    public ResponseEntity<Void> deleteAtendimentoDiverso(@PathVariable Long id) {
        log.debug("REST request to delete AtendimentoDiverso : {}", id);
        atendimentoDiversoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/atendimento-diversos?query=:query} : search for the atendimentoDiverso corresponding
     * to the query.
     *
     * @param query the query of the atendimentoDiverso search.
     * @return the result of the search.
     */
    @GetMapping("/_search/atendimento-diversos")
    public List<AtendimentoDiversoDTO> searchAtendimentoDiversos(@RequestParam String query) {
        log.debug("REST request to search AtendimentoDiversos for query {}", query);
        return atendimentoDiversoService.search(query);
    }
}
