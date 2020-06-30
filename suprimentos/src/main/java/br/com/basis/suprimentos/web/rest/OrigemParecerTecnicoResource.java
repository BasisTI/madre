package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.OrigemParecerTecnicoService;
import br.com.basis.suprimentos.service.dto.OrigemParecerTecnicoDTO;
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
public class OrigemParecerTecnicoResource {
    private static final String ENTITY_NAME = "madresuprimentosOrigemParecerTecnico";
    private final OrigemParecerTecnicoService origemParecerTecnicoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/origem-parecer-tecnicos")
    public ResponseEntity<OrigemParecerTecnicoDTO> createOrigemParecerTecnico(@Valid @RequestBody OrigemParecerTecnicoDTO origemParecerTecnicoDTO) throws URISyntaxException {
        log.debug("REST request to save OrigemParecerTecnico : {}", origemParecerTecnicoDTO);
        if (origemParecerTecnicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new origemParecerTecnico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrigemParecerTecnicoDTO result = origemParecerTecnicoService.save(origemParecerTecnicoDTO);
        return ResponseEntity.created(new URI("/api/origem-parecer-tecnicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/origem-parecer-tecnicos")
    public ResponseEntity<OrigemParecerTecnicoDTO> updateOrigemParecerTecnico(@Valid @RequestBody OrigemParecerTecnicoDTO origemParecerTecnicoDTO) throws URISyntaxException {
        log.debug("REST request to update OrigemParecerTecnico : {}", origemParecerTecnicoDTO);
        if (origemParecerTecnicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrigemParecerTecnicoDTO result = origemParecerTecnicoService.save(origemParecerTecnicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, origemParecerTecnicoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/origem-parecer-tecnicos")
    public ResponseEntity<List<OrigemParecerTecnicoDTO>> getAllOrigemParecerTecnicos(Pageable pageable) {
        log.debug("REST request to get a page of OrigemParecerTecnicos");
        Page<OrigemParecerTecnicoDTO> page = origemParecerTecnicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/origem-parecer-tecnicos/{id}")
    public ResponseEntity<OrigemParecerTecnicoDTO> getOrigemParecerTecnico(@PathVariable Long id) {
        log.debug("REST request to get OrigemParecerTecnico : {}", id);
        Optional<OrigemParecerTecnicoDTO> origemParecerTecnicoDTO = origemParecerTecnicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(origemParecerTecnicoDTO);
    }

    @DeleteMapping("/origem-parecer-tecnicos/{id}")
    public ResponseEntity<Void> deleteOrigemParecerTecnico(@PathVariable Long id) {
        log.debug("REST request to delete OrigemParecerTecnico : {}", id);
        origemParecerTecnicoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/origem-parecer-tecnicos")
    public ResponseEntity<List<OrigemParecerTecnicoDTO>> searchOrigemParecerTecnicos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrigemParecerTecnicos for query {}", query);
        Page<OrigemParecerTecnicoDTO> page = origemParecerTecnicoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
