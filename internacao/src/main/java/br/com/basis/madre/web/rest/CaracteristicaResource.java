package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CaracteristicaService;
import br.com.basis.madre.service.dto.CaracteristicaDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Caracteristica}.
 */
@RestController
@RequestMapping("/api")
public class CaracteristicaResource {

    private final Logger log = LoggerFactory.getLogger(CaracteristicaResource.class);

    private static final String ENTITY_NAME = "internacaoCaracteristica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaracteristicaService caracteristicaService;

    public CaracteristicaResource(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    /**
     * {@code POST  /caracteristicas} : Create a new caracteristica.
     *
     * @param caracteristicaDTO the caracteristicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caracteristicaDTO, or with status {@code 400 (Bad Request)} if the caracteristica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/caracteristicas")
    public ResponseEntity<CaracteristicaDTO> createCaracteristica(@RequestBody CaracteristicaDTO caracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to save Caracteristica : {}", caracteristicaDTO);
        if (caracteristicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new caracteristica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaracteristicaDTO result = caracteristicaService.save(caracteristicaDTO);
        return ResponseEntity.created(new URI("/api/caracteristicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /caracteristicas} : Updates an existing caracteristica.
     *
     * @param caracteristicaDTO the caracteristicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caracteristicaDTO,
     * or with status {@code 400 (Bad Request)} if the caracteristicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caracteristicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/caracteristicas")
    public ResponseEntity<CaracteristicaDTO> updateCaracteristica(@RequestBody CaracteristicaDTO caracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to update Caracteristica : {}", caracteristicaDTO);
        if (caracteristicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaracteristicaDTO result = caracteristicaService.save(caracteristicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, caracteristicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /caracteristicas} : get all the caracteristicas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caracteristicas in body.
     */
    @GetMapping("/caracteristicas")
    public ResponseEntity<List<CaracteristicaDTO>> getAllCaracteristicas(Pageable pageable) {
        log.debug("REST request to get a page of Caracteristicas");
        Page<CaracteristicaDTO> page = caracteristicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /caracteristicas/:id} : get the "id" caracteristica.
     *
     * @param id the id of the caracteristicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caracteristicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/caracteristicas/{id}")
    public ResponseEntity<CaracteristicaDTO> getCaracteristica(@PathVariable Long id) {
        log.debug("REST request to get Caracteristica : {}", id);
        Optional<CaracteristicaDTO> caracteristicaDTO = caracteristicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caracteristicaDTO);
    }

    /**
     * {@code DELETE  /caracteristicas/:id} : delete the "id" caracteristica.
     *
     * @param id the id of the caracteristicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/caracteristicas/{id}")
    public ResponseEntity<Void> deleteCaracteristica(@PathVariable Long id) {
        log.debug("REST request to delete Caracteristica : {}", id);
        caracteristicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/caracteristicas?query=:query} : search for the caracteristica corresponding
     * to the query.
     *
     * @param query the query of the caracteristica search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/caracteristicas")
    public ResponseEntity<List<CaracteristicaDTO>> searchCaracteristicas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Caracteristicas for query {}", query);
        Page<CaracteristicaDTO> page = caracteristicaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
