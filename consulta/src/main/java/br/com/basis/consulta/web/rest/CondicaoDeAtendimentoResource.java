package br.com.basis.consulta.web.rest;

import br.com.basis.consulta.service.CondicaoDeAtendimentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.consulta.service.dto.CondicaoDeAtendimentoDTO;

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
 * REST controller for managing {@link br.com.basis.consulta.domain.CondicaoDeAtendimento}.
 */
@RestController
@RequestMapping("/api")
public class CondicaoDeAtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(CondicaoDeAtendimentoResource.class);

    private static final String ENTITY_NAME = "madreconsultaCondicaoDeAtendimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CondicaoDeAtendimentoService condicaoDeAtendimentoService;

    public CondicaoDeAtendimentoResource(CondicaoDeAtendimentoService condicaoDeAtendimentoService) {
        this.condicaoDeAtendimentoService = condicaoDeAtendimentoService;
    }

    /**
     * {@code POST  /condicao-de-atendimentos} : Create a new condicaoDeAtendimento.
     *
     * @param condicaoDeAtendimentoDTO the condicaoDeAtendimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new condicaoDeAtendimentoDTO, or with status {@code 400 (Bad Request)} if the condicaoDeAtendimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/condicao-de-atendimentos")
    public ResponseEntity<CondicaoDeAtendimentoDTO> createCondicaoDeAtendimento(@Valid @RequestBody CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO) throws URISyntaxException {
        log.debug("REST request to save CondicaoDeAtendimento : {}", condicaoDeAtendimentoDTO);
        if (condicaoDeAtendimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new condicaoDeAtendimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CondicaoDeAtendimentoDTO result = condicaoDeAtendimentoService.save(condicaoDeAtendimentoDTO);
        return ResponseEntity.created(new URI("/api/condicao-de-atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /condicao-de-atendimentos} : Updates an existing condicaoDeAtendimento.
     *
     * @param condicaoDeAtendimentoDTO the condicaoDeAtendimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated condicaoDeAtendimentoDTO,
     * or with status {@code 400 (Bad Request)} if the condicaoDeAtendimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the condicaoDeAtendimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/condicao-de-atendimentos")
    public ResponseEntity<CondicaoDeAtendimentoDTO> updateCondicaoDeAtendimento(@Valid @RequestBody CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO) throws URISyntaxException {
        log.debug("REST request to update CondicaoDeAtendimento : {}", condicaoDeAtendimentoDTO);
        if (condicaoDeAtendimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CondicaoDeAtendimentoDTO result = condicaoDeAtendimentoService.save(condicaoDeAtendimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, condicaoDeAtendimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /condicao-de-atendimentos} : get all the condicaoDeAtendimentos.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of condicaoDeAtendimentos in body.
     */
    @GetMapping("/condicao-de-atendimentos")
    public ResponseEntity<List<CondicaoDeAtendimentoDTO>> getAllCondicaoDeAtendimentos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("emergencia-is-null".equals(filter)) {
            log.debug("REST request to get all CondicaoDeAtendimentos where emergencia is null");
            return new ResponseEntity<>(condicaoDeAtendimentoService.findAllWhereEmergenciaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of CondicaoDeAtendimentos");
        Page<CondicaoDeAtendimentoDTO> page = condicaoDeAtendimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /condicao-de-atendimentos/:id} : get the "id" condicaoDeAtendimento.
     *
     * @param id the id of the condicaoDeAtendimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the condicaoDeAtendimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/condicao-de-atendimentos/{id}")
    public ResponseEntity<CondicaoDeAtendimentoDTO> getCondicaoDeAtendimento(@PathVariable Long id) {
        log.debug("REST request to get CondicaoDeAtendimento : {}", id);
        Optional<CondicaoDeAtendimentoDTO> condicaoDeAtendimentoDTO = condicaoDeAtendimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(condicaoDeAtendimentoDTO);
    }

    /**
     * {@code DELETE  /condicao-de-atendimentos/:id} : delete the "id" condicaoDeAtendimento.
     *
     * @param id the id of the condicaoDeAtendimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/condicao-de-atendimentos/{id}")
    public ResponseEntity<Void> deleteCondicaoDeAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete CondicaoDeAtendimento : {}", id);
        condicaoDeAtendimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/condicao-de-atendimentos?query=:query} : search for the condicaoDeAtendimento corresponding
     * to the query.
     *
     * @param query the query of the condicaoDeAtendimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/condicao-de-atendimentos")
    public ResponseEntity<List<CondicaoDeAtendimentoDTO>> searchCondicaoDeAtendimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CondicaoDeAtendimentos for query {}", query);
        Page<CondicaoDeAtendimentoDTO> page = condicaoDeAtendimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
