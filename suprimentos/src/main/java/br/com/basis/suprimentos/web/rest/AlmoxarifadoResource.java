package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.AlmoxarifadoService;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;
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
public class AlmoxarifadoResource {

    private static final String ENTITY_NAME = "madresuprimentosAlmoxarifado";
    private final AlmoxarifadoService almoxarifadoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/almoxarifados")
    public ResponseEntity<AlmoxarifadoDTO> createAlmoxarifado(@Valid @RequestBody AlmoxarifadoDTO almoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to save Almoxarifado : {}", almoxarifadoDTO);
        if (almoxarifadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new almoxarifado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlmoxarifadoDTO result = almoxarifadoService.save(almoxarifadoDTO);
        return ResponseEntity.created(new URI("/api/almoxarifados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/almoxarifados")
    public ResponseEntity<AlmoxarifadoDTO> updateAlmoxarifado(@Valid @RequestBody AlmoxarifadoDTO almoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to update Almoxarifado : {}", almoxarifadoDTO);
        if (almoxarifadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlmoxarifadoDTO result = almoxarifadoService.save(almoxarifadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, almoxarifadoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/almoxarifados")
    public ResponseEntity<List<AlmoxarifadoDTO>> getAllAlmoxarifados(Pageable pageable) {
        log.debug("REST request to get a page of Almoxarifados");
        Page<AlmoxarifadoDTO> page = almoxarifadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/almoxarifados/{id}")
    public ResponseEntity<AlmoxarifadoDTO> getAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to get Almoxarifado : {}", id);
        Optional<AlmoxarifadoDTO> almoxarifadoDTO = almoxarifadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(almoxarifadoDTO);
    }

    @DeleteMapping("/almoxarifados/{id}")
    public ResponseEntity<Void> deleteAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to delete Almoxarifado : {}", id);
        almoxarifadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/almoxarifados")
    public ResponseEntity<List<AlmoxarifadoDTO>> searchAlmoxarifados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Almoxarifados for query {}", query);
        Page<AlmoxarifadoDTO> page = almoxarifadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
