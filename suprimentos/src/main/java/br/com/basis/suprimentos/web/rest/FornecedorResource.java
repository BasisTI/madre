package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.FornecedorService;
import br.com.basis.suprimentos.service.dto.FornecedorDTO;
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
public class FornecedorResource {
    private static final String ENTITY_NAME = "madresuprimentosFornecedor";
    private final FornecedorService fornecedorService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/fornecedores")
    public ResponseEntity<FornecedorDTO> createFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedorDTO);
        if (fornecedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FornecedorDTO result = fornecedorService.save(fornecedorDTO);
        return ResponseEntity.created(new URI("/api/fornecedores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/fornecedores")
    public ResponseEntity<FornecedorDTO> updateFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}", fornecedorDTO);
        if (fornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FornecedorDTO result = fornecedorService.save(fornecedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fornecedorDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/fornecedores")
    public ResponseEntity<List<FornecedorDTO>> getAllFornecedors(Pageable pageable) {
        log.debug("REST request to get a page of Fornecedors");
        Page<FornecedorDTO> page = fornecedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/fornecedores/{id}")
    public ResponseEntity<FornecedorDTO> getFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<FornecedorDTO> fornecedorDTO = fornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorDTO);
    }

    @DeleteMapping("/fornecedores/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/fornecedores")
    public ResponseEntity<List<FornecedorDTO>> searchFornecedors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fornecedors for query {}", query);
        Page<FornecedorDTO> page = fornecedorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
