package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.ControleQualidadeService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.ControleQualidadeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.ControleQualidade}.
 */
@RestController
@RequestMapping("/api")
public class ControleQualidadeResource {

    private final Logger log = LoggerFactory.getLogger(ControleQualidadeResource.class);

    private static final String ENTITY_NAME = "madreexamesControleQualidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControleQualidadeService controleQualidadeService;

    public ControleQualidadeResource(ControleQualidadeService controleQualidadeService) {
        this.controleQualidadeService = controleQualidadeService;
    }

    /**
     * {@code POST  /controle-qualidades} : Create a new controleQualidade.
     *
     * @param controleQualidadeDTO the controleQualidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controleQualidadeDTO, or with status {@code 400 (Bad Request)} if the controleQualidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/controle-qualidades")
    public ResponseEntity<ControleQualidadeDTO> createControleQualidade(@Valid @RequestBody ControleQualidadeDTO controleQualidadeDTO) throws URISyntaxException {
        log.debug("REST request to save ControleQualidade : {}", controleQualidadeDTO);
        if (controleQualidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new controleQualidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControleQualidadeDTO result = controleQualidadeService.save(controleQualidadeDTO);
        return ResponseEntity.created(new URI("/api/controle-qualidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-qualidades} : Updates an existing controleQualidade.
     *
     * @param controleQualidadeDTO the controleQualidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controleQualidadeDTO,
     * or with status {@code 400 (Bad Request)} if the controleQualidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controleQualidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/controle-qualidades")
    public ResponseEntity<ControleQualidadeDTO> updateControleQualidade(@Valid @RequestBody ControleQualidadeDTO controleQualidadeDTO) throws URISyntaxException {
        log.debug("REST request to update ControleQualidade : {}", controleQualidadeDTO);
        if (controleQualidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ControleQualidadeDTO result = controleQualidadeService.save(controleQualidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, controleQualidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /controle-qualidades} : get all the controleQualidades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controleQualidades in body.
     */
    @GetMapping("/controle-qualidades")
    public ResponseEntity<List<ControleQualidadeDTO>> getAllControleQualidades(Pageable pageable) {
        log.debug("REST request to get a page of ControleQualidades");
        Page<ControleQualidadeDTO> page = controleQualidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /controle-qualidades/:id} : get the "id" controleQualidade.
     *
     * @param id the id of the controleQualidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controleQualidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/controle-qualidades/{id}")
    public ResponseEntity<ControleQualidadeDTO> getControleQualidade(@PathVariable Long id) {
        log.debug("REST request to get ControleQualidade : {}", id);
        Optional<ControleQualidadeDTO> controleQualidadeDTO = controleQualidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controleQualidadeDTO);
    }

    /**
     * {@code DELETE  /controle-qualidades/:id} : delete the "id" controleQualidade.
     *
     * @param id the id of the controleQualidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/controle-qualidades/{id}")
    public ResponseEntity<Void> deleteControleQualidade(@PathVariable Long id) {
        log.debug("REST request to delete ControleQualidade : {}", id);
        controleQualidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/controle-qualidades?query=:query} : search for the controleQualidade corresponding
     * to the query.
     *
     * @param query the query of the controleQualidade search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/controle-qualidades")
    public ResponseEntity<List<ControleQualidadeDTO>> searchControleQualidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ControleQualidades for query {}", query);
        Page<ControleQualidadeDTO> page = controleQualidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
