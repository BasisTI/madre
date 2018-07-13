package br.com.basis.madre.pacientes.web.rest;

import br.com.basis.madre.pacientes.domain.Paciente;
import br.com.basis.madre.pacientes.repository.PacienteRepository;
import br.com.basis.madre.pacientes.service.PacienteService;
import br.com.basis.madre.pacientes.service.exception.RelatorioException;
import br.com.basis.madre.pacientes.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.pacientes.web.rest.util.HeaderUtil;
import br.com.basis.madre.pacientes.web.rest.util.MadreUtil;
import br.com.basis.madre.pacientes.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Paciente.
 */
@RestController
@RequestMapping("/api")
public class PacienteResource {

    private final Logger log = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "paciente";

    private final PacienteRepository pacienteRepository;

    private final PacienteService pacienteService;


    public PacienteResource(PacienteService pacienteService, PacienteRepository pacienteRepository) {
        this.pacienteService = pacienteService;
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * POST  /pacientes : Create a new paciente.
     *
     * @param paciente the paciente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paciente, or with status 400 (Bad Request) if the paciente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacientes")
    @Timed
    public ResponseEntity<Paciente> createPaciente(@Valid @RequestBody Paciente paciente) throws URISyntaxException {
        log.debug("REST request to save Paciente : {}", paciente);
        if(paciente.getProntuario() == null || paciente.getProntuario().equals("")){paciente.setProntuario(String.valueOf(pacienteRepository.indexPacientes()));}
        if ((pacienteRepository.findOneByRg(paciente.getRg())).isPresent()) { return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "rgexists", "RG já cadastrado")).body(null); }
        if ((pacienteRepository.findOneByCpf(paciente.getCpf())).isPresent()) { return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "cpfexists", "CPF já cadastrado")).body(null); }
        if ((pacienteRepository.findOneByEmailPrincipalIgnoreCase(MadreUtil.removeCaracteresEmBranco(paciente.getEmailPrincipal()))).isPresent()) { return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "E-mail já cadastrado")).body(null); }
        if ((pacienteRepository.findOneByCartaoSus(paciente.getCartaoSus()).isPresent()) && (MadreUtil.removeCaracteresEmBranco(paciente.getCartaoSus()) != null)) { return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "susexists", "Cartão do SUS já cadastrado")).body(null); }
        if((pacienteRepository.findOneByProntuario(MadreUtil.removeCaracteresEmBranco(paciente.getProntuario())).isPresent())){ return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "prontuarioexists", "Prontuário já registrado")).body(null); }
        if (paciente.getId() != null) { throw new BadRequestAlertException("A new paciente cannot already have an ID", ENTITY_NAME, "idexists"); }
        Paciente result = pacienteService.save(paciente);
        return ResponseEntity.created(new URI("/api/pacientes/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }
    /**
     * PUT  /pacientes : Updates an existing paciente.
     *
     * @param paciente the paciente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paciente,
     * or with status 400 (Bad Request) if the paciente is not valid,
     * or with status 500 (Internal Server Error) if the paciente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacientes")
    @Timed
    public ResponseEntity<Paciente> updatePaciente(@Valid @RequestBody Paciente paciente) throws URISyntaxException {
        log.debug("REST request to update Paciente : {}", paciente);
        if (paciente.getId() == null) {
            return createPaciente(paciente);
        }
        Paciente result = pacienteService.save(paciente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paciente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacientes : get all the pacientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pacientes in body
     */
    @GetMapping("/pacientes")
    @Timed
    public ResponseEntity<List<Paciente>> getAllPacientes(Pageable pageable) {

        log.debug("REST request to get a page of Pacientes");
        Page<Paciente> page = pacienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pacientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pacientes/:id : get the "id" paciente.
     *
     * @param id the id of the paciente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paciente, or with status 404 (Not Found)
     */
    @GetMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<Paciente> getPaciente(@PathVariable Long id) {
        log.debug("REST request to get Paciente : {}", id);
        Paciente pacienteId = pacienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacienteId));
    }

    /**
     * DELETE  /pacientes/:id : delete the "id" paciente.
     *
     * @param id the id of the paciente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        log.debug("REST request to delete Paciente : {}", id);
        pacienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacientes?query=:query : search for the paciente corresponding
     * to the query.
     *
     * @param query    the query of the paciente search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pacientes")
    @Timed
    public ResponseEntity<List<Paciente>> searchPacientes(@RequestParam(defaultValue = "*") String query, Pageable pageable) {
        log.debug("REST request to search for a page of Pacientes for query {}", query);
        Page<Paciente> page = pacienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pacientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




    @GetMapping(value = "/paciente/exportacao/{tipoRelatorio}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public ResponseEntity<InputStreamResource> getRelatorioExportacao(@PathVariable String tipoRelatorio, @RequestParam(defaultValue = "*") String query) {
        try {
            return pacienteService.gerarRelatorioExportacao(tipoRelatorio, query);
        } catch (RelatorioException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, RelatorioException.getCodeEntidade(), e.getMessage())).body(null);
        }
    }


}
