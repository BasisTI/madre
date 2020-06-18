package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.DocumentoFiscalEntradaService;
import br.com.basis.suprimentos.service.dto.DocumentoFiscalEntradaDTO;
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
 * REST controller for managing {@link br.com.basis.suprimentos.domain.DocumentoFiscalEntrada}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoFiscalEntradaResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoFiscalEntradaResource.class);

    private static final String ENTITY_NAME = "madresuprimentosDocumentoFiscalEntrada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoFiscalEntradaService documentoFiscalEntradaService;

    public DocumentoFiscalEntradaResource(DocumentoFiscalEntradaService documentoFiscalEntradaService) {
        this.documentoFiscalEntradaService = documentoFiscalEntradaService;
    }

    /**
     * {@code POST  /documentos-fiscais} : Create a new documentoFiscalEntrada.
     *
     * @param documentoFiscalEntradaDTO the documentoFiscalEntradaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoFiscalEntradaDTO, or with status {@code 400 (Bad Request)} if the documentoFiscalEntrada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentos-fiscais")
    public ResponseEntity<DocumentoFiscalEntradaDTO> createDocumentoFiscalEntrada(@Valid @RequestBody DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentoFiscalEntrada : {}", documentoFiscalEntradaDTO);
        if (documentoFiscalEntradaDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentoFiscalEntrada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentoFiscalEntradaDTO result = documentoFiscalEntradaService.save(documentoFiscalEntradaDTO);
        return ResponseEntity.created(new URI("/api/documentos-fiscais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /documentos-fiscais} : Updates an existing documentoFiscalEntrada.
     *
     * @param documentoFiscalEntradaDTO the documentoFiscalEntradaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoFiscalEntradaDTO,
     * or with status {@code 400 (Bad Request)} if the documentoFiscalEntradaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoFiscalEntradaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentos-fiscais")
    public ResponseEntity<DocumentoFiscalEntradaDTO> updateDocumentoFiscalEntrada(@Valid @RequestBody DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentoFiscalEntrada : {}", documentoFiscalEntradaDTO);
        if (documentoFiscalEntradaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentoFiscalEntradaDTO result = documentoFiscalEntradaService.save(documentoFiscalEntradaDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, documentoFiscalEntradaDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /documentos-fiscais} : get all the documentoFiscalEntradas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentoFiscalEntradas in body.
     */
    @GetMapping("/documentos-fiscais")
    public ResponseEntity<List<DocumentoFiscalEntradaDTO>> getAllDocumentoFiscalEntradas(Pageable pageable, DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) {
        log.debug("REST request to get a page of DocumentoFiscalEntradas");
        Page<DocumentoFiscalEntradaDTO> page = documentoFiscalEntradaService.findAll(pageable, documentoFiscalEntradaDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documentos-fiscais/:id} : get the "id" documentoFiscalEntrada.
     *
     * @param id the id of the documentoFiscalEntradaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoFiscalEntradaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentos-fiscais/{id}")
    public ResponseEntity<DocumentoFiscalEntradaDTO> getDocumentoFiscalEntrada(@PathVariable Long id) {
        log.debug("REST request to get DocumentoFiscalEntrada : {}", id);
        Optional<DocumentoFiscalEntradaDTO> documentoFiscalEntradaDTO = documentoFiscalEntradaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoFiscalEntradaDTO);
    }

    /**
     * {@code DELETE  /documentos-fiscais/:id} : delete the "id" documentoFiscalEntrada.
     *
     * @param id the id of the documentoFiscalEntradaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentos-fiscais/{id}")
    public ResponseEntity<Void> deleteDocumentoFiscalEntrada(@PathVariable Long id) {
        log.debug("REST request to delete DocumentoFiscalEntrada : {}", id);
        documentoFiscalEntradaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/documentos-fiscais?query=:query} : search for the documentoFiscalEntrada corresponding
     * to the query.
     *
     * @param query    the query of the documentoFiscalEntrada search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/documentos-fiscais")
    public ResponseEntity<List<DocumentoFiscalEntradaDTO>> searchDocumentoFiscalEntradas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentoFiscalEntradas for query {}", query);
        Page<DocumentoFiscalEntradaDTO> page = documentoFiscalEntradaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
