package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.InformacoesComplementaresService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.InformacoesComplementaresDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.InformacoesComplementares}.
 */
@RestController
@RequestMapping("/api")
public class InformacoesComplementaresResource {

    private final Logger log = LoggerFactory.getLogger(InformacoesComplementaresResource.class);

    private static final String ENTITY_NAME = "madreexamesInformacoesComplementares";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformacoesComplementaresService informacoesComplementaresService;

    public InformacoesComplementaresResource(InformacoesComplementaresService informacoesComplementaresService) {
        this.informacoesComplementaresService = informacoesComplementaresService;
    }

    /**
     * {@code POST  /informacoes-complementares} : Create a new informacoesComplementares.
     *
     * @param informacoesComplementaresDTO the informacoesComplementaresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informacoesComplementaresDTO, or with status {@code 400 (Bad Request)} if the informacoesComplementares has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/informacoes-complementares")
    public ResponseEntity<InformacoesComplementaresDTO> createInformacoesComplementares(@Valid @RequestBody InformacoesComplementaresDTO informacoesComplementaresDTO) throws URISyntaxException {
        log.debug("REST request to save InformacoesComplementares : {}", informacoesComplementaresDTO);
        if (informacoesComplementaresDTO.getId() != null) {
            throw new BadRequestAlertException("A new informacoesComplementares cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformacoesComplementaresDTO result = informacoesComplementaresService.save(informacoesComplementaresDTO);
        return ResponseEntity.created(new URI("/api/informacoes-complementares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /informacoes-complementares} : Updates an existing informacoesComplementares.
     *
     * @param informacoesComplementaresDTO the informacoesComplementaresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informacoesComplementaresDTO,
     * or with status {@code 400 (Bad Request)} if the informacoesComplementaresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informacoesComplementaresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/informacoes-complementares")
    public ResponseEntity<InformacoesComplementaresDTO> updateInformacoesComplementares(@Valid @RequestBody InformacoesComplementaresDTO informacoesComplementaresDTO) throws URISyntaxException {
        log.debug("REST request to update InformacoesComplementares : {}", informacoesComplementaresDTO);
        if (informacoesComplementaresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InformacoesComplementaresDTO result = informacoesComplementaresService.save(informacoesComplementaresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, informacoesComplementaresDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /informacoes-complementares} : get all the informacoesComplementares.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informacoesComplementares in body.
     */
    @GetMapping("/informacoes-complementares")
    public ResponseEntity<List<InformacoesComplementaresDTO>> getAllInformacoesComplementares(Pageable pageable) {
        log.debug("REST request to get a page of InformacoesComplementares");
        Page<InformacoesComplementaresDTO> page = informacoesComplementaresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /informacoes-complementares/:id} : get the "id" informacoesComplementares.
     *
     * @param id the id of the informacoesComplementaresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informacoesComplementaresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/informacoes-complementares/{id}")
    public ResponseEntity<InformacoesComplementaresDTO> getInformacoesComplementares(@PathVariable Long id) {
        log.debug("REST request to get InformacoesComplementares : {}", id);
        Optional<InformacoesComplementaresDTO> informacoesComplementaresDTO = informacoesComplementaresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informacoesComplementaresDTO);
    }

    /**
     * {@code DELETE  /informacoes-complementares/:id} : delete the "id" informacoesComplementares.
     *
     * @param id the id of the informacoesComplementaresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/informacoes-complementares/{id}")
    public ResponseEntity<Void> deleteInformacoesComplementares(@PathVariable Long id) {
        log.debug("REST request to delete InformacoesComplementares : {}", id);
        informacoesComplementaresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/informacoes-complementares?query=:query} : search for the informacoesComplementares corresponding
     * to the query.
     *
     * @param query the query of the informacoesComplementares search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/informacoes-complementares")
    public ResponseEntity<List<InformacoesComplementaresDTO>> searchInformacoesComplementares(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InformacoesComplementares for query {}", query);
        Page<InformacoesComplementaresDTO> page = informacoesComplementaresService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
