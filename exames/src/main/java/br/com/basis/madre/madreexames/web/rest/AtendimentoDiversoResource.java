package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.AtendimentoDiversoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.AtendimentoDiversoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.AtendimentoDiverso}.
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
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atendimentoDiversos in body.
     */
    @GetMapping("/atendimento-diversos")
    public ResponseEntity<List<AtendimentoDiversoDTO>> getAllAtendimentoDiversos(Pageable pageable) {
        log.debug("REST request to get a page of AtendimentoDiversos");
        Page<AtendimentoDiversoDTO> page = atendimentoDiversoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/atendimento-diversos")
    public ResponseEntity<List<AtendimentoDiversoDTO>> searchAtendimentoDiversos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AtendimentoDiversos for query {}", query);
        Page<AtendimentoDiversoDTO> page = atendimentoDiversoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
