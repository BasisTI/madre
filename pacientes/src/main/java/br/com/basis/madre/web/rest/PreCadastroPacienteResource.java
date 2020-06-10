package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.PreCadastroPacienteService;
import br.com.basis.madre.service.dto.PreCadastroPacienteDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.PreCadastroPaciente}.
 */
@RestController
@RequestMapping("/api")
public class PreCadastroPacienteResource {

    private final Logger log = LoggerFactory.getLogger(PreCadastroPacienteResource.class);

    private static final String ENTITY_NAME = "pacientesPreCadastroPaciente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreCadastroPacienteService preCadastroPacienteService;

    public PreCadastroPacienteResource(PreCadastroPacienteService preCadastroPacienteService) {
        this.preCadastroPacienteService = preCadastroPacienteService;
    }

    /**
     * {@code POST  /pre-cadastro-pacientes} : Create a new preCadastroPaciente.
     *
     * @param preCadastroPacienteDTO the preCadastroPacienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preCadastroPacienteDTO, or with status {@code 400 (Bad Request)} if the preCadastroPaciente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pre-cadastro-pacientes")
    @Timed
    public ResponseEntity<PreCadastroPacienteDTO> createPreCadastroPaciente(@Valid @RequestBody PreCadastroPacienteDTO preCadastroPacienteDTO)
        throws URISyntaxException {
        log.debug("REST request to save PreCadastroPaciente : {}", preCadastroPacienteDTO);
        if (preCadastroPacienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new preCadastroPaciente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreCadastroPacienteDTO result = preCadastroPacienteService.save(preCadastroPacienteDTO);
        return ResponseEntity.created(new URI("/api/pre-cadastro-pacientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pre-cadastro-pacientes} : Updates an existing preCadastroPaciente.
     *
     * @param preCadastroPacienteDTO the preCadastroPacienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preCadastroPacienteDTO,
     * or with status {@code 400 (Bad Request)} if the preCadastroPacienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preCadastroPacienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pre-cadastro-pacientes")
    public ResponseEntity<PreCadastroPacienteDTO> updatePreCadastroPaciente(@Valid @RequestBody PreCadastroPacienteDTO preCadastroPacienteDTO) throws URISyntaxException {
        log.debug("REST request to update PreCadastroPaciente : {}", preCadastroPacienteDTO);
        if (preCadastroPacienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreCadastroPacienteDTO result = preCadastroPacienteService.save(preCadastroPacienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, preCadastroPacienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pre-cadastro-pacientes} : get all the preCadastroPacientes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preCadastroPacientes in body.
     */
    @GetMapping("/pre-cadastro-pacientes")
    public ResponseEntity<List<PreCadastroPacienteDTO>> getAllPreCadastroPacientes(@RequestParam(required = false,defaultValue = "", name="query") String nome, Pageable pageable) {
        log.debug("REST request to get a page of PreCadastroPacientes");
        Page<PreCadastroPacienteDTO> page = preCadastroPacienteService.findByName(nome,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pre-cadastro-pacientes/:id} : get the "id" preCadastroPaciente.
     *
     * @param id the id of the preCadastroPacienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preCadastroPacienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pre-cadastro-pacientes/{id}")
    public ResponseEntity<PreCadastroPacienteDTO> getPreCadastroPaciente(@PathVariable Long id) {
        log.debug("REST request to get PreCadastroPaciente : {}", id);
        Optional<PreCadastroPacienteDTO> preCadastroPacienteDTO = preCadastroPacienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preCadastroPacienteDTO);
    }

    /**
     * {@code DELETE  /pre-cadastro-pacientes/:id} : delete the "id" preCadastroPaciente.
     *
     * @param id the id of the preCadastroPacienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pre-cadastro-pacientes/{id}")
    public ResponseEntity<Void> deletePreCadastroPaciente(@PathVariable Long id) {
        log.debug("REST request to delete PreCadastroPaciente : {}", id);
        preCadastroPacienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pre-cadastro-pacientes?query=:query} : search for the preCadastroPaciente corresponding
     * to the query.
     *
     * @param query the query of the preCadastroPaciente search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pre-cadastro-pacientes")
    public ResponseEntity<List<PreCadastroPacienteDTO>> searchPreCadastroPacientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PreCadastroPacientes for query {}", query);
        Page<PreCadastroPacienteDTO> page = preCadastroPacienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
