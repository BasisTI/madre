package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ReligiaoService;
import br.com.basis.madre.service.dto.ReligiaoDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Religiao}.
 */
@RestController
@RequestMapping("/api")
public class ReligiaoResource {

    private final Logger log = LoggerFactory.getLogger(ReligiaoResource.class);

    private static final String ENTITY_NAME = "pacientesReligiao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReligiaoService religiaoService;

    public ReligiaoResource(ReligiaoService religiaoService) {
        this.religiaoService = religiaoService;
    }

    /**
     * {@code POST  /religiaos} : Create a new religiao.
     *
     * @param religiaoDTO the religiaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new religiaoDTO, or with status {@code 400 (Bad Request)} if the religiao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/religiaos")
    public ResponseEntity<ReligiaoDTO> createReligiao(@Valid @RequestBody ReligiaoDTO religiaoDTO) throws URISyntaxException {
        log.debug("REST request to save Religiao : {}", religiaoDTO);
        if (religiaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new religiao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReligiaoDTO result = religiaoService.save(religiaoDTO);
        return ResponseEntity.created(new URI("/api/religiaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /religiaos} : Updates an existing religiao.
     *
     * @param religiaoDTO the religiaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated religiaoDTO,
     * or with status {@code 400 (Bad Request)} if the religiaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the religiaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/religiaos")
    public ResponseEntity<ReligiaoDTO> updateReligiao(@Valid @RequestBody ReligiaoDTO religiaoDTO) throws URISyntaxException {
        log.debug("REST request to update Religiao : {}", religiaoDTO);
        if (religiaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReligiaoDTO result = religiaoService.save(religiaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, religiaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /religiaos} : get all the religiaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of religiaos in body.
     */
    @GetMapping("/religiaos")
    public ResponseEntity<List<ReligiaoDTO>> getAllReligiaos(Pageable pageable) {
        log.debug("REST request to get a page of Religiaos");
        Page<ReligiaoDTO> page = religiaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /religiaos/:id} : get the "id" religiao.
     *
     * @param id the id of the religiaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the religiaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/religiaos/{id}")
    public ResponseEntity<ReligiaoDTO> getReligiao(@PathVariable Long id) {
        log.debug("REST request to get Religiao : {}", id);
        Optional<ReligiaoDTO> religiaoDTO = religiaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(religiaoDTO);
    }

    /**
     * {@code DELETE  /religiaos/:id} : delete the "id" religiao.
     *
     * @param id the id of the religiaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/religiaos/{id}")
    public ResponseEntity<Void> deleteReligiao(@PathVariable Long id) {
        log.debug("REST request to delete Religiao : {}", id);
        religiaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/religiaos?query=:query} : search for the religiao corresponding
     * to the query.
     *
     * @param query the query of the religiao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/religiaos")
    public ResponseEntity<List<ReligiaoDTO>> searchReligiaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Religiaos for query {}", query);
        Page<ReligiaoDTO> page = religiaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
