package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.MaterialService;
import br.com.basis.suprimentos.service.dto.MaterialDTO;
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
public class MaterialResource {
    private static final String ENTITY_NAME = "madresuprimentosMaterial";
    private final MaterialService materialService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/materiais")
    public ResponseEntity<MaterialDTO> createMaterial(@Valid @RequestBody MaterialDTO materialDTO) throws URISyntaxException {
        log.debug("REST request to save Material : {}", materialDTO);
        if (materialDTO.getId() != null) {
            throw new BadRequestAlertException("A new material cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialDTO result = materialService.save(materialDTO);
        return ResponseEntity.created(new URI("/api/materiais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/materiais")
    public ResponseEntity<MaterialDTO> updateMaterial(@Valid @RequestBody MaterialDTO materialDTO) throws URISyntaxException {
        log.debug("REST request to update Material : {}", materialDTO);
        if (materialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MaterialDTO result = materialService.save(materialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, materialDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/materiais")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials(Pageable pageable) {
        log.debug("REST request to get a page of Materials");
        Page<MaterialDTO> page = materialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/materiais/{id}")
    public ResponseEntity<MaterialDTO> getMaterial(@PathVariable Long id) {
        log.debug("REST request to get Material : {}", id);
        Optional<MaterialDTO> materialDTO = materialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materialDTO);
    }

    @DeleteMapping("/materiais/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        log.debug("REST request to delete Material : {}", id);
        materialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/materiais")
    public ResponseEntity<List<MaterialDTO>> searchMaterials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Materials for query {}", query);
        Page<MaterialDTO> page = materialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
