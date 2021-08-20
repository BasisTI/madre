package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.service.VinculoService;
import br.com.basis.madre.seguranca.service.projection.PessoaResumo;
import br.com.basis.madre.seguranca.service.projection.VinculoResumo;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.seguranca.service.dto.VinculoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Vinculo}.
 */
@RestController
@RequestMapping("/api")
public class VinculoResource {

    private final Logger log = LoggerFactory.getLogger(VinculoResource.class);

    private static final String ENTITY_NAME = "madresegurancaVinculo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VinculoService vinculoService;

    public VinculoResource(VinculoService vinculoService) {
        this.vinculoService = vinculoService;
    }

    /**
     * {@code POST  /vinculos} : Create a new vinculo.
     *
     * @param vinculoDTO the vinculoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vinculoDTO, or with status {@code 400 (Bad Request)} if the vinculo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vinculos")
    public ResponseEntity<VinculoDTO> createVinculo(@Valid @RequestBody VinculoDTO vinculoDTO) throws URISyntaxException {
        log.debug("REST request to save Vinculo : {}", vinculoDTO);
        if (vinculoDTO.getId() != null) {
            throw new BadRequestAlertException("A new vinculo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VinculoDTO result = vinculoService.save(vinculoDTO);
        return ResponseEntity.created(new URI("/api/vinculos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vinculos} : Updates an existing vinculo.
     *
     * @param vinculoDTO the vinculoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vinculoDTO,
     * or with status {@code 400 (Bad Request)} if the vinculoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vinculoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vinculos")
    public ResponseEntity<VinculoDTO> updateVinculo(@Valid @RequestBody VinculoDTO vinculoDTO) throws URISyntaxException {
        log.debug("REST request to update Vinculo : {}", vinculoDTO);
        if (vinculoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VinculoDTO result = vinculoService.save(vinculoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vinculoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vinculos} : get all the vinculos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vinculos in body.
     */
    @GetMapping("/vinculos")
    public ResponseEntity<List<VinculoDTO>> getAllVinculos(Pageable pageable) {
        log.debug("REST request to get a page of Vinculos");
        Page<VinculoDTO> page = vinculoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vinculos/:id} : get the "id" vinculo.
     *
     * @param id the id of the vinculoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vinculoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vinculos/{id}")
    public ResponseEntity<VinculoDTO> getVinculo(@PathVariable Long id) {
        log.debug("REST request to get Vinculo : {}", id);
        Optional<VinculoDTO> vinculoDTO = vinculoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vinculoDTO);
    }

    /**
     * {@code DELETE  /vinculos/:id} : delete the "id" vinculo.
     *
     * @param id the id of the vinculoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vinculos/{id}")
    public ResponseEntity<Void> deleteVinculo(@PathVariable Long id) {
        log.debug("REST request to delete Vinculo : {}", id);
        vinculoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/vinculos?query=:query} : search for the vinculo corresponding
     * to the query.
     *
     * @param query the query of the vinculo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/vinculos")
    public ResponseEntity<List<VinculoDTO>> searchVinculos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Vinculos for query {}", query);
        Page<VinculoDTO> page = vinculoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vinculos/_resumo")
    public ResponseEntity<Page<VinculoResumo>> findAllProjectedVinculoResumoBy(@RequestParam(required = false,defaultValue = "") String descricao, Pageable pageable) {
        return ResponseEntity.ok(vinculoService.findAllProjectedVinculoResumoBy(descricao,pageable));
    }
}
