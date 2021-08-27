package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.AmostraDeMaterialService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.AmostraDeMaterialDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.AmostraDeMaterial}.
 */
@RestController
@RequestMapping("/api")
public class AmostraDeMaterialResource {

    private final Logger log = LoggerFactory.getLogger(AmostraDeMaterialResource.class);

    private static final String ENTITY_NAME = "madreexamesAmostraDeMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmostraDeMaterialService amostraDeMaterialService;

    public AmostraDeMaterialResource(AmostraDeMaterialService amostraDeMaterialService) {
        this.amostraDeMaterialService = amostraDeMaterialService;
    }

    /**
     * {@code POST  /amostra-de-materials} : Create a new amostraDeMaterial.
     *
     * @param amostraDeMaterialDTO the amostraDeMaterialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amostraDeMaterialDTO, or with status {@code 400 (Bad Request)} if the amostraDeMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amostra-de-materials")
    public ResponseEntity<AmostraDeMaterialDTO> createAmostraDeMaterial(@Valid @RequestBody AmostraDeMaterialDTO amostraDeMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save AmostraDeMaterial : {}", amostraDeMaterialDTO);
        if (amostraDeMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new amostraDeMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmostraDeMaterialDTO result = amostraDeMaterialService.save(amostraDeMaterialDTO);
        return ResponseEntity.created(new URI("/api/amostra-de-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amostra-de-materials} : Updates an existing amostraDeMaterial.
     *
     * @param amostraDeMaterialDTO the amostraDeMaterialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amostraDeMaterialDTO,
     * or with status {@code 400 (Bad Request)} if the amostraDeMaterialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amostraDeMaterialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amostra-de-materials")
    public ResponseEntity<AmostraDeMaterialDTO> updateAmostraDeMaterial(@Valid @RequestBody AmostraDeMaterialDTO amostraDeMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update AmostraDeMaterial : {}", amostraDeMaterialDTO);
        if (amostraDeMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmostraDeMaterialDTO result = amostraDeMaterialService.save(amostraDeMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amostraDeMaterialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amostra-de-materials} : get all the amostraDeMaterials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amostraDeMaterials in body.
     */
    @GetMapping("/amostra-de-materials")
    public ResponseEntity<List<AmostraDeMaterialDTO>> getAllAmostraDeMaterials(Pageable pageable) {
        log.debug("REST request to get a page of AmostraDeMaterials");
        Page<AmostraDeMaterialDTO> page = amostraDeMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /amostra-de-materials/:id} : get the "id" amostraDeMaterial.
     *
     * @param id the id of the amostraDeMaterialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amostraDeMaterialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amostra-de-materials/{id}")
    public ResponseEntity<AmostraDeMaterialDTO> getAmostraDeMaterial(@PathVariable Long id) {
        log.debug("REST request to get AmostraDeMaterial : {}", id);
        Optional<AmostraDeMaterialDTO> amostraDeMaterialDTO = amostraDeMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amostraDeMaterialDTO);
    }

    /**
     * {@code DELETE  /amostra-de-materials/:id} : delete the "id" amostraDeMaterial.
     *
     * @param id the id of the amostraDeMaterialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amostra-de-materials/{id}")
    public ResponseEntity<Void> deleteAmostraDeMaterial(@PathVariable Long id) {
        log.debug("REST request to delete AmostraDeMaterial : {}", id);
        amostraDeMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/amostra-de-materials?query=:query} : search for the amostraDeMaterial corresponding
     * to the query.
     *
     * @param query the query of the amostraDeMaterial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amostra-de-materials")
    public ResponseEntity<List<AmostraDeMaterialDTO>> searchAmostraDeMaterials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AmostraDeMaterials for query {}", query);
        Page<AmostraDeMaterialDTO> page = amostraDeMaterialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
