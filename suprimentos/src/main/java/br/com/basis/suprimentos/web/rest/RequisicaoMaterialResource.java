package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.domain.projection.RequisicaoMaterialResumo;
import br.com.basis.suprimentos.service.RequisicaoMaterialService;
import br.com.basis.suprimentos.service.dto.RequisicaoMaterialDTO;
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
public class RequisicaoMaterialResource {
    private static final String ENTITY_NAME = "madresuprimentosRequisicaoMaterial";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final RequisicaoMaterialService requisicaoMaterialService;

    @PostMapping("/requisicoes-materiais")
    public ResponseEntity<RequisicaoMaterialDTO> createRequisicaoMaterial(@Valid @RequestBody RequisicaoMaterialDTO requisicaoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save RequisicaoMaterial : {}", requisicaoMaterialDTO);
        if (requisicaoMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisicaoMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisicaoMaterialDTO result = requisicaoMaterialService.criarRequisicaoMaterial(requisicaoMaterialDTO);
        return ResponseEntity.created(new URI("/api/requisicoes-materiais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/requisicoes-materiais")
    public ResponseEntity<RequisicaoMaterialDTO> updateRequisicaoMaterial(@Valid @RequestBody RequisicaoMaterialDTO requisicaoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update RequisicaoMaterial : {}", requisicaoMaterialDTO);
        if (requisicaoMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisicaoMaterialDTO result = requisicaoMaterialService.save(requisicaoMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requisicaoMaterialDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/requisicoes-materiais")
    public ResponseEntity<List<RequisicaoMaterialResumo>> getAllRequisicaoMaterials(Pageable pageable) {
        log.debug("REST request to get a page of RequisicaoMaterials");
        Page<RequisicaoMaterialResumo> page = requisicaoMaterialService.findAllRequisicoesMaterialResumo(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/requisicoes-materiais/{id}")
    public ResponseEntity<RequisicaoMaterialDTO> getRequisicaoMaterial(@PathVariable Long id) {
        log.debug("REST request to get RequisicaoMaterial : {}", id);
        Optional<RequisicaoMaterialDTO> requisicaoMaterialDTO = requisicaoMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisicaoMaterialDTO);
    }

    @DeleteMapping("/requisicoes-materiais/{id}")
    public ResponseEntity<Void> deleteRequisicaoMaterial(@PathVariable Long id) {
        log.debug("REST request to delete RequisicaoMaterial : {}", id);
        requisicaoMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/requisicoes-materiais")
    public ResponseEntity<List<RequisicaoMaterialDTO>> searchRequisicaoMaterials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RequisicaoMaterials for query {}", query);
        Page<RequisicaoMaterialDTO> page = requisicaoMaterialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
