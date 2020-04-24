package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.EspecialidadeService;
import br.com.basis.madre.service.dto.EspecialidadeDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

/**
 * REST controller for managing Especialidade.
 */
@RestController
@RequestMapping("/api")
public class EspecialidadeResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeResource.class);

    private static final String ENTITY_NAME = "pacientesEspecialidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecialidadeService especialidadeService;

    public EspecialidadeResource(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    /**
     * POST  /especialidades : Create a new especialidade.
     *
     * @param especialidadeDTO the especialidadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especialidadeDTO, or with status 400 (Bad Request) if the especialidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especialidades")
    @Timed
    public ResponseEntity<EspecialidadeDTO> createEspecialidade(@Valid @RequestBody EspecialidadeDTO especialidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Especialidade : {}", especialidadeDTO);
        if (especialidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new especialidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspecialidadeDTO result = especialidadeService.save(especialidadeDTO);
        return ResponseEntity.created(new URI("/api/especialidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especialidades : Updates an existing especialidade.
     *
     * @param especialidadeDTO the especialidadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especialidadeDTO,
     * or with status 400 (Bad Request) if the especialidadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the especialidadeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especialidades")
    @Timed
    public ResponseEntity<EspecialidadeDTO> updateEspecialidade(@Valid @RequestBody EspecialidadeDTO especialidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Especialidade : {}", especialidadeDTO);
        if (especialidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EspecialidadeDTO result = especialidadeService.save(especialidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, especialidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especialidades : get all the especialidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of especialidades in body
     */
    @GetMapping("/especialidades")
    @Timed
    public ResponseEntity<List<EspecialidadeDTO>> getAllEspecialidades(EspecialidadeDTO especialidadeDTO, Pageable pageable) {
        log.debug("REST request to get a page of Especialidades");
        Page<EspecialidadeDTO> page = especialidadeService.findAll(especialidadeDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /especialidades/:id : get the "id" especialidade.
     *
     * @param id the id of the especialidadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especialidadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/especialidades/{id}")
    @Timed
    public ResponseEntity<EspecialidadeDTO> getEspecialidade(@PathVariable Long id) {
        log.debug("REST request to get Especialidade : {}", id);
        Optional<EspecialidadeDTO> especialidadeDTO = especialidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialidadeDTO);
    }

    /**
     * DELETE  /especialidades/:id : delete the "id" especialidade.
     *
     * @param id the id of the especialidadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especialidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspecialidade(@PathVariable Long id) {
        log.debug("REST request to delete Especialidade : {}", id);
        especialidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/especialidades?query=:query : search for the especialidade corresponding
     * to the query.
     *
     * @param query the query of the especialidade search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/especialidades")
    @Timed
    public ResponseEntity<List<EspecialidadeDTO>> searchEspecialidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Especialidades for query {}", query);
        Page<EspecialidadeDTO> page = especialidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
