package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.EtniaService;
import br.com.basis.madre.service.dto.EtniaDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Etnia}.
 */
@RestController
@RequestMapping("/api")
public class EtniaResource {

    private final Logger log = LoggerFactory.getLogger(EtniaResource.class);

    private static final String ENTITY_NAME = "pacientesEtnia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtniaService etniaService;

    public EtniaResource(EtniaService etniaService) {
        this.etniaService = etniaService;
    }

    /**
     * {@code POST  /etnias} : Create a new etnia.
     *
     * @param etniaDTO the etniaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etniaDTO, or with status {@code 400 (Bad Request)} if the etnia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etnias")
    public ResponseEntity<EtniaDTO> createEtnia(@Valid @RequestBody EtniaDTO etniaDTO) throws URISyntaxException {
        log.debug("REST request to save Etnia : {}", etniaDTO);
        if (etniaDTO.getId() != null) {
            throw new BadRequestAlertException("A new etnia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtniaDTO result = etniaService.save(etniaDTO);
        return ResponseEntity.created(new URI("/api/etnias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etnias} : Updates an existing etnia.
     *
     * @param etniaDTO the etniaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etniaDTO,
     * or with status {@code 400 (Bad Request)} if the etniaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etniaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etnias")
    public ResponseEntity<EtniaDTO> updateEtnia(@Valid @RequestBody EtniaDTO etniaDTO) throws URISyntaxException {
        log.debug("REST request to update Etnia : {}", etniaDTO);
        if (etniaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtniaDTO result = etniaService.save(etniaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, etniaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etnias} : get all the etnias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etnias in body.
     */
    @GetMapping("/etnias")
    public ResponseEntity<List<EtniaDTO>> getAllEtnias(Pageable pageable) {
        log.debug("REST request to get a page of Etnias");
        Page<EtniaDTO> page = etniaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etnias/:id} : get the "id" etnia.
     *
     * @param id the id of the etniaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etniaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etnias/{id}")
    public ResponseEntity<EtniaDTO> getEtnia(@PathVariable Long id) {
        log.debug("REST request to get Etnia : {}", id);
        Optional<EtniaDTO> etniaDTO = etniaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etniaDTO);
    }

    /**
     * {@code DELETE  /etnias/:id} : delete the "id" etnia.
     *
     * @param id the id of the etniaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etnias/{id}")
    public ResponseEntity<Void> deleteEtnia(@PathVariable Long id) {
        log.debug("REST request to delete Etnia : {}", id);
        etniaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/etnias?query=:query} : search for the etnia corresponding
     * to the query.
     *
     * @param query the query of the etnia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/etnias")
    public ResponseEntity<List<EtniaDTO>> searchEtnias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Etnias for query {}", query);
        Page<EtniaDTO> page = etniaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
