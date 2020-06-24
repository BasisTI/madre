package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.CodigoCatmatService;
import br.com.basis.suprimentos.service.dto.CodigoCatmatDTO;
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
public class CodigoCatmatResource {
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "madresuprimentosCodigoCatmat";
    private final CodigoCatmatService codigoCatmatService;

    @PostMapping("/codigos-catmat")
    public ResponseEntity<CodigoCatmatDTO> createCodigoCatmat(@Valid @RequestBody CodigoCatmatDTO codigoCatmatDTO) throws URISyntaxException {
        log.debug("REST request to save CodigoCatmat : {}", codigoCatmatDTO);
        if (codigoCatmatDTO.getId() != null) {
            throw new BadRequestAlertException("A new codigoCatmat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodigoCatmatDTO result = codigoCatmatService.save(codigoCatmatDTO);
        return ResponseEntity.created(new URI("/api/codigos-catmat/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/codigos-catmat")
    public ResponseEntity<CodigoCatmatDTO> updateCodigoCatmat(@Valid @RequestBody CodigoCatmatDTO codigoCatmatDTO) throws URISyntaxException {
        log.debug("REST request to update CodigoCatmat : {}", codigoCatmatDTO);
        if (codigoCatmatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodigoCatmatDTO result = codigoCatmatService.save(codigoCatmatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codigoCatmatDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/codigos-catmat")
    public ResponseEntity<List<CodigoCatmatDTO>> getAllCodigoCatmats(Pageable pageable) {
        log.debug("REST request to get a page of CodigoCatmats");
        Page<CodigoCatmatDTO> page = codigoCatmatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/codigos-catmat/{id}")
    public ResponseEntity<CodigoCatmatDTO> getCodigoCatmat(@PathVariable Long id) {
        log.debug("REST request to get CodigoCatmat : {}", id);
        Optional<CodigoCatmatDTO> codigoCatmatDTO = codigoCatmatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codigoCatmatDTO);
    }

    @DeleteMapping("/codigos-catmat/{id}")
    public ResponseEntity<Void> deleteCodigoCatmat(@PathVariable Long id) {
        log.debug("REST request to delete CodigoCatmat : {}", id);
        codigoCatmatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/codigos-catmat")
    public ResponseEntity<List<CodigoCatmatDTO>> searchCodigoCatmats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CodigoCatmats for query {}", query);
        Page<CodigoCatmatDTO> page = codigoCatmatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
