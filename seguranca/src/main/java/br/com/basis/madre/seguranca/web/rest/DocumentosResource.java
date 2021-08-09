package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.service.DocumentosService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.seguranca.service.dto.DocumentosDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Documentos}.
 */
@RestController
@RequestMapping("/api")
public class DocumentosResource {

    private final Logger log = LoggerFactory.getLogger(DocumentosResource.class);

    private static final String ENTITY_NAME = "madresegurancaDocumentos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentosService documentosService;

    public DocumentosResource(DocumentosService documentosService) {
        this.documentosService = documentosService;
    }

    /**
     * {@code POST  /documentos} : Create a new documentos.
     *
     * @param documentosDTO the documentosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentosDTO, or with status {@code 400 (Bad Request)} if the documentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentos")
    public ResponseEntity<DocumentosDTO> createDocumentos(@RequestBody DocumentosDTO documentosDTO) throws URISyntaxException {
        log.debug("REST request to save Documentos : {}", documentosDTO);
        if (documentosDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentosDTO result = documentosService.save(documentosDTO);
        return ResponseEntity.created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentos} : Updates an existing documentos.
     *
     * @param documentosDTO the documentosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentosDTO,
     * or with status {@code 400 (Bad Request)} if the documentosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentos")
    public ResponseEntity<DocumentosDTO> updateDocumentos(@RequestBody DocumentosDTO documentosDTO) throws URISyntaxException {
        log.debug("REST request to update Documentos : {}", documentosDTO);
        if (documentosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentosDTO result = documentosService.save(documentosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentos} : get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentos in body.
     */
    @GetMapping("/documentos")
    public ResponseEntity<List<DocumentosDTO>> getAllDocumentos(Pageable pageable) {
        log.debug("REST request to get a page of Documentos");
        Page<DocumentosDTO> page = documentosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documentos/:id} : get the "id" documentos.
     *
     * @param id the id of the documentosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentos/{id}")
    public ResponseEntity<DocumentosDTO> getDocumentos(@PathVariable Long id) {
        log.debug("REST request to get Documentos : {}", id);
        Optional<DocumentosDTO> documentosDTO = documentosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentosDTO);
    }

    /**
     * {@code DELETE  /documentos/:id} : delete the "id" documentos.
     *
     * @param id the id of the documentosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> deleteDocumentos(@PathVariable Long id) {
        log.debug("REST request to delete Documentos : {}", id);
        documentosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/documentos?query=:query} : search for the documentos corresponding
     * to the query.
     *
     * @param query the query of the documentos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/documentos")
    public ResponseEntity<List<DocumentosDTO>> searchDocumentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Documentos for query {}", query);
        Page<DocumentosDTO> page = documentosService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
