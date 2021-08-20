package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.service.InstituicaoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.seguranca.service.dto.InstituicaoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Instituicao}.
 */
@RestController
@RequestMapping("/api")
public class InstituicaoResource {

    private final Logger log = LoggerFactory.getLogger(InstituicaoResource.class);

    private static final String ENTITY_NAME = "madresegurancaInstituicao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstituicaoService instituicaoService;

    public InstituicaoResource(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    /**
     * {@code POST  /instituicaos} : Create a new instituicao.
     *
     * @param instituicaoDTO the instituicaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instituicaoDTO, or with status {@code 400 (Bad Request)} if the instituicao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instituicaos")
    public ResponseEntity<InstituicaoDTO> createInstituicao(@Valid @RequestBody InstituicaoDTO instituicaoDTO) throws URISyntaxException {
        log.debug("REST request to save Instituicao : {}", instituicaoDTO);
        if (instituicaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new instituicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstituicaoDTO result = instituicaoService.save(instituicaoDTO);
        return ResponseEntity.created(new URI("/api/instituicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instituicaos} : Updates an existing instituicao.
     *
     * @param instituicaoDTO the instituicaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituicaoDTO,
     * or with status {@code 400 (Bad Request)} if the instituicaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instituicaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instituicaos")
    public ResponseEntity<InstituicaoDTO> updateInstituicao(@Valid @RequestBody InstituicaoDTO instituicaoDTO) throws URISyntaxException {
        log.debug("REST request to update Instituicao : {}", instituicaoDTO);
        if (instituicaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstituicaoDTO result = instituicaoService.save(instituicaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, instituicaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instituicaos} : get all the instituicaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instituicaos in body.
     */
    @GetMapping("/instituicaos")
    public ResponseEntity<List<InstituicaoDTO>> getAllInstituicaos(Pageable pageable) {
        log.debug("REST request to get a page of Instituicaos");
        Page<InstituicaoDTO> page = instituicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instituicaos/:id} : get the "id" instituicao.
     *
     * @param id the id of the instituicaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instituicaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instituicaos/{id}")
    public ResponseEntity<InstituicaoDTO> getInstituicao(@PathVariable Long id) {
        log.debug("REST request to get Instituicao : {}", id);
        Optional<InstituicaoDTO> instituicaoDTO = instituicaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instituicaoDTO);
    }

    /**
     * {@code DELETE  /instituicaos/:id} : delete the "id" instituicao.
     *
     * @param id the id of the instituicaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instituicaos/{id}")
    public ResponseEntity<Void> deleteInstituicao(@PathVariable Long id) {
        log.debug("REST request to delete Instituicao : {}", id);
        instituicaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/instituicaos?query=:query} : search for the instituicao corresponding
     * to the query.
     *
     * @param query the query of the instituicao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/instituicaos")
    public ResponseEntity<List<InstituicaoDTO>> searchInstituicaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Instituicaos for query {}", query);
        Page<InstituicaoDTO> page = instituicaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
