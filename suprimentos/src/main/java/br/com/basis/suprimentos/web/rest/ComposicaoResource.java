package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.ComposicaoService;
import br.com.basis.suprimentos.service.dto.ComposicaoDTO;
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
public class ComposicaoResource {

    private static final String ENTITY_NAME = "madresuprimentosComposicao";
    private final ComposicaoService composicaoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/composicoes")
    public ResponseEntity<ComposicaoDTO> createComposicao(@Valid @RequestBody ComposicaoDTO composicaoDTO) throws URISyntaxException {
        log.debug("REST request to save Composicao : {}", composicaoDTO);
        if (composicaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new composicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComposicaoDTO result = composicaoService.save(composicaoDTO);
        return ResponseEntity.created(new URI("/api/composicoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/composicoes")
    public ResponseEntity<ComposicaoDTO> updateComposicao(@Valid @RequestBody ComposicaoDTO composicaoDTO) throws URISyntaxException {
        log.debug("REST request to update Composicao : {}", composicaoDTO);
        if (composicaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComposicaoDTO result = composicaoService.save(composicaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, composicaoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/composicoes")
    public ResponseEntity<List<ComposicaoDTO>> getAllComposicaos(Pageable pageable) {
        log.debug("REST request to get a page of Composicaos");
        Page<ComposicaoDTO> page = composicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/composicoes/{id}")
    public ResponseEntity<ComposicaoDTO> getComposicao(@PathVariable Long id) {
        log.debug("REST request to get Composicao : {}", id);
        Optional<ComposicaoDTO> composicaoDTO = composicaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(composicaoDTO);
    }

    @DeleteMapping("/composicoes/{id}")
    public ResponseEntity<Void> deleteComposicao(@PathVariable Long id) {
        log.debug("REST request to delete Composicao : {}", id);
        composicaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/composicoes")
    public ResponseEntity<List<ComposicaoDTO>> searchComposicaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Composicaos for query {}", query);
        Page<ComposicaoDTO> page = composicaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
