package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.AutorizacaoFornecimentoService;
import br.com.basis.suprimentos.service.dto.AutorizacaoFornecimentoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class AutorizacaoFornecimentoResource {
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "madresuprimentosAutorizacaoFornecimento";
    private final AutorizacaoFornecimentoService autorizacaoFornecimentoService;

    @PostMapping("/autorizacoes-fornecimento")
    public ResponseEntity<AutorizacaoFornecimentoDTO> createAutorizacaoFornecimento(@Valid @RequestBody AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) throws URISyntaxException {
        log.debug("REST request to save AutorizacaoFornecimento : {}", autorizacaoFornecimentoDTO);
        if (autorizacaoFornecimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new autorizacaoFornecimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutorizacaoFornecimentoDTO result = autorizacaoFornecimentoService.save(autorizacaoFornecimentoDTO);
        return ResponseEntity.created(new URI("/api/autorizacoes-fornecimento/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/autorizacoes-fornecimento")
    public ResponseEntity<AutorizacaoFornecimentoDTO> updateAutorizacaoFornecimento(@Valid @RequestBody AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) throws URISyntaxException {
        log.debug("REST request to update AutorizacaoFornecimento : {}", autorizacaoFornecimentoDTO);
        if (autorizacaoFornecimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutorizacaoFornecimentoDTO result = autorizacaoFornecimentoService.save(autorizacaoFornecimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autorizacaoFornecimentoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/autorizacoes-fornecimento")
    public ResponseEntity<List<AutorizacaoFornecimentoDTO>> getAllAutorizacaoFornecimentos(Pageable pageable, AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO) {
        log.debug("REST request to get a page of AutorizacaoFornecimentos");
        Page<AutorizacaoFornecimentoDTO> page = autorizacaoFornecimentoService.findAll(pageable, autorizacaoFornecimentoDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/autorizacoes-fornecimento/{id}")
    public ResponseEntity<AutorizacaoFornecimentoDTO> getAutorizacaoFornecimento(@PathVariable Long id) {
        log.debug("REST request to get AutorizacaoFornecimento : {}", id);
        Optional<AutorizacaoFornecimentoDTO> autorizacaoFornecimentoDTO = autorizacaoFornecimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autorizacaoFornecimentoDTO);
    }

    @DeleteMapping("/autorizacoes-fornecimento/{id}")
    public ResponseEntity<Void> deleteAutorizacaoFornecimento(@PathVariable Long id) {
        log.debug("REST request to delete AutorizacaoFornecimento : {}", id);
        autorizacaoFornecimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/autorizacoes-fornecimento")
    public ResponseEntity<List<AutorizacaoFornecimentoDTO>> searchAutorizacaoFornecimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AutorizacaoFornecimentos for query {}", query);
        Page<AutorizacaoFornecimentoDTO> page = autorizacaoFornecimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
