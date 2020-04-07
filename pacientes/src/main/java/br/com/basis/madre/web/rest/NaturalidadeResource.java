package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.NaturalidadeService;
import br.com.basis.madre.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.service.dto.NaturalidadeDTO;

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
 * REST controller for managing {@link br.com.basis.madre.domain.Naturalidade}.
 */
@RestController
@RequestMapping("/api")
public class NaturalidadeResource {

    private final Logger log = LoggerFactory.getLogger(NaturalidadeResource.class);

    private static final String ENTITY_NAME = "pacientesNaturalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaturalidadeService naturalidadeService;

    public NaturalidadeResource(NaturalidadeService naturalidadeService) {
        this.naturalidadeService = naturalidadeService;
    }

    /**
     * {@code POST  /naturalidades} : Create a new naturalidade.
     *
     * @param naturalidadeDTO the naturalidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naturalidadeDTO, or with status {@code 400 (Bad Request)} if the naturalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/naturalidades")
    public ResponseEntity<NaturalidadeDTO> createNaturalidade(@Valid @RequestBody NaturalidadeDTO naturalidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Naturalidade : {}", naturalidadeDTO);
        if (naturalidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new naturalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NaturalidadeDTO result = naturalidadeService.save(naturalidadeDTO);
        return ResponseEntity.created(new URI("/api/naturalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /naturalidades} : Updates an existing naturalidade.
     *
     * @param naturalidadeDTO the naturalidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naturalidadeDTO,
     * or with status {@code 400 (Bad Request)} if the naturalidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naturalidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/naturalidades")
    public ResponseEntity<NaturalidadeDTO> updateNaturalidade(@Valid @RequestBody NaturalidadeDTO naturalidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Naturalidade : {}", naturalidadeDTO);
        if (naturalidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NaturalidadeDTO result = naturalidadeService.save(naturalidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, naturalidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /naturalidades} : get all the naturalidades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naturalidades in body.
     */
    @GetMapping("/naturalidades")
    public ResponseEntity<List<NaturalidadeDTO>> getAllNaturalidades(Pageable pageable) {
        log.debug("REST request to get a page of Naturalidades");
        Page<NaturalidadeDTO> page = naturalidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /naturalidades/:id} : get the "id" naturalidade.
     *
     * @param id the id of the naturalidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naturalidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/naturalidades/{id}")
    public ResponseEntity<NaturalidadeDTO> getNaturalidade(@PathVariable Long id) {
        log.debug("REST request to get Naturalidade : {}", id);
        Optional<NaturalidadeDTO> naturalidadeDTO = naturalidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(naturalidadeDTO);
    }

    /**
     * {@code DELETE  /naturalidades/:id} : delete the "id" naturalidade.
     *
     * @param id the id of the naturalidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/naturalidades/{id}")
    public ResponseEntity<Void> deleteNaturalidade(@PathVariable Long id) {
        log.debug("REST request to delete Naturalidade : {}", id);
        naturalidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/naturalidades?query=:query} : search for the naturalidade corresponding
     * to the query.
     *
     * @param query the query of the naturalidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/naturalidades")
    public ResponseEntity<List<NaturalidadeDTO>> searchNaturalidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Naturalidades for query {}", query);
        Page<NaturalidadeDTO> page = naturalidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
