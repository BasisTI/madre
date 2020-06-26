package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.MarcaComercialService;
import br.com.basis.suprimentos.service.dto.MarcaComercialDTO;
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
public class MarcaComercialResource {
    private static final String ENTITY_NAME = "madresuprimentosMarcaComercial";
    private final MarcaComercialService marcaComercialService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/marcas-comerciais")
    public ResponseEntity<MarcaComercialDTO> createMarcaComercial(@Valid @RequestBody MarcaComercialDTO marcaComercialDTO) throws URISyntaxException {
        log.debug("REST request to save MarcaComercial : {}", marcaComercialDTO);
        if (marcaComercialDTO.getId() != null) {
            throw new BadRequestAlertException("A new marcaComercial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarcaComercialDTO result = marcaComercialService.save(marcaComercialDTO);
        return ResponseEntity.created(new URI("/api/marcas-comerciais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/marcas-comerciais")
    public ResponseEntity<MarcaComercialDTO> updateMarcaComercial(@Valid @RequestBody MarcaComercialDTO marcaComercialDTO) throws URISyntaxException {
        log.debug("REST request to update MarcaComercial : {}", marcaComercialDTO);
        if (marcaComercialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MarcaComercialDTO result = marcaComercialService.save(marcaComercialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marcaComercialDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/marcas-comerciais")
    public ResponseEntity<List<MarcaComercialDTO>> getAllMarcaComercials(Pageable pageable) {
        log.debug("REST request to get a page of MarcaComercials");
        Page<MarcaComercialDTO> page = marcaComercialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/marcas-comerciais/{id}")
    public ResponseEntity<MarcaComercialDTO> getMarcaComercial(@PathVariable Long id) {
        log.debug("REST request to get MarcaComercial : {}", id);
        Optional<MarcaComercialDTO> marcaComercialDTO = marcaComercialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marcaComercialDTO);
    }

    @DeleteMapping("/marcas-comerciais/{id}")
    public ResponseEntity<Void> deleteMarcaComercial(@PathVariable Long id) {
        log.debug("REST request to delete MarcaComercial : {}", id);
        marcaComercialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/marcas-comerciais")
    public ResponseEntity<List<MarcaComercialDTO>> searchMarcaComercials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MarcaComercials for query {}", query);
        Page<MarcaComercialDTO> page = marcaComercialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
