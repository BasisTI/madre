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

import br.com.basis.madre.seguranca.service.GraduacaoService;
import br.com.basis.madre.seguranca.service.dto.GraduacaoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Graduacao}.
 */
@RestController
@RequestMapping("/api")
public class GraduacaoResource {

    private final Logger log = LoggerFactory.getLogger(GraduacaoResource.class);

    private static final String ENTITY_NAME = "madresegurancaGraduacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GraduacaoService graduacaoService;

    public GraduacaoResource(GraduacaoService graduacaoService) {
        this.graduacaoService = graduacaoService;
    }

    /**
     * {@code POST  /graduacaos} : Create a new graduacao.
     *
     * @param graduacaoDTO the graduacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new graduacaoDTO, or with status {@code 400 (Bad Request)} if the graduacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/graduacaos")
    public ResponseEntity<GraduacaoDTO> createGraduacao(@Valid @RequestBody GraduacaoDTO graduacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Graduacao : {}", graduacaoDTO);
        if (graduacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new graduacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GraduacaoDTO result = graduacaoService.save(graduacaoDTO);
        return ResponseEntity.created(new URI("/api/graduacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /graduacaos} : Updates an existing graduacao.
     *
     * @param graduacaoDTO the graduacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated graduacaoDTO,
     * or with status {@code 400 (Bad Request)} if the graduacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the graduacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/graduacaos")
    public ResponseEntity<GraduacaoDTO> updateGraduacao(@Valid @RequestBody GraduacaoDTO graduacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Graduacao : {}", graduacaoDTO);
        if (graduacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GraduacaoDTO result = graduacaoService.save(graduacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, graduacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /graduacaos} : get all the graduacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of graduacaos in body.
     */
    @GetMapping("/graduacaos")
    public ResponseEntity<List<GraduacaoDTO>> getAllGraduacaos(Pageable pageable) {
        log.debug("REST request to get a page of Graduacaos");
        Page<GraduacaoDTO> page = graduacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /graduacaos/:id} : get the "id" graduacao.
     *
     * @param id the id of the graduacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the graduacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/graduacaos/{id}")
    public ResponseEntity<GraduacaoDTO> getGraduacao(@PathVariable Long id) {
        log.debug("REST request to get Graduacao : {}", id);
        Optional<GraduacaoDTO> graduacaoDTO = graduacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(graduacaoDTO);
    }

    /**
     * {@code DELETE  /graduacaos/:id} : delete the "id" graduacao.
     *
     * @param id the id of the graduacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/graduacaos/{id}")
    public ResponseEntity<Void> deleteGraduacao(@PathVariable Long id) {
        log.debug("REST request to delete Graduacao : {}", id);
        graduacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/graduacaos?query=:query} : search for the graduacao corresponding
     * to the query.
     *
     * @param query the query of the graduacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/graduacaos")
    public ResponseEntity<List<GraduacaoDTO>> searchGraduacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Graduacaos for query {}", query);
        Page<GraduacaoDTO> page = graduacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
