package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.LoteService;
import br.com.basis.suprimentos.service.dto.LoteDTO;
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
public class LoteResource {
    private static final String ENTITY_NAME = "madresuprimentosLote";
    private final LoteService loteService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/lotes")
    public ResponseEntity<LoteDTO> createLote(@Valid @RequestBody LoteDTO loteDTO) throws URISyntaxException {
        log.debug("REST request to save Lote : {}", loteDTO);
        if (loteDTO.getId() != null) {
            throw new BadRequestAlertException("A new lote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoteDTO result = loteService.save(loteDTO);
        return ResponseEntity.created(new URI("/api/lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/lotes")
    public ResponseEntity<LoteDTO> updateLote(@Valid @RequestBody LoteDTO loteDTO) throws URISyntaxException {
        log.debug("REST request to update Lote : {}", loteDTO);
        if (loteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoteDTO result = loteService.save(loteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loteDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/lotes")
    public ResponseEntity<List<LoteDTO>> getAllLotes(Pageable pageable) {
        log.debug("REST request to get a page of Lotes");
        Page<LoteDTO> page = loteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/lotes/{id}")
    public ResponseEntity<LoteDTO> getLote(@PathVariable Long id) {
        log.debug("REST request to get Lote : {}", id);
        Optional<LoteDTO> loteDTO = loteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loteDTO);
    }

    @DeleteMapping("/lotes/{id}")
    public ResponseEntity<Void> deleteLote(@PathVariable Long id) {
        log.debug("REST request to delete Lote : {}", id);
        loteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/lotes")
    public ResponseEntity<List<LoteDTO>> searchLotes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lotes for query {}", query);
        Page<LoteDTO> page = loteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
