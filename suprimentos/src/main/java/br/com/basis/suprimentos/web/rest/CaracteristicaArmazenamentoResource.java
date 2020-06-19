package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.CaracteristicaArmazenamentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.CaracteristicaArmazenamentoDTO;

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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.CaracteristicaArmazenamento}.
 */
@RestController
@RequestMapping("/api")
public class CaracteristicaArmazenamentoResource {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaArmazenamentoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosCaracteristicaArmazenamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaracteristicaArmazenamentoService caracteristicaArmazenamentoService;

    public CaracteristicaArmazenamentoResource(CaracteristicaArmazenamentoService caracteristicaArmazenamentoService) {
        this.caracteristicaArmazenamentoService = caracteristicaArmazenamentoService;
    }

    /**
     * {@code POST  /caracteristica-armazenamentos} : Create a new caracteristicaArmazenamento.
     *
     * @param caracteristicaArmazenamentoDTO the caracteristicaArmazenamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caracteristicaArmazenamentoDTO, or with status {@code 400 (Bad Request)} if the caracteristicaArmazenamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/caracteristica-armazenamentos")
    public ResponseEntity<CaracteristicaArmazenamentoDTO> createCaracteristicaArmazenamento(@Valid @RequestBody CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO) throws URISyntaxException {
        log.debug("REST request to save CaracteristicaArmazenamento : {}", caracteristicaArmazenamentoDTO);
        if (caracteristicaArmazenamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new caracteristicaArmazenamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaracteristicaArmazenamentoDTO result = caracteristicaArmazenamentoService.save(caracteristicaArmazenamentoDTO);
        return ResponseEntity.created(new URI("/api/caracteristica-armazenamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /caracteristica-armazenamentos} : Updates an existing caracteristicaArmazenamento.
     *
     * @param caracteristicaArmazenamentoDTO the caracteristicaArmazenamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicaArmazenamentoDTO,
     * or with status {@code 400 (Bad Request)} if the caracteristicaArmazenamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicaArmazenamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/caracteristica-armazenamentos")
    public ResponseEntity<CaracteristicaArmazenamentoDTO> updateCaracteristicaArmazenamento(@Valid @RequestBody CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO) throws URISyntaxException {
        log.debug("REST request to update CaracteristicaArmazenamento : {}", caracteristicaArmazenamentoDTO);
        if (caracteristicaArmazenamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaracteristicaArmazenamentoDTO result = caracteristicaArmazenamentoService.save(caracteristicaArmazenamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, caracteristicaArmazenamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /caracteristica-armazenamentos} : get all the caracteristicaArmazenamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caracteristicaArmazenamentos in body.
     */
    @GetMapping("/caracteristica-armazenamentos")
    public ResponseEntity<List<CaracteristicaArmazenamentoDTO>> getAllCaracteristicaArmazenamentos(Pageable pageable) {
        log.debug("REST request to get a page of CaracteristicaArmazenamentos");
        Page<CaracteristicaArmazenamentoDTO> page = caracteristicaArmazenamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /caracteristica-armazenamentos/:id} : get the "id" caracteristicaArmazenamento.
     *
     * @param id the id of the caracteristicaArmazenamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caracteristicaArmazenamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/caracteristica-armazenamentos/{id}")
    public ResponseEntity<CaracteristicaArmazenamentoDTO> getCaracteristicaArmazenamento(@PathVariable Long id) {
        log.debug("REST request to get CaracteristicaArmazenamento : {}", id);
        Optional<CaracteristicaArmazenamentoDTO> caracteristicaArmazenamentoDTO = caracteristicaArmazenamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caracteristicaArmazenamentoDTO);
    }

    /**
     * {@code DELETE  /caracteristica-armazenamentos/:id} : delete the "id" caracteristicaArmazenamento.
     *
     * @param id the id of the caracteristicaArmazenamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/caracteristica-armazenamentos/{id}")
    public ResponseEntity<Void> deleteCaracteristicaArmazenamento(@PathVariable Long id) {
        log.debug("REST request to delete CaracteristicaArmazenamento : {}", id);
        caracteristicaArmazenamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/caracteristica-armazenamentos?query=:query} : search for the caracteristicaArmazenamento corresponding
     * to the query.
     *
     * @param query the query of the caracteristicaArmazenamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/caracteristica-armazenamentos")
    public ResponseEntity<List<CaracteristicaArmazenamentoDTO>> searchCaracteristicaArmazenamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CaracteristicaArmazenamentos for query {}", query);
        Page<CaracteristicaArmazenamentoDTO> page = caracteristicaArmazenamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
