package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TipoLancamentoService;
import br.com.basis.suprimentos.service.dto.TipoLancamentoDTO;
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
public class TipoLancamentoResource {
    private static final String ENTITY_NAME = "madresuprimentosTipoLancamento";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final TipoLancamentoService tipoLancamentoService;

    @PostMapping("/tipos-lancamento")
    public ResponseEntity<TipoLancamentoDTO> createTipoLancamento(@Valid @RequestBody TipoLancamentoDTO tipoLancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoLancamento : {}", tipoLancamentoDTO);
        if (tipoLancamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoLancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoLancamentoDTO result = tipoLancamentoService.save(tipoLancamentoDTO);
        return ResponseEntity.created(new URI("/api/tipos-lancamento/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tipos-lancamento")
    public ResponseEntity<TipoLancamentoDTO> updateTipoLancamento(@Valid @RequestBody TipoLancamentoDTO tipoLancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoLancamento : {}", tipoLancamentoDTO);
        if (tipoLancamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoLancamentoDTO result = tipoLancamentoService.save(tipoLancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoLancamentoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/tipos-lancamento")
    public ResponseEntity<List<TipoLancamentoDTO>> getAllTipoLancamentos(Pageable pageable) {
        log.debug("REST request to get a page of TipoLancamentos");
        Page<TipoLancamentoDTO> page = tipoLancamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tipos-lancamento/{id}")
    public ResponseEntity<TipoLancamentoDTO> getTipoLancamento(@PathVariable Long id) {
        log.debug("REST request to get TipoLancamento : {}", id);
        Optional<TipoLancamentoDTO> tipoLancamentoDTO = tipoLancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoLancamentoDTO);
    }

    @DeleteMapping("/tipos-lancamento/{id}")
    public ResponseEntity<Void> deleteTipoLancamento(@PathVariable Long id) {
        log.debug("REST request to delete TipoLancamento : {}", id);
        tipoLancamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/tipos-lancamento")
    public ResponseEntity<List<TipoLancamentoDTO>> searchTipoLancamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoLancamentos for query {}", query);
        Page<TipoLancamentoDTO> page = tipoLancamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
