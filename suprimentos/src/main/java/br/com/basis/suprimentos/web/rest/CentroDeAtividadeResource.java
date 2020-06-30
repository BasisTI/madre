package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.CentroDeAtividadeService;
import br.com.basis.suprimentos.service.dto.CentroDeAtividadeDTO;
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
public class CentroDeAtividadeResource {
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "madresuprimentosCentroDeAtividade";
    private final CentroDeAtividadeService centroDeAtividadeService;

    @PostMapping("/centro-de-atividades")
    public ResponseEntity<CentroDeAtividadeDTO> createCentroDeAtividade(@Valid @RequestBody CentroDeAtividadeDTO centroDeAtividadeDTO) throws URISyntaxException {
        log.debug("REST request to save CentroDeAtividade : {}", centroDeAtividadeDTO);
        if (centroDeAtividadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new centroDeAtividade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentroDeAtividadeDTO result = centroDeAtividadeService.save(centroDeAtividadeDTO);
        return ResponseEntity.created(new URI("/api/centro-de-atividades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/centro-de-atividades")
    public ResponseEntity<CentroDeAtividadeDTO> updateCentroDeAtividade(@Valid @RequestBody CentroDeAtividadeDTO centroDeAtividadeDTO) throws URISyntaxException {
        log.debug("REST request to update CentroDeAtividade : {}", centroDeAtividadeDTO);
        if (centroDeAtividadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentroDeAtividadeDTO result = centroDeAtividadeService.save(centroDeAtividadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, centroDeAtividadeDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/centro-de-atividades")
    public ResponseEntity<List<CentroDeAtividadeDTO>> getAllCentroDeAtividades(Pageable pageable) {
        log.debug("REST request to get a page of CentroDeAtividades");
        Page<CentroDeAtividadeDTO> page = centroDeAtividadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/centro-de-atividades/{id}")
    public ResponseEntity<CentroDeAtividadeDTO> getCentroDeAtividade(@PathVariable Long id) {
        log.debug("REST request to get CentroDeAtividade : {}", id);
        Optional<CentroDeAtividadeDTO> centroDeAtividadeDTO = centroDeAtividadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroDeAtividadeDTO);
    }

    @DeleteMapping("/centro-de-atividades/{id}")
    public ResponseEntity<Void> deleteCentroDeAtividade(@PathVariable Long id) {
        log.debug("REST request to delete CentroDeAtividade : {}", id);
        centroDeAtividadeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/centro-de-atividades")
    public ResponseEntity<List<CentroDeAtividadeDTO>> searchCentroDeAtividades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CentroDeAtividades for query {}", query);
        Page<CentroDeAtividadeDTO> page = centroDeAtividadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
