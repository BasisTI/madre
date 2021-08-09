package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.service.OcupacaoDeCargoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.seguranca.service.dto.OcupacaoDeCargoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.OcupacaoDeCargo}.
 */
@RestController
@RequestMapping("/api")
public class OcupacaoDeCargoResource {

    private final Logger log = LoggerFactory.getLogger(OcupacaoDeCargoResource.class);

    private static final String ENTITY_NAME = "madresegurancaOcupacaoDeCargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OcupacaoDeCargoService ocupacaoDeCargoService;

    public OcupacaoDeCargoResource(OcupacaoDeCargoService ocupacaoDeCargoService) {
        this.ocupacaoDeCargoService = ocupacaoDeCargoService;
    }

    /**
     * {@code POST  /ocupacao-de-cargos} : Create a new ocupacaoDeCargo.
     *
     * @param ocupacaoDeCargoDTO the ocupacaoDeCargoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ocupacaoDeCargoDTO, or with status {@code 400 (Bad Request)} if the ocupacaoDeCargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ocupacao-de-cargos")
    public ResponseEntity<OcupacaoDeCargoDTO> createOcupacaoDeCargo(@Valid @RequestBody OcupacaoDeCargoDTO ocupacaoDeCargoDTO) throws URISyntaxException {
        log.debug("REST request to save OcupacaoDeCargo : {}", ocupacaoDeCargoDTO);
        if (ocupacaoDeCargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ocupacaoDeCargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OcupacaoDeCargoDTO result = ocupacaoDeCargoService.save(ocupacaoDeCargoDTO);
        return ResponseEntity.created(new URI("/api/ocupacao-de-cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ocupacao-de-cargos} : Updates an existing ocupacaoDeCargo.
     *
     * @param ocupacaoDeCargoDTO the ocupacaoDeCargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocupacaoDeCargoDTO,
     * or with status {@code 400 (Bad Request)} if the ocupacaoDeCargoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ocupacaoDeCargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ocupacao-de-cargos")
    public ResponseEntity<OcupacaoDeCargoDTO> updateOcupacaoDeCargo(@Valid @RequestBody OcupacaoDeCargoDTO ocupacaoDeCargoDTO) throws URISyntaxException {
        log.debug("REST request to update OcupacaoDeCargo : {}", ocupacaoDeCargoDTO);
        if (ocupacaoDeCargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OcupacaoDeCargoDTO result = ocupacaoDeCargoService.save(ocupacaoDeCargoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ocupacaoDeCargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ocupacao-de-cargos} : get all the ocupacaoDeCargos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ocupacaoDeCargos in body.
     */
    @GetMapping("/ocupacao-de-cargos")
    public ResponseEntity<List<OcupacaoDeCargoDTO>> getAllOcupacaoDeCargos(Pageable pageable) {
        log.debug("REST request to get a page of OcupacaoDeCargos");
        Page<OcupacaoDeCargoDTO> page = ocupacaoDeCargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ocupacao-de-cargos/:id} : get the "id" ocupacaoDeCargo.
     *
     * @param id the id of the ocupacaoDeCargoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ocupacaoDeCargoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ocupacao-de-cargos/{id}")
    public ResponseEntity<OcupacaoDeCargoDTO> getOcupacaoDeCargo(@PathVariable Long id) {
        log.debug("REST request to get OcupacaoDeCargo : {}", id);
        Optional<OcupacaoDeCargoDTO> ocupacaoDeCargoDTO = ocupacaoDeCargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ocupacaoDeCargoDTO);
    }

    /**
     * {@code DELETE  /ocupacao-de-cargos/:id} : delete the "id" ocupacaoDeCargo.
     *
     * @param id the id of the ocupacaoDeCargoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ocupacao-de-cargos/{id}")
    public ResponseEntity<Void> deleteOcupacaoDeCargo(@PathVariable Long id) {
        log.debug("REST request to delete OcupacaoDeCargo : {}", id);
        ocupacaoDeCargoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ocupacao-de-cargos?query=:query} : search for the ocupacaoDeCargo corresponding
     * to the query.
     *
     * @param query the query of the ocupacaoDeCargo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ocupacao-de-cargos")
    public ResponseEntity<List<OcupacaoDeCargoDTO>> searchOcupacaoDeCargos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OcupacaoDeCargos for query {}", query);
        Page<OcupacaoDeCargoDTO> page = ocupacaoDeCargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
