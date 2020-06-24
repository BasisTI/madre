package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.GrupoMaterialService;
import br.com.basis.suprimentos.service.dto.GrupoMaterialDTO;
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
public class GrupoMaterialResource {
    private static final String ENTITY_NAME = "madresuprimentosGrupoMaterial";
    private final GrupoMaterialService grupoMaterialService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/grupos-materiais")
    public ResponseEntity<GrupoMaterialDTO> createGrupoMaterial(@Valid @RequestBody GrupoMaterialDTO grupoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoMaterial : {}", grupoMaterialDTO);
        if (grupoMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoMaterialDTO result = grupoMaterialService.save(grupoMaterialDTO);
        return ResponseEntity.created(new URI("/api/grupos-materiais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/grupos-materiais")
    public ResponseEntity<GrupoMaterialDTO> updateGrupoMaterial(@Valid @RequestBody GrupoMaterialDTO grupoMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update GrupoMaterial : {}", grupoMaterialDTO);
        if (grupoMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoMaterialDTO result = grupoMaterialService.save(grupoMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoMaterialDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/grupos-materiais")
    public ResponseEntity<List<GrupoMaterialDTO>> getAllGrupoMaterials(Pageable pageable) {
        log.debug("REST request to get a page of GrupoMaterials");
        Page<GrupoMaterialDTO> page = grupoMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/grupos-materiais/{id}")
    public ResponseEntity<GrupoMaterialDTO> getGrupoMaterial(@PathVariable Long id) {
        log.debug("REST request to get GrupoMaterial : {}", id);
        Optional<GrupoMaterialDTO> grupoMaterialDTO = grupoMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoMaterialDTO);
    }

    @DeleteMapping("/grupos-materiais/{id}")
    public ResponseEntity<Void> deleteGrupoMaterial(@PathVariable Long id) {
        log.debug("REST request to delete GrupoMaterial : {}", id);
        grupoMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/grupos-materiais")
    public ResponseEntity<List<GrupoMaterialDTO>> searchGrupoMaterials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrupoMaterials for query {}", query);
        Page<GrupoMaterialDTO> page = grupoMaterialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
