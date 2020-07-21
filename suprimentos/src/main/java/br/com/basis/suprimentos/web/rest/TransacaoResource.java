package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.dto.TransacaoDTO;
import br.com.basis.suprimentos.service.transacoeservice;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class TransacaoResource {
    private static final String ENTITY_NAME = "madresuprimentosTransacao";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final transacoeservice transacoeservice;

    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoDTO> createTransacao(@Valid @RequestBody TransacaoDTO transacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Transacao : {}", transacaoDTO);
        if (transacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new transacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransacaoDTO result = transacoeservice.save(transacaoDTO);
        return ResponseEntity.created(new URI("/api/transacoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/transacoes")
    public ResponseEntity<List<TransacaoDTO>> getAlltransacoes(Pageable pageable) {
        log.debug("REST request to get a page of transacoes");
        Page<TransacaoDTO> page = transacoeservice.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoDTO> getTransacao(@PathVariable Long id) {
        log.debug("REST request to get Transacao : {}", id);
        Optional<TransacaoDTO> transacaoDTO = transacoeservice.findOne(id);
        return ResponseUtil.wrapOrNotFound(transacaoDTO);
    }

    @GetMapping("/_search/transacoes")
    public ResponseEntity<List<TransacaoDTO>> searchtransacoes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of transacoes for query {}", query);
        Page<TransacaoDTO> page = transacoeservice.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
