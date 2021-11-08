package br.com.basis.madre.seguranca.web.rest;

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

import br.com.basis.madre.seguranca.service.CargoService;
import br.com.basis.madre.seguranca.service.dto.CargoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Cargo}.
 */
@RestController
@RequestMapping("/api")
public class CargoResource {

    private final Logger log = LoggerFactory.getLogger(CargoResource.class);

    private static final String ENTITY_NAME = "madresegurancaCargo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CargoService cargoService;

    public CargoResource(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * {@code POST  /cargos} : Create a new cargo.
     *
     * @param cargoDTO the cargoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cargoDTO, or with status {@code 400 (Bad Request)} if the cargo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cargos")
    public ResponseEntity<CargoDTO> createCargo(@Valid @RequestBody CargoDTO cargoDTO) throws URISyntaxException {
        log.debug("REST request to save Cargo : {}", cargoDTO);
        if (cargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CargoDTO result = cargoService.save(cargoDTO);
        return ResponseEntity.created(new URI("/api/cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cargos} : Updates an existing cargo.
     *
     * @param cargoDTO the cargoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cargoDTO,
     * or with status {@code 400 (Bad Request)} if the cargoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cargoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cargos")
    public ResponseEntity<CargoDTO> updateCargo(@Valid @RequestBody CargoDTO cargoDTO) throws URISyntaxException {
        log.debug("REST request to update Cargo : {}", cargoDTO);
        if (cargoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CargoDTO result = cargoService.save(cargoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cargos} : get all the cargos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cargos in body.
     */
    @GetMapping("/cargos")
    public ResponseEntity<List<CargoDTO>> getAllCargos(Pageable pageable) {
        log.debug("REST request to get a page of Cargos");
        Page<CargoDTO> page = cargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cargos/:id} : get the "id" cargo.
     *
     * @param id the id of the cargoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cargoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cargos/{id}")
    public ResponseEntity<CargoDTO> getCargo(@PathVariable Long id) {
        log.debug("REST request to get Cargo : {}", id);
        Optional<CargoDTO> cargoDTO = cargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cargoDTO);
    }

    /**
     * {@code DELETE  /cargos/:id} : delete the "id" cargo.
     *
     * @param id the id of the cargoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cargos/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        log.debug("REST request to delete Cargo : {}", id);
        cargoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cargos?query=:query} : search for the cargo corresponding
     * to the query.
     *
     * @param query the query of the cargo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cargos")
    public ResponseEntity<List<CargoDTO>> searchCargos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cargos for query {}", query);
        Page<CargoDTO> page = cargoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
