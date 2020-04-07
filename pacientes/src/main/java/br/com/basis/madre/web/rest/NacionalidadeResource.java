package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.NacionalidadeService;
import br.com.basis.madre.service.dto.NacionalidadeDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Nacionalidade}.
 */
@RestController
@RequestMapping("/api")
public class NacionalidadeResource {

    private final Logger log = LoggerFactory.getLogger(NacionalidadeResource.class);

    private static final String ENTITY_NAME = "pacientesNacionalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NacionalidadeService nacionalidadeService;

    public NacionalidadeResource(NacionalidadeService nacionalidadeService) {
        this.nacionalidadeService = nacionalidadeService;
    }

    /**
     * {@code POST  /nacionalidades} : Create a new nacionalidade.
     *
     * @param nacionalidadeDTO the nacionalidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nacionalidadeDTO, or with status {@code 400 (Bad Request)} if the nacionalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nacionalidades")
    public ResponseEntity<NacionalidadeDTO> createNacionalidade(@Valid @RequestBody NacionalidadeDTO nacionalidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Nacionalidade : {}", nacionalidadeDTO);
        if (nacionalidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new nacionalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NacionalidadeDTO result = nacionalidadeService.save(nacionalidadeDTO);
        return ResponseEntity.created(new URI("/api/nacionalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nacionalidades} : Updates an existing nacionalidade.
     *
     * @param nacionalidadeDTO the nacionalidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nacionalidadeDTO,
     * or with status {@code 400 (Bad Request)} if the nacionalidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nacionalidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nacionalidades")
    public ResponseEntity<NacionalidadeDTO> updateNacionalidade(@Valid @RequestBody NacionalidadeDTO nacionalidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Nacionalidade : {}", nacionalidadeDTO);
        if (nacionalidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NacionalidadeDTO result = nacionalidadeService.save(nacionalidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nacionalidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nacionalidades} : get all the nacionalidades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nacionalidades in body.
     */
    @GetMapping("/nacionalidades")
    public ResponseEntity<List<NacionalidadeDTO>> getAllNacionalidades(Pageable pageable) {
        log.debug("REST request to get a page of Nacionalidades");
        Page<NacionalidadeDTO> page = nacionalidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nacionalidades/:id} : get the "id" nacionalidade.
     *
     * @param id the id of the nacionalidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nacionalidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nacionalidades/{id}")
    public ResponseEntity<NacionalidadeDTO> getNacionalidade(@PathVariable Long id) {
        log.debug("REST request to get Nacionalidade : {}", id);
        Optional<NacionalidadeDTO> nacionalidadeDTO = nacionalidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nacionalidadeDTO);
    }

    /**
     * {@code DELETE  /nacionalidades/:id} : delete the "id" nacionalidade.
     *
     * @param id the id of the nacionalidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nacionalidades/{id}")
    public ResponseEntity<Void> deleteNacionalidade(@PathVariable Long id) {
        log.debug("REST request to delete Nacionalidade : {}", id);
        nacionalidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/nacionalidades?query=:query} : search for the nacionalidade corresponding
     * to the query.
     *
     * @param query the query of the nacionalidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/nacionalidades")
    public ResponseEntity<List<NacionalidadeDTO>> searchNacionalidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Nacionalidades for query {}", query);
        Page<NacionalidadeDTO> page = nacionalidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
