package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.MarcaComercialService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.MarcaComercialDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.MarcaComercial}.
 */
@RestController
@RequestMapping("/api")
public class MarcaComercialResource {

    private final Logger log = LoggerFactory.getLogger(MarcaComercialResource.class);

    private static final String ENTITY_NAME = "madresuprimentosMarcaComercial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarcaComercialService marcaComercialService;

    public MarcaComercialResource(MarcaComercialService marcaComercialService) {
        this.marcaComercialService = marcaComercialService;
    }

    /**
     * {@code POST  /marca-comercials} : Create a new marcaComercial.
     *
     * @param marcaComercialDTO the marcaComercialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marcaComercialDTO, or with status {@code 400 (Bad Request)} if the marcaComercial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/marca-comercials")
    public ResponseEntity<MarcaComercialDTO> createMarcaComercial(@Valid @RequestBody MarcaComercialDTO marcaComercialDTO) throws URISyntaxException {
        log.debug("REST request to save MarcaComercial : {}", marcaComercialDTO);
        if (marcaComercialDTO.getId() != null) {
            throw new BadRequestAlertException("A new marcaComercial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarcaComercialDTO result = marcaComercialService.save(marcaComercialDTO);
        return ResponseEntity.created(new URI("/api/marca-comercials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /marca-comercials} : Updates an existing marcaComercial.
     *
     * @param marcaComercialDTO the marcaComercialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marcaComercialDTO,
     * or with status {@code 400 (Bad Request)} if the marcaComercialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marcaComercialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/marca-comercials")
    public ResponseEntity<MarcaComercialDTO> updateMarcaComercial(@Valid @RequestBody MarcaComercialDTO marcaComercialDTO) throws URISyntaxException {
        log.debug("REST request to update MarcaComercial : {}", marcaComercialDTO);
        if (marcaComercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MarcaComercialDTO result = marcaComercialService.save(marcaComercialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marcaComercialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /marca-comercials} : get all the marcaComercials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marcaComercials in body.
     */
    @GetMapping("/marca-comercials")
    public ResponseEntity<List<MarcaComercialDTO>> getAllMarcaComercials(Pageable pageable) {
        log.debug("REST request to get a page of MarcaComercials");
        Page<MarcaComercialDTO> page = marcaComercialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /marca-comercials/:id} : get the "id" marcaComercial.
     *
     * @param id the id of the marcaComercialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marcaComercialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marca-comercials/{id}")
    public ResponseEntity<MarcaComercialDTO> getMarcaComercial(@PathVariable Long id) {
        log.debug("REST request to get MarcaComercial : {}", id);
        Optional<MarcaComercialDTO> marcaComercialDTO = marcaComercialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marcaComercialDTO);
    }

    /**
     * {@code DELETE  /marca-comercials/:id} : delete the "id" marcaComercial.
     *
     * @param id the id of the marcaComercialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marca-comercials/{id}")
    public ResponseEntity<Void> deleteMarcaComercial(@PathVariable Long id) {
        log.debug("REST request to delete MarcaComercial : {}", id);
        marcaComercialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/marca-comercials?query=:query} : search for the marcaComercial corresponding
     * to the query.
     *
     * @param query the query of the marcaComercial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/marca-comercials")
    public ResponseEntity<List<MarcaComercialDTO>> searchMarcaComercials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MarcaComercials for query {}", query);
        Page<MarcaComercialDTO> page = marcaComercialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
