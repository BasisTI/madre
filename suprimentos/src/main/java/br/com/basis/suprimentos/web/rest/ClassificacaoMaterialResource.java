package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.ClassificacaoMaterialService;
import br.com.basis.suprimentos.service.dto.ClassificacaoMaterialDTO;
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
public class ClassificacaoMaterialResource {
    private static final String ENTITY_NAME = "madresuprimentosClassificacaoMaterial";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final ClassificacaoMaterialService classificacaoMaterialService;

    @PostMapping("/classificacoes-materiais")
    public ResponseEntity<ClassificacaoMaterialDTO> createClassificacaoMaterial(@Valid @RequestBody ClassificacaoMaterialDTO classificacaoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save ClassificacaoMaterial : {}", classificacaoMaterialDTO);
        if (classificacaoMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new classificacaoMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassificacaoMaterialDTO result = classificacaoMaterialService.save(classificacaoMaterialDTO);
        return ResponseEntity.created(new URI("/api/classificacoes-materiais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/classificacoes-materiais")
    public ResponseEntity<ClassificacaoMaterialDTO> updateClassificacaoMaterial(@Valid @RequestBody ClassificacaoMaterialDTO classificacaoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update ClassificacaoMaterial : {}", classificacaoMaterialDTO);
        if (classificacaoMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassificacaoMaterialDTO result = classificacaoMaterialService.save(classificacaoMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classificacaoMaterialDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/classificacoes-materiais")
    public ResponseEntity<List<ClassificacaoMaterialDTO>> getAllClassificacaoMaterials(Pageable pageable) {
        log.debug("REST request to get a page of ClassificacaoMaterials");
        Page<ClassificacaoMaterialDTO> page = classificacaoMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/classificacoes-materiais/{id}")
    public ResponseEntity<ClassificacaoMaterialDTO> getClassificacaoMaterial(@PathVariable Long id) {
        log.debug("REST request to get ClassificacaoMaterial : {}", id);
        Optional<ClassificacaoMaterialDTO> classificacaoMaterialDTO = classificacaoMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classificacaoMaterialDTO);
    }

    @DeleteMapping("/classificacoes-materiais/{id}")
    public ResponseEntity<Void> deleteClassificacaoMaterial(@PathVariable Long id) {
        log.debug("REST request to delete ClassificacaoMaterial : {}", id);
        classificacaoMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/classificacoes-materiais")
    public ResponseEntity<List<ClassificacaoMaterialDTO>> searchClassificacaoMaterials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ClassificacaoMaterials for query {}", query);
        Page<ClassificacaoMaterialDTO> page = classificacaoMaterialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
