package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.SolicitacaoComprasService;
import br.com.basis.suprimentos.service.dto.SolicitacaoComprasDTO;
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
public class SolicitacaoComprasResource {
    private static final String ENTITY_NAME = "madresuprimentosSolicitacaoCompras";
    private final SolicitacaoComprasService solicitacaoComprasService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/solicitacoes-compra")
    public ResponseEntity<SolicitacaoComprasDTO> createSolicitacaoCompras(@Valid @RequestBody SolicitacaoComprasDTO solicitacaoComprasDTO) throws URISyntaxException {
        log.debug("REST request to save SolicitacaoCompras : {}", solicitacaoComprasDTO);
        if (solicitacaoComprasDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitacaoCompras cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoComprasDTO result = solicitacaoComprasService.save(solicitacaoComprasDTO);
        return ResponseEntity.created(new URI("/api/solicitacoes-compra/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/solicitacoes-compra")
    public ResponseEntity<SolicitacaoComprasDTO> updateSolicitacaoCompras(@Valid @RequestBody SolicitacaoComprasDTO solicitacaoComprasDTO) throws URISyntaxException {
        log.debug("REST request to update SolicitacaoCompras : {}", solicitacaoComprasDTO);
        if (solicitacaoComprasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitacaoComprasDTO result = solicitacaoComprasService.save(solicitacaoComprasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoComprasDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/solicitacoes-compra")
    public ResponseEntity<List<SolicitacaoComprasDTO>> getAllSolicitacaoCompras(Pageable pageable) {
        log.debug("REST request to get a page of SolicitacaoCompras");
        Page<SolicitacaoComprasDTO> page = solicitacaoComprasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/solicitacoes-compra/{id}")
    public ResponseEntity<SolicitacaoComprasDTO> getSolicitacaoCompras(@PathVariable Long id) {
        log.debug("REST request to get SolicitacaoCompras : {}", id);
        Optional<SolicitacaoComprasDTO> solicitacaoComprasDTO = solicitacaoComprasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoComprasDTO);
    }

    @DeleteMapping("/solicitacoes-compra/{id}")
    public ResponseEntity<Void> deleteSolicitacaoCompras(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoCompras : {}", id);
        solicitacaoComprasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/solicitacoes-compra")
    public ResponseEntity<List<SolicitacaoComprasDTO>> searchSolicitacaoCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolicitacaoCompras for query {}", query);
        Page<SolicitacaoComprasDTO> page = solicitacaoComprasService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
