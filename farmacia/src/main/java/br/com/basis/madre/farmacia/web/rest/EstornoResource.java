package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.service.EstornoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.EstornoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.Estorno}.
 */
@RestController
@RequestMapping("/api")
public class EstornoResource {

    private final Logger log = LoggerFactory.getLogger(EstornoResource.class);

    private static final String ENTITY_NAME = "farmaciaEstorno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstornoService estornoService;

    public EstornoResource(EstornoService estornoService) {
        this.estornoService = estornoService;
    }

    /**
     * {@code POST  /estornos} : Create a new estorno.
     *
     * @param estornoDTO the estornoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estornoDTO, or with status {@code 400 (Bad Request)} if the estorno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estornos")
    public ResponseEntity<EstornoDTO> createEstorno(@RequestBody EstornoDTO estornoDTO) throws URISyntaxException {
        log.debug("REST request to save Estorno : {}", estornoDTO);
        if (estornoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estorno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstornoDTO result = estornoService.save(estornoDTO);
        return ResponseEntity.created(new URI("/api/estornos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estornos} : Updates an existing estorno.
     *
     * @param estornoDTO the estornoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estornoDTO,
     * or with status {@code 400 (Bad Request)} if the estornoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estornoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estornos")
    public ResponseEntity<EstornoDTO> updateEstorno(@RequestBody EstornoDTO estornoDTO) throws URISyntaxException {
        log.debug("REST request to update Estorno : {}", estornoDTO);
        if (estornoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstornoDTO result = estornoService.save(estornoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estornoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estornos} : get all the estornos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estornos in body.
     */
    @GetMapping("/estornos")
    public ResponseEntity<List<EstornoDTO>> getAllEstornos(Pageable pageable) {
        log.debug("REST request to get a page of Estornos");
        Page<EstornoDTO> page = estornoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estornos/:id} : get the "id" estorno.
     *
     * @param id the id of the estornoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estornoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estornos/{id}")
    public ResponseEntity<EstornoDTO> getEstorno(@PathVariable Long id) {
        log.debug("REST request to get Estorno : {}", id);
        Optional<EstornoDTO> estornoDTO = estornoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estornoDTO);
    }

    /**
     * {@code DELETE  /estornos/:id} : delete the "id" estorno.
     *
     * @param id the id of the estornoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estornos/{id}")
    public ResponseEntity<Void> deleteEstorno(@PathVariable Long id) {
        log.debug("REST request to delete Estorno : {}", id);
        estornoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/estornos?query=:query} : search for the estorno corresponding
     * to the query.
     *
     * @param query the query of the estorno search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/estornos")
    public ResponseEntity<List<EstornoDTO>> searchEstornos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Estornos for query {}", query);
        Page<EstornoDTO> page = estornoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
