package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.DocumentoFiscalEntradaService;
import br.com.basis.suprimentos.service.dto.DocumentoFiscalEntradaDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DocumentoFiscalEntradaResource {
    private static final String ENTITY_NAME = "madresuprimentosDocumentoFiscalEntrada";
    private final DocumentoFiscalEntradaService documentoFiscalEntradaService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

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

    @GetMapping("/documentos-fiscais")
    public ResponseEntity<List<DocumentoFiscalEntradaDTO>> getAllDocumentoFiscalEntradas(Pageable pageable, DocumentoFiscalEntradaDTO documentoFiscalEntradaDTO) {
        log.debug("REST request to get a page of DocumentoFiscalEntradas");
        Page<DocumentoFiscalEntradaDTO> page = documentoFiscalEntradaService.findAll(pageable, documentoFiscalEntradaDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/documentos-fiscais/{id}")
    public ResponseEntity<DocumentoFiscalEntradaDTO> getDocumentoFiscalEntrada(@PathVariable Long id) {
        log.debug("REST request to get DocumentoFiscalEntrada : {}", id);
        Optional<DocumentoFiscalEntradaDTO> documentoFiscalEntradaDTO = documentoFiscalEntradaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoFiscalEntradaDTO);
    }

    @DeleteMapping("/documentos-fiscais/{id}")
    public ResponseEntity<Void> deleteDocumentoFiscalEntrada(@PathVariable Long id) {
        log.debug("REST request to delete DocumentoFiscalEntrada : {}", id);
        documentoFiscalEntradaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/documentos-fiscais")
    public ResponseEntity<List<DocumentoFiscalEntradaDTO>> searchDocumentoFiscalEntradas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentoFiscalEntradas for query {}", query);
        Page<DocumentoFiscalEntradaDTO> page = documentoFiscalEntradaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
