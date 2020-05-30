package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.TipoProcedimentoService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.TipoProcedimentoDTO;

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
 * REST controller for managing
 * {@link br.com.basis.madre.prescricao.domain.TipoProcedimento}.
 */
@RestController
@RequestMapping("/api")
public class TipoProcedimentoResource {

	private final Logger log = LoggerFactory.getLogger(TipoProcedimentoResource.class);

	private static final String ENTITY_NAME = "prescricaoTipoProcedimento";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TipoProcedimentoService tipoProcedimentoService;

	public TipoProcedimentoResource(TipoProcedimentoService tipoProcedimentoService) {
		this.tipoProcedimentoService = tipoProcedimentoService;
	}

	/**
	 * {@code POST  /tipo-procedimentos} : Create a new tipoProcedimento.
	 *
	 * @param tipoProcedimentoDTO the tipoProcedimentoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tipoProcedimentoDTO, or with status
	 *         {@code 400 (Bad Request)} if the tipoProcedimento has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tipo-procedimentos")
	public ResponseEntity<TipoProcedimentoDTO> createTipoProcedimento(
			@Valid @RequestBody TipoProcedimentoDTO tipoProcedimentoDTO) throws URISyntaxException {
		log.debug("REST request to save TipoProcedimento : {}", tipoProcedimentoDTO);
		if (tipoProcedimentoDTO.getId() != null) {
			throw new BadRequestAlertException("A new tipoProcedimento cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		TipoProcedimentoDTO result = tipoProcedimentoService.save(tipoProcedimentoDTO);
		return ResponseEntity
				.created(new URI("/api/tipo-procedimentos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /tipo-procedimentos} : Updates an existing tipoProcedimento.
	 *
	 * @param tipoProcedimentoDTO the tipoProcedimentoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tipoProcedimentoDTO, or with status
	 *         {@code 400 (Bad Request)} if the tipoProcedimentoDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         tipoProcedimentoDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/tipo-procedimentos")
	public ResponseEntity<TipoProcedimentoDTO> updateTipoProcedimento(
			@Valid @RequestBody TipoProcedimentoDTO tipoProcedimentoDTO) throws URISyntaxException {
		log.debug("REST request to update TipoProcedimento : {}", tipoProcedimentoDTO);
		if (tipoProcedimentoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		TipoProcedimentoDTO result = tipoProcedimentoService.save(tipoProcedimentoDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				tipoProcedimentoDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /tipo-procedimentos} : get all the tipoProcedimentos.
	 *
	 * 
	 * @param pageable the pagination information.
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tipoProcedimentos in body.
	 */
	@GetMapping("/tipo-procedimentos")
	public ResponseEntity<List<TipoProcedimentoDTO>> getAllTipoProcedimentos(Pageable pageable) {
		log.debug("REST request to get a page of TipoProcedimentos");
		Page<TipoProcedimentoDTO> page = tipoProcedimentoService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/tipo-procedimentos/teste/{tipo}")
	public ResponseEntity<List<TipoProcedimentoDTO>> listarTipoProcedimento(
			@PathVariable(name = "tipo") String tipo, Pageable pageable) {
		Page<TipoProcedimentoDTO> page = Page.empty();

		if (tipo.equals("diversos")) {
			page = tipoProcedimentoService.listarTipoProcedimentoDiversos(pageable);
			
		} else if (tipo.equals("cirurgias-leito")) {
			page = tipoProcedimentoService.listarTipoProcedimentoCirurgias(pageable);
			
		} else if (tipo.equals("orteses-proteses")) {
			page = tipoProcedimentoService.listarTipoProcedimentoOsteseProtese(pageable);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /tipo-procedimentos/:id} : get the "id" tipoProcedimento.
	 *
	 * @param id the id of the tipoProcedimentoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tipoProcedimentoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/tipo-procedimentos/{id}")
	public ResponseEntity<TipoProcedimentoDTO> getTipoProcedimento(@PathVariable Long id) {
		log.debug("REST request to get TipoProcedimento : {}", id);
		Optional<TipoProcedimentoDTO> tipoProcedimentoDTO = tipoProcedimentoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(tipoProcedimentoDTO);
	}

	/**
	 * {@code DELETE  /tipo-procedimentos/:id} : delete the "id" tipoProcedimento.
	 *
	 * @param id the id of the tipoProcedimentoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/tipo-procedimentos/{id}")
	public ResponseEntity<Void> deleteTipoProcedimento(@PathVariable Long id) {
		log.debug("REST request to delete TipoProcedimento : {}", id);
		tipoProcedimentoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	/**
	 * {@code SEARCH  /_search/tipo-procedimentos?query=:query} : search for the
	 * tipoProcedimento corresponding to the query.
	 *
	 * @param query    the query of the tipoProcedimento search.
	 * @param pageable the pagination information.
	 * @return the result of the search.
	 */
	@GetMapping("/_search/tipo-procedimentos")
	public ResponseEntity<List<TipoProcedimentoDTO>> searchTipoProcedimentos(@RequestParam String query,
			Pageable pageable) {
		log.debug("REST request to search for a page of TipoProcedimentos for query {}", query);
		Page<TipoProcedimentoDTO> page = tipoProcedimentoService.search(query, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
