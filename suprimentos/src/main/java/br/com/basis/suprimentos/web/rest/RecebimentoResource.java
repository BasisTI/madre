package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.domain.projection.RecebimentoProvisorio;
import br.com.basis.suprimentos.service.RecebimentoService;
import br.com.basis.suprimentos.service.dto.RecebimentoDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RecebimentoResource {
    private static final String ENTITY_NAME = "madresuprimentosRecebimento";
    private final RecebimentoService recebimentoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @GetMapping("/recebimentos-provisorios")
    public ResponseEntity<List<RecebimentoProvisorio>> getAllRecebimentosProvisorios(Pageable pageable, RecebimentoDTO recebimentoDTO) {
        final Page<RecebimentoProvisorio> page = recebimentoService.findAllRecebimentosProvisorios(pageable, recebimentoDTO);
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/recebimentos")
    public ResponseEntity<RecebimentoDTO> createRecebimento(@RequestBody RecebimentoDTO recebimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Recebimento : {}", recebimentoDTO);
        if (recebimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new recebimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecebimentoDTO result = recebimentoService.save(recebimentoDTO);
        return ResponseEntity.created(new URI("/api/recebimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/recebimentos")
    public ResponseEntity<RecebimentoDTO> updateRecebimento(@RequestBody RecebimentoDTO recebimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Recebimento : {}", recebimentoDTO);
        if (recebimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecebimentoDTO result = recebimentoService.save(recebimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recebimentoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/recebimentos")
    public ResponseEntity<List<RecebimentoDTO>> getAllRecebimentos(Pageable pageable) {
        log.debug("REST request to get a page of Recebimentos");
        Page<RecebimentoDTO> page = recebimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/recebimentos/{id}")
    public ResponseEntity<RecebimentoDTO> getRecebimento(@PathVariable Long id) {
        log.debug("REST request to get Recebimento : {}", id);
        Optional<RecebimentoDTO> recebimentoDTO = recebimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recebimentoDTO);
    }

    @DeleteMapping("/recebimentos/{id}")
    public ResponseEntity<Void> deleteRecebimento(@PathVariable Long id) {
        log.debug("REST request to delete Recebimento : {}", id);
        recebimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/recebimentos")
    public ResponseEntity<List<RecebimentoDTO>> searchRecebimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recebimentos for query {}", query);
        Page<RecebimentoDTO> page = recebimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
