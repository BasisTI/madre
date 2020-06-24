package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.OcupacaoService;
import br.com.basis.madre.service.dto.OcupacaoDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Ocupacao}.
 */
@RestController
@RequestMapping("/api")
public class OcupacaoResource {

    private final Logger log = LoggerFactory.getLogger(OcupacaoResource.class);

    private static final String ENTITY_NAME = "pacientesOcupacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OcupacaoService ocupacaoService;

    public OcupacaoResource(OcupacaoService ocupacaoService) {
        this.ocupacaoService = ocupacaoService;
    }

    /**
     * {@code POST  /ocupacaos} : Create a new ocupacao.
     *
     * @param ocupacaoDTO the ocupacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * ocupacaoDTO, or with status {@code 400 (Bad Request)} if the ocupacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ocupacaos")
    public ResponseEntity<OcupacaoDTO> createOcupacao(@Valid @RequestBody OcupacaoDTO ocupacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Ocupacao : {}", ocupacaoDTO);
        if (ocupacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ocupacao cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        OcupacaoDTO result = ocupacaoService.save(ocupacaoDTO);
        return ResponseEntity.created(new URI("/api/ocupacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ocupacaos} : Updates an existing ocupacao.
     *
     * @param ocupacaoDTO the ocupacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * ocupacaoDTO, or with status {@code 400 (Bad Request)} if the ocupacaoDTO is not valid, or
     * with status {@code 500 (Internal Server Error)} if the ocupacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ocupacaos")
    public ResponseEntity<OcupacaoDTO> updateOcupacao(@Valid @RequestBody OcupacaoDTO ocupacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to update Ocupacao : {}", ocupacaoDTO);
        if (ocupacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OcupacaoDTO result = ocupacaoService.save(ocupacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                ocupacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ocupacaos} : get all the ocupacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ocupacaos in
     * body.
     */
    @GetMapping("/ocupacaos")
    public ResponseEntity<List<OcupacaoDTO>> getAllOcupacaos(OcupacaoDTO ocupacaoDTO,
        Pageable pageable) {
        log.debug("REST request to get a page of Ocupacaos");
        Page<OcupacaoDTO> page = ocupacaoService.findAll(ocupacaoDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ocupacaos/:id} : get the "id" ocupacao.
     *
     * @param id the id of the ocupacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * ocupacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ocupacaos/{id}")
    public ResponseEntity<OcupacaoDTO> getOcupacao(@PathVariable Long id) {
        log.debug("REST request to get Ocupacao : {}", id);
        Optional<OcupacaoDTO> ocupacaoDTO = ocupacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ocupacaoDTO);
    }

    /**
     * {@code DELETE  /ocupacaos/:id} : delete the "id" ocupacao.
     *
     * @param id the id of the ocupacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ocupacaos/{id}")
    public ResponseEntity<Void> deleteOcupacao(@PathVariable Long id) {
        log.debug("REST request to delete Ocupacao : {}", id);
        ocupacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ocupacaos?query=:query} : search for the ocupacao corresponding to
     * the query.
     *
     * @param query    the query of the ocupacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ocupacaos")
    public ResponseEntity<List<OcupacaoDTO>> searchOcupacaos(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of Ocupacaos for query {}", query);
        Page<OcupacaoDTO> page = ocupacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
