package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TransferenciaAlmoxarifadoService;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
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
public class TransferenciaAlmoxarifadoResource {

    private static final String ENTITY_NAME = "madresuprimentosTransferenciaAlmoxarifado";
    private final TransferenciaAlmoxarifadoService transferenciaAlmoxarifadoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/transferencias-almoxarifados")
    public ResponseEntity<TransferenciaAlmoxarifadoDTO> createTransferenciaAlmoxarifado(@Valid @RequestBody TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to save TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        if (transferenciaAlmoxarifadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferenciaAlmoxarifado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoService.save(transferenciaAlmoxarifadoDTO);
        return ResponseEntity.created(new URI("/api/transferencias-almoxarifados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/transferencias-almoxarifados")
    public ResponseEntity<TransferenciaAlmoxarifadoDTO> updateTransferenciaAlmoxarifado(@Valid @RequestBody TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to update TransferenciaAlmoxarifado : {}", transferenciaAlmoxarifadoDTO);
        if (transferenciaAlmoxarifadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransferenciaAlmoxarifadoDTO result = transferenciaAlmoxarifadoService.save(transferenciaAlmoxarifadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transferenciaAlmoxarifadoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/transferencias-almoxarifados")
    public ResponseEntity<List<TransferenciaAlmoxarifadoDTO>> getAllTransferenciaAlmoxarifados(Pageable pageable) {
        log.debug("REST request to get a page of TransferenciaAlmoxarifados");
        Page<TransferenciaAlmoxarifadoDTO> page = transferenciaAlmoxarifadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/transferencias-almoxarifados/{id}")
    public ResponseEntity<TransferenciaAlmoxarifadoDTO> getTransferenciaAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to get TransferenciaAlmoxarifado : {}", id);
        Optional<TransferenciaAlmoxarifadoDTO> transferenciaAlmoxarifadoDTO = transferenciaAlmoxarifadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferenciaAlmoxarifadoDTO);
    }

    @DeleteMapping("/transferencias-almoxarifados/{id}")
    public ResponseEntity<Void> deleteTransferenciaAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to delete TransferenciaAlmoxarifado : {}", id);
        transferenciaAlmoxarifadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/transferencias-almoxarifados")
    public ResponseEntity<List<TransferenciaAlmoxarifadoDTO>> searchTransferenciaAlmoxarifados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TransferenciaAlmoxarifados for query {}", query);
        Page<TransferenciaAlmoxarifadoDTO> page = transferenciaAlmoxarifadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
