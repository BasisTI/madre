package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.service.MedicamentoService;
import br.com.basis.madre.farmacia.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.farmacia.service.dto.MedicamentoDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.farmacia.domain.Medicamento}.
 */
@RestController
@RequestMapping("/api")
public class MedicamentoResource {

    private final Logger log = LoggerFactory.getLogger(MedicamentoResource.class);

    private static final String ENTITY_NAME = "farmaciaMedicamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicamentoService medicamentoService;

    public MedicamentoResource(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    /**
     * {@code POST  /medicamentos} : Create a new medicamento.
     *
     * @param medicamentoDTO the medicamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicamentoDTO, or with status {@code 400 (Bad Request)} if the medicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicamentos")
    public ResponseEntity<MedicamentoDTO> createMedicamento(@RequestBody MedicamentoDTO medicamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Medicamento : {}", medicamentoDTO);
        if (medicamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicamentoDTO result = medicamentoService.save(medicamentoDTO);
        return ResponseEntity.created(new URI("/api/medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicamentos} : Updates an existing medicamento.
     *
     * @param medicamentoDTO the medicamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicamentoDTO,
     * or with status {@code 400 (Bad Request)} if the medicamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicamentos")
    public ResponseEntity<MedicamentoDTO> updateMedicamento(@RequestBody MedicamentoDTO medicamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Medicamento : {}", medicamentoDTO);
        if (medicamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicamentoDTO result = medicamentoService.save(medicamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicamentos} : get all the medicamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicamentos in body.
     */
    @GetMapping("/medicamentos")
    public ResponseEntity<List<MedicamentoDTO>> getAllMedicamentos(Pageable pageable) {
        log.debug("REST request to get a page of Medicamentos");
        Page<MedicamentoDTO> page = medicamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicamentos/:id} : get the "id" medicamento.
     *
     * @param id the id of the medicamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicamentos/{id}")
    public ResponseEntity<MedicamentoDTO> getMedicamento(@PathVariable Long id) {
        log.debug("REST request to get Medicamento : {}", id);
        Optional<MedicamentoDTO> medicamentoDTO = medicamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicamentoDTO);
    }

    /**
     * {@code DELETE  /medicamentos/:id} : delete the "id" medicamento.
     *
     * @param id the id of the medicamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicamentos/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete Medicamento : {}", id);
        medicamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/medicamentos?query=:query} : search for the medicamento corresponding
     * to the query.
     *
     * @param query the query of the medicamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/medicamentos")
    public ResponseEntity<List<MedicamentoDTO>> searchMedicamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Medicamentos for query {}", query);
        Page<MedicamentoDTO> page = medicamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/pesquisa-medicamentos")
    public Page<Medicamento> getMedicamentos
        (@RequestParam(required = false) String codigo,@RequestParam(required = false)   String descricao,@RequestParam(required = false) String ativo, Pageable pageable) {
        log.debug("REST request to get a page of Medicamentos");
        Page<Medicamento> page = medicamentoService.findMedicamento( codigo, descricao, ativo, pageable);

        return page;
    }
    @PostMapping("/medicamentos-elastic")
    public ResponseEntity<MedicamentoDTO> createMedicamentoElastic(@RequestBody Medicamento medicamento) throws URISyntaxException {

        MedicamentoDTO result = medicamentoService.saveElastic(medicamento);
        return ResponseEntity.created(new URI("/api/medicamentos-elastic/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}
