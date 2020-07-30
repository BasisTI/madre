package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.SituacaoRequisicaoService;
import br.com.basis.suprimentos.service.dto.SituacaoRequisicaoDTO;
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
public class SituacaoRequisicaoResource {
    private static final String ENTITY_NAME = "madresuprimentosSituacaoRequisicao";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final SituacaoRequisicaoService situacaoRequisicaoService;

    @PostMapping("/situacoes-requisicao")
    public ResponseEntity<SituacaoRequisicaoDTO> createSituacaoRequisicao(@Valid @RequestBody SituacaoRequisicaoDTO situacaoRequisicaoDTO) throws URISyntaxException {
        log.debug("REST request to save SituacaoRequisicao : {}", situacaoRequisicaoDTO);
        if (situacaoRequisicaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new situacaoRequisicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituacaoRequisicaoDTO result = situacaoRequisicaoService.save(situacaoRequisicaoDTO);
        return ResponseEntity.created(new URI("/api/situacoes-requisicao/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/situacoes-requisicao")
    public ResponseEntity<SituacaoRequisicaoDTO> updateSituacaoRequisicao(@Valid @RequestBody SituacaoRequisicaoDTO situacaoRequisicaoDTO) throws URISyntaxException {
        log.debug("REST request to update SituacaoRequisicao : {}", situacaoRequisicaoDTO);
        if (situacaoRequisicaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SituacaoRequisicaoDTO result = situacaoRequisicaoService.save(situacaoRequisicaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, situacaoRequisicaoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/situacoes-requisicao")
    public ResponseEntity<List<SituacaoRequisicaoDTO>> getAllSituacaoRequisicaos(Pageable pageable) {
        log.debug("REST request to get a page of SituacaoRequisicaos");
        Page<SituacaoRequisicaoDTO> page = situacaoRequisicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/situacoes-requisicao/{id}")
    public ResponseEntity<SituacaoRequisicaoDTO> getSituacaoRequisicao(@PathVariable Long id) {
        log.debug("REST request to get SituacaoRequisicao : {}", id);
        Optional<SituacaoRequisicaoDTO> situacaoRequisicaoDTO = situacaoRequisicaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situacaoRequisicaoDTO);
    }

    @DeleteMapping("/situacoes-requisicao/{id}")
    public ResponseEntity<Void> deleteSituacaoRequisicao(@PathVariable Long id) {
        log.debug("REST request to delete SituacaoRequisicao : {}", id);
        situacaoRequisicaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/situacoes-requisicao")
    public ResponseEntity<List<SituacaoRequisicaoDTO>> searchSituacaoRequisicaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SituacaoRequisicaos for query {}", query);
        Page<SituacaoRequisicaoDTO> page = situacaoRequisicaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
