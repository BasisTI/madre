package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.PrescricaoMedicamentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicamentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing
 * {@link br.com.basis.madre.prescricao.domain.PrescricaoMedicamento}.
 */
@RestController
@RequestMapping("/api")
public class PrescricaoMedicamentoResource {

	private final Logger log = LoggerFactory.getLogger(PrescricaoMedicamentoResource.class);

	private static final String ENTITY_NAME = "prescricaoPrescricaoMedicamento";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PrescricaoMedicamentoService prescricaoMedicamentoService;

	public PrescricaoMedicamentoResource(PrescricaoMedicamentoService prescricaoMedicamentoService) {
		this.prescricaoMedicamentoService = prescricaoMedicamentoService;
	}

	/**
	 * {@code POST  /prescricao-medicamentos} : Create a new prescricaoMedicamento.
	 *
	 * @param prescricaoMedicamentoDTO the prescricaoMedicamentoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new prescricaoMedicamentoDTO, or with status
	 *         {@code 400 (Bad Request)} if the prescricaoMedicamento has already an
	 *         ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/prescricao-medicamentos")
	public ResponseEntity<PrescricaoMedicamentoDTO> createPrescricaoMedicamento(
			@Valid @RequestBody PrescricaoMedicamentoDTO prescricaoMedicamentoDTO) throws URISyntaxException {
		log.debug("REST request to save PrescricaoMedicamento : {}", prescricaoMedicamentoDTO);
		if (prescricaoMedicamentoDTO.getId() != null) {
			throw new BadRequestAlertException("A new prescricaoMedicamento cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		PrescricaoMedicamentoDTO result = prescricaoMedicamentoService.save(prescricaoMedicamentoDTO);
		return ResponseEntity
				.created(new URI("/api/prescricao-medicamentos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /prescricao-medicamentos} : Updates an existing
	 * prescricaoMedicamento.
	 *
	 * @param prescricaoMedicamentoDTO the prescricaoMedicamentoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated prescricaoMedicamentoDTO, or with status
	 *         {@code 400 (Bad Request)} if the prescricaoMedicamentoDTO is not
	 *         valid, or with status {@code 500 (Internal Server Error)} if the
	 *         prescricaoMedicamentoDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/prescricao-medicamentos")
	public ResponseEntity<PrescricaoMedicamentoDTO> updatePrescricaoMedicamento(
			@Valid @RequestBody PrescricaoMedicamentoDTO prescricaoMedicamentoDTO) throws URISyntaxException {
		log.debug("REST request to update PrescricaoMedicamento : {}", prescricaoMedicamentoDTO);
		if (prescricaoMedicamentoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PrescricaoMedicamentoDTO result = prescricaoMedicamentoService.save(prescricaoMedicamentoDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				prescricaoMedicamentoDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /prescricao-medicamentos} : get all the prescricaoMedicamentos.
	 *
	 * 
	 * @param pageable the pagination information.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of prescricaoMedicamentos in body.
	 */
	@GetMapping("/prescricao-medicamentos")
	public ResponseEntity<List<PrescricaoMedicamentoDTO>> getAllPrescricaoMedicamentos(Pageable pageable) {
		log.debug("REST request to get a page of PrescricaoMedicamentos");
		Page<PrescricaoMedicamentoDTO> page = prescricaoMedicamentoService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /prescricao-medicamentos/:id} : get the "id"
	 * prescricaoMedicamento.
	 *
	 * @param id the id of the prescricaoMedicamentoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the prescricaoMedicamentoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/prescricao-medicamentos/{id}")
	public ResponseEntity<PrescricaoMedicamentoDTO> getPrescricaoMedicamento(@PathVariable Long id) {
		log.debug("REST request to get PrescricaoMedicamento : {}", id);
		Optional<PrescricaoMedicamentoDTO> prescricaoMedicamentoDTO = prescricaoMedicamentoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(prescricaoMedicamentoDTO);
	}

	/**
	 * {@code DELETE  /prescricao-medicamentos/:id} : delete the "id"
	 * prescricaoMedicamento.
	 *
	 * @param id the id of the prescricaoMedicamentoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/prescricao-medicamentos/{id}")
	public ResponseEntity<Void> deletePrescricaoMedicamento(@PathVariable Long id) {
		log.debug("REST request to delete PrescricaoMedicamento : {}", id);
		prescricaoMedicamentoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code SEARCH  /_search/prescricao-medicamentos?query=:query} : search for
	 * the prescricaoMedicamento corresponding to the query.
	 *
	 * @param query    the query of the prescricaoMedicamento search.
	 * @param pageable the pagination information.
	 * @return the result of the search.
	 */
	@GetMapping("/_search/prescricao-medicamentos")
	public ResponseEntity<List<PrescricaoMedicamentoDTO>> searchPrescricaoMedicamentos(@RequestParam String query,
			Pageable pageable) {
		log.debug("REST request to search for a page of PrescricaoMedicamentos for query {}", query);
		Page<PrescricaoMedicamentoDTO> page = prescricaoMedicamentoService.search(query, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
