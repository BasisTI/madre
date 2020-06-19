package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.RecebimentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.RecebimentoDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.suprimentos.domain.Recebimento}.
 */
@RestController
@RequestMapping("/api")
public class RecebimentoResource {

    private final Logger log = LoggerFactory.getLogger(RecebimentoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosRecebimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecebimentoService recebimentoService;

    public RecebimentoResource(RecebimentoService recebimentoService) {
        this.recebimentoService = recebimentoService;
    }

    /**
     * {@code POST  /recebimentos} : Create a new recebimento.
     *
     * @param recebimentoDTO the recebimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recebimentoDTO, or with status {@code 400 (Bad Request)} if the recebimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recebimentos")
    public ResponseEntity<RecebimentoDTO> createRecebimento(@RequestBody RecebimentoDTO recebimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Recebimento : {}", recebimentoDTO);
        if (recebimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recebimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecebimentoDTO result = recebimentoService.save(recebimentoDTO);
        return ResponseEntity.created(new URI("/api/recebimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recebimentos} : Updates an existing recebimento.
     *
     * @param recebimentoDTO the recebimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recebimentoDTO,
     * or with status {@code 400 (Bad Request)} if the recebimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recebimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recebimentos")
    public ResponseEntity<RecebimentoDTO> updateRecebimento(@RequestBody RecebimentoDTO recebimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Recebimento : {}", recebimentoDTO);
        if (recebimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecebimentoDTO result = recebimentoService.save(recebimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recebimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recebimentos} : get all the recebimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recebimentos in body.
     */
    @GetMapping("/recebimentos")
    public ResponseEntity<List<RecebimentoDTO>> getAllRecebimentos(Pageable pageable) {
        log.debug("REST request to get a page of Recebimentos");
        Page<RecebimentoDTO> page = recebimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recebimentos/:id} : get the "id" recebimento.
     *
     * @param id the id of the recebimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recebimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recebimentos/{id}")
    public ResponseEntity<RecebimentoDTO> getRecebimento(@PathVariable Long id) {
        log.debug("REST request to get Recebimento : {}", id);
        Optional<RecebimentoDTO> recebimentoDTO = recebimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recebimentoDTO);
    }

    /**
     * {@code DELETE  /recebimentos/:id} : delete the "id" recebimento.
     *
     * @param id the id of the recebimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recebimentos/{id}")
    public ResponseEntity<Void> deleteRecebimento(@PathVariable Long id) {
        log.debug("REST request to delete Recebimento : {}", id);
        recebimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/recebimentos?query=:query} : search for the recebimento corresponding
     * to the query.
     *
     * @param query the query of the recebimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/recebimentos")
    public ResponseEntity<List<RecebimentoDTO>> searchRecebimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recebimentos for query {}", query);
        Page<RecebimentoDTO> page = recebimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
