package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.GrupoMaterialService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.GrupoMaterialDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.GrupoMaterial}.
 */
@RestController
@RequestMapping("/api")
public class GrupoMaterialResource {

    private final Logger log = LoggerFactory.getLogger(GrupoMaterialResource.class);

    private static final String ENTITY_NAME = "madresuprimentosGrupoMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoMaterialService grupoMaterialService;

    public GrupoMaterialResource(GrupoMaterialService grupoMaterialService) {
        this.grupoMaterialService = grupoMaterialService;
    }

    /**
     * {@code POST  /grupo-materials} : Create a new grupoMaterial.
     *
     * @param grupoMaterialDTO the grupoMaterialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoMaterialDTO, or with status {@code 400 (Bad Request)} if the grupoMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-materials")
    public ResponseEntity<GrupoMaterialDTO> createGrupoMaterial(@Valid @RequestBody GrupoMaterialDTO grupoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoMaterial : {}", grupoMaterialDTO);
        if (grupoMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoMaterialDTO result = grupoMaterialService.save(grupoMaterialDTO);
        return ResponseEntity.created(new URI("/api/grupo-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-materials} : Updates an existing grupoMaterial.
     *
     * @param grupoMaterialDTO the grupoMaterialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoMaterialDTO,
     * or with status {@code 400 (Bad Request)} if the grupoMaterialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoMaterialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-materials")
    public ResponseEntity<GrupoMaterialDTO> updateGrupoMaterial(@Valid @RequestBody GrupoMaterialDTO grupoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update GrupoMaterial : {}", grupoMaterialDTO);
        if (grupoMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoMaterialDTO result = grupoMaterialService.save(grupoMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoMaterialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupo-materials} : get all the grupoMaterials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoMaterials in body.
     */
    @GetMapping("/grupo-materials")
    public ResponseEntity<List<GrupoMaterialDTO>> getAllGrupoMaterials(Pageable pageable) {
        log.debug("REST request to get a page of GrupoMaterials");
        Page<GrupoMaterialDTO> page = grupoMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupo-materials/:id} : get the "id" grupoMaterial.
     *
     * @param id the id of the grupoMaterialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoMaterialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-materials/{id}")
    public ResponseEntity<GrupoMaterialDTO> getGrupoMaterial(@PathVariable Long id) {
        log.debug("REST request to get GrupoMaterial : {}", id);
        Optional<GrupoMaterialDTO> grupoMaterialDTO = grupoMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoMaterialDTO);
    }

    /**
     * {@code DELETE  /grupo-materials/:id} : delete the "id" grupoMaterial.
     *
     * @param id the id of the grupoMaterialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-materials/{id}")
    public ResponseEntity<Void> deleteGrupoMaterial(@PathVariable Long id) {
        log.debug("REST request to delete GrupoMaterial : {}", id);
        grupoMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/grupo-materials?query=:query} : search for the grupoMaterial corresponding
     * to the query.
     *
     * @param query the query of the grupoMaterial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/grupo-materials")
    public ResponseEntity<List<GrupoMaterialDTO>> searchGrupoMaterials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrupoMaterials for query {}", query);
        Page<GrupoMaterialDTO> page = grupoMaterialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
