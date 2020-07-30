package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.UnidadeMedidaService;
import br.com.basis.suprimentos.service.dto.UnidadeMedidaDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UnidadeMedidaResource {
    private static final String ENTITY_NAME = "madresuprimentosUnidadeMedida";
    private final UnidadeMedidaService unidadeMedidaService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/unidades-medida")
    public ResponseEntity<UnidadeMedidaDTO> createUnidadeMedida(@Valid @RequestBody UnidadeMedidaDTO unidadeMedidaDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadeMedida : {}", unidadeMedidaDTO);
        if (unidadeMedidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadeMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeMedidaDTO result = unidadeMedidaService.save(unidadeMedidaDTO);
        return ResponseEntity.created(new URI("/api/unidades-medida/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/unidades-medida")
    public ResponseEntity<UnidadeMedidaDTO> updateUnidadeMedida(@Valid @RequestBody UnidadeMedidaDTO unidadeMedidaDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadeMedida : {}", unidadeMedidaDTO);
        if (unidadeMedidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeMedidaDTO result = unidadeMedidaService.save(unidadeMedidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadeMedidaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/unidades-medida")
    public ResponseEntity<List<UnidadeMedidaDTO>> getAllUnidadeMedidas(Pageable pageable, UnidadeMedidaDTO unidadeMedidaDTO) {
        log.debug("REST request to get a page of UnidadeMedidas");
        Page<UnidadeMedidaDTO> page = unidadeMedidaService.findAll(pageable, unidadeMedidaDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/unidades-medida/{id}")
    public ResponseEntity<UnidadeMedidaDTO> getUnidadeMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadeMedida : {}", id);
        Optional<UnidadeMedidaDTO> unidadeMedidaDTO = unidadeMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeMedidaDTO);
    }

    @DeleteMapping("/unidades-medida/{id}")
    public ResponseEntity<Void> deleteUnidadeMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeMedida : {}", id);
        unidadeMedidaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/unidades-medida")
    public ResponseEntity<List<UnidadeMedidaDTO>> searchUnidadeMedidas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeMedidas for query {}", query);
        Page<UnidadeMedidaDTO> page = unidadeMedidaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
