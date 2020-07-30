package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TipoTransacaoService;
import br.com.basis.suprimentos.service.dto.TipoTransacaoDTO;
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
public class TipoTransacaoResource {
    private static final String ENTITY_NAME = "madresuprimentosTipoTransacao";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final TipoTransacaoService tipoTransacaoService;

    @PostMapping("/tipos-transacao")
    public ResponseEntity<TipoTransacaoDTO> createTipoTransacao(@Valid @RequestBody TipoTransacaoDTO tipoTransacaoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoTransacao : {}", tipoTransacaoDTO);
        if (tipoTransacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoTransacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoTransacaoDTO result = tipoTransacaoService.save(tipoTransacaoDTO);
        return ResponseEntity.created(new URI("/api/tipos-transacao/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tipos-transacao")
    public ResponseEntity<TipoTransacaoDTO> updateTipoTransacao(@Valid @RequestBody TipoTransacaoDTO tipoTransacaoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoTransacao : {}", tipoTransacaoDTO);
        if (tipoTransacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoTransacaoDTO result = tipoTransacaoService.save(tipoTransacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoTransacaoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/tipos-transacao")
    public ResponseEntity<List<TipoTransacaoDTO>> getAllTipoTransacaos(Pageable pageable) {
        log.debug("REST request to get a page of TipoTransacaos");
        Page<TipoTransacaoDTO> page = tipoTransacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tipos-transacao/{id}")
    public ResponseEntity<TipoTransacaoDTO> getTipoTransacao(@PathVariable Long id) {
        log.debug("REST request to get TipoTransacao : {}", id);
        Optional<TipoTransacaoDTO> tipoTransacaoDTO = tipoTransacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoTransacaoDTO);
    }

    @DeleteMapping("/tipos-transacao/{id}")
    public ResponseEntity<Void> deleteTipoTransacao(@PathVariable Long id) {
        log.debug("REST request to delete TipoTransacao : {}", id);
        tipoTransacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/tipos-transacao")
    public ResponseEntity<List<TipoTransacaoDTO>> searchTipoTransacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoTransacaos for query {}", query);
        Page<TipoTransacaoDTO> page = tipoTransacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
