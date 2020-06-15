package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TempoPorClasseService;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;
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
public class TempoPorClasseResource {

    private static final String ENTITY_NAME = "madresuprimentosTempoPorClasse";
    private final TempoPorClasseService tempoPorClasseService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/tempos-por-classes")
    public ResponseEntity<TempoPorClasseDTO> createTempoPorClasse(@Valid @RequestBody TempoPorClasseDTO tempoPorClasseDTO) throws URISyntaxException {
        log.debug("REST request to save TempoPorClasse : {}", tempoPorClasseDTO);
        if (tempoPorClasseDTO.getId() != null) {
            throw new BadRequestAlertException("A new tempoPorClasse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TempoPorClasseDTO result = tempoPorClasseService.save(tempoPorClasseDTO);
        return ResponseEntity.created(new URI("/api/tempo-por-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tempos-por-classes")
    public ResponseEntity<TempoPorClasseDTO> updateTempoPorClasse(@Valid @RequestBody TempoPorClasseDTO tempoPorClasseDTO) throws URISyntaxException {
        log.debug("REST request to update TempoPorClasse : {}", tempoPorClasseDTO);
        if (tempoPorClasseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TempoPorClasseDTO result = tempoPorClasseService.save(tempoPorClasseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tempoPorClasseDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/tempos-por-classes")
    public ResponseEntity<List<TempoPorClasseDTO>> getAllTempoPorClasses(Pageable pageable) {
        log.debug("REST request to get a page of TempoPorClasses");
        Page<TempoPorClasseDTO> page = tempoPorClasseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tempos-por-classes/{id}")
    public ResponseEntity<TempoPorClasseDTO> getTempoPorClasse(@PathVariable Long id) {
        log.debug("REST request to get TempoPorClasse : {}", id);
        Optional<TempoPorClasseDTO> tempoPorClasseDTO = tempoPorClasseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tempoPorClasseDTO);
    }

    @DeleteMapping("/tempos-por-classes/{id}")
    public ResponseEntity<Void> deleteTempoPorClasse(@PathVariable Long id) {
        log.debug("REST request to delete TempoPorClasse : {}", id);
        tempoPorClasseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/tempos-por-classes")
    public ResponseEntity<List<TempoPorClasseDTO>> searchTempoPorClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TempoPorClasses for query {}", query);
        Page<TempoPorClasseDTO> page = tempoPorClasseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
