package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.SazonalidadeService;
import br.com.basis.suprimentos.service.dto.SazonalidadeDTO;
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
public class SazonalidadeResource {
    private static final String ENTITY_NAME = "madresuprimentosSazonalidade";
    private final SazonalidadeService sazonalidadeService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/sazonalidades")
    public ResponseEntity<SazonalidadeDTO> createSazonalidade(@Valid @RequestBody SazonalidadeDTO sazonalidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Sazonalidade : {}", sazonalidadeDTO);
        if (sazonalidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sazonalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SazonalidadeDTO result = sazonalidadeService.save(sazonalidadeDTO);
        return ResponseEntity.created(new URI("/api/sazonalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/sazonalidades")
    public ResponseEntity<SazonalidadeDTO> updateSazonalidade(@Valid @RequestBody SazonalidadeDTO sazonalidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Sazonalidade : {}", sazonalidadeDTO);
        if (sazonalidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SazonalidadeDTO result = sazonalidadeService.save(sazonalidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sazonalidadeDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/sazonalidades")
    public ResponseEntity<List<SazonalidadeDTO>> getAllSazonalidades(Pageable pageable) {
        log.debug("REST request to get a page of Sazonalidades");
        Page<SazonalidadeDTO> page = sazonalidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/sazonalidades/{id}")
    public ResponseEntity<SazonalidadeDTO> getSazonalidade(@PathVariable Long id) {
        log.debug("REST request to get Sazonalidade : {}", id);
        Optional<SazonalidadeDTO> sazonalidadeDTO = sazonalidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sazonalidadeDTO);
    }

    @DeleteMapping("/sazonalidades/{id}")
    public ResponseEntity<Void> deleteSazonalidade(@PathVariable Long id) {
        log.debug("REST request to delete Sazonalidade : {}", id);
        sazonalidadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/sazonalidades")
    public ResponseEntity<List<SazonalidadeDTO>> searchSazonalidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sazonalidades for query {}", query);
        Page<SazonalidadeDTO> page = sazonalidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
