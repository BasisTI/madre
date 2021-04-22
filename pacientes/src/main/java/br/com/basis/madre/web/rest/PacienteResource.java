package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.service.PacienteService;
import br.com.basis.madre.service.reports.PdfPacienteService;
import br.com.basis.madre.service.dto.PacienteDTO;
import br.com.basis.madre.service.dto.PacienteInclusaoDTO;
import br.com.basis.madre.service.projection.PacienteResumo;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import com.lowagie.text.DocumentException;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Paciente}.
 */
@RestController
@RequestMapping("/api")
public class PacienteResource {

    private final Logger log = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "pacientesPaciente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PacienteService pacienteService;
    private final PdfPacienteService pdfPacienteService;

    public PacienteResource(PacienteService pacienteService, PdfPacienteService pdfPacienteService) {
        this.pacienteService = pacienteService;
        this.pdfPacienteService = pdfPacienteService;
    }

    /**
     * Write documentation
     */
    @GetMapping("/pacientes/_resumo")
    public ResponseEntity<Page<PacienteResumo>> findAllProjectedPacienteResumoBy(@RequestParam(required = false,defaultValue = "") String nome,Pageable pageable) {
        return ResponseEntity.ok(pacienteService.findAllProjectedPacienteResumoBy(nome,pageable));
    }

    /*lista de paciente com elasticsearch*/
    @GetMapping("/pacientes/lista-de-pacientes-elastic")
    public ResponseEntity<Page<Paciente>> findPacienteByNameOrProntuario(@RequestParam(value = "nome",required = false) String nome,
                                                               @RequestParam(value = "prontuario",required = false) Long prontuario,
                                                               Pageable pageable) {
        return ResponseEntity.ok(pacienteService.findPacienteByNomeOrProntuario(nome, prontuario, pageable));
    }

    /**
     * {@code POST  /pacientes} : Create a new paciente.
     *
     * @param paciente the pacienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pacienteDTO, or with status {@code 400 (Bad Request)} if the paciente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/pacientes")
    public ResponseEntity<PacienteInclusaoDTO> createPaciente(@Valid @RequestBody PacienteInclusaoDTO paciente) throws URISyntaxException {
        log.debug("REST request to save Paciente : {}", paciente);
        if (paciente.getId() != null) {
            throw new BadRequestAlertException("A new paciente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacienteInclusaoDTO result = pacienteService.save(paciente);
        return ResponseEntity.created(new URI("/api/pacientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pacientes} : Updates an existing paciente.
     *
     * @param pacienteDTO the pacienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacienteDTO,
     * or with status {@code 400 (Bad Request)} if the pacienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pacienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pacientes")
    public ResponseEntity<PacienteInclusaoDTO> updatePaciente(@Valid @RequestBody PacienteInclusaoDTO pacienteDTO) {
        log.debug("REST request to update Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PacienteInclusaoDTO result = pacienteService.save(pacienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pacienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pacientes} : get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacientes in body.
     */
    @GetMapping("/pacientes")
    @Timed
    public ResponseEntity<List<PacienteDTO>> getAllPacientes(Pageable pageable) {
        log.debug("REST request to get a page of Pacientes");
        Page<PacienteDTO> page = pacienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pacientes/:id} : get the "id" paciente.
     *
     * @param id the id of the pacienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pacienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<PacienteInclusaoDTO> getPaciente(@PathVariable Long id) {
        log.debug("REST request to get Paciente : {}", id);
        Optional<PacienteInclusaoDTO> pacienteDTO = pacienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pacienteDTO);
    }

    @GetMapping("/pacientes/formulario/{id}")
    @Timed
    public ResponseEntity<byte[]> getFormularioPaciente(@PathVariable Long id) throws DocumentException {
        log.debug("REST request to get formulario by Paciente: {}", id);
        return ResponseEntity.ok(pdfPacienteService.getPdfPorPacienteId(id));
    }

    @GetMapping("/pacientes/prontuario/{prontuario}")
    @Timed
    public ResponseEntity<PacienteInclusaoDTO> getPacienteProntuario(@PathVariable Long prontuario) {
        log.debug("REST request to get Paciente : {}", prontuario);
        Optional<PacienteInclusaoDTO> pacienteDTO = pacienteService.findOneProntuario(prontuario);
        return ResponseUtil.wrapOrNotFound(pacienteDTO);
    }

    /**
     * {@code DELETE  /pacientes/:id} : delete the "id" paciente.
     *
     * @param id the id of the pacienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        log.debug("REST request to delete Paciente : {}", id);
        pacienteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pacientes?query=:query} : search for the paciente corresponding
     * to the query.
     *
     * @param query the query of the paciente search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pacientes")
    @Timed
    public ResponseEntity<List<PacienteDTO>> searchPacientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Pacientes for query {}", query);
        Page<PacienteDTO> page = pacienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
