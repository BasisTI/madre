package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.MaterialDeExameService;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameCompletoDTO;
import br.com.basis.madre.madreexames.service.dto.MaterialDeExameDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.MaterialDeExame}.
 */
@RestController
@RequestMapping("/api")
public class MaterialDeExameResource {

    private final Logger log = LoggerFactory.getLogger(MaterialDeExameResource.class);

    private static final String ENTITY_NAME = "madreexamesMaterialDeExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialDeExameService materialDeExameService;

    public MaterialDeExameResource(MaterialDeExameService materialDeExameService) {
        this.materialDeExameService = materialDeExameService;
    }

    /**
     * {@code POST  /material-de-exames} : Create a new materialDeExame.
     *
     * @param materialDeExameCompletoDTO the materialDeExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialDeExameDTO, or with status {@code 400 (Bad Request)} if the materialDeExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-de-exames")
    public ResponseEntity<MaterialDeExameCompletoDTO> createMaterialDeExame(@Valid @RequestBody MaterialDeExameCompletoDTO materialDeExameCompletoDTO) throws URISyntaxException {
        log.debug("REST request to save MaterialDeExame : {}", materialDeExameCompletoDTO);
        if (materialDeExameCompletoDTO.getId() != null) {
            throw new BadRequestAlertException("A new materialDeExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialDeExameCompletoDTO result = materialDeExameService.save(materialDeExameCompletoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-de-exames} : Updates an existing materialDeExame.
     *
     * @param materialDeExameCompletoDTO the materialDeExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialDeExameDTO,
     * or with status {@code 400 (Bad Request)} if the materialDeExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialDeExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-de-exames")
    public ResponseEntity<MaterialDeExameCompletoDTO> updateMaterialDeExame(@Valid @RequestBody MaterialDeExameCompletoDTO materialDeExameCompletoDTO) throws URISyntaxException {
        log.debug("REST request to update MaterialDeExame : {}", materialDeExameCompletoDTO);
        if (materialDeExameCompletoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialDeExameCompletoDTO result = materialDeExameService.save(materialDeExameCompletoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materialDeExameCompletoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /material-de-exames} : get all the materialDeExames.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialDeExames in body.
     */
    @GetMapping("/material-de-exames")
    public ResponseEntity<List<MaterialDeExameDTO>> getAllMaterialDeExames(Pageable pageable) {
        log.debug("REST request to get a page of MaterialDeExames");
        Page<MaterialDeExameDTO> page = materialDeExameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /material-de-exames/:id} : get the "id" materialDeExame.
     *
     * @param id the id of the materialDeExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialDeExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-de-exames/{id}")
    public ResponseEntity<MaterialDeExameDTO> getMaterialDeExame(@PathVariable Long id) {
        log.debug("REST request to get MaterialDeExame : {}", id);
        Optional<MaterialDeExameDTO> materialDeExameDTO = materialDeExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialDeExameDTO);
    }

    /**
     * {@code DELETE  /material-de-exames/:id} : delete the "id" materialDeExame.
     *
     * @param id the id of the materialDeExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-de-exames/{id}")
    public ResponseEntity<Void> deleteMaterialDeExame(@PathVariable Long id) {
        log.debug("REST request to delete MaterialDeExame : {}", id);
        materialDeExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/material-de-exames?query=:query} : search for the materialDeExame corresponding
     * to the query.
     *
     * @param query the query of the materialDeExame search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/material-de-exames")
    public ResponseEntity<List<MaterialDeExameDTO>> searchMaterialDeExames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MaterialDeExames for query {}", query);
        Page<MaterialDeExameDTO> page = materialDeExameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
