package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.TipoResiduoService;
import br.com.basis.suprimentos.service.dto.TipoResiduoDTO;
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
public class TipoResiduoResource {
    private static final String ENTITY_NAME = "madresuprimentosTipoResiduo";
    private final TipoResiduoService tipoResiduoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/tipos-residuo")
    public ResponseEntity<TipoResiduoDTO> createTipoResiduo(@Valid @RequestBody TipoResiduoDTO tipoResiduoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoResiduo : {}", tipoResiduoDTO);
        if (tipoResiduoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoResiduo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoResiduoDTO result = tipoResiduoService.save(tipoResiduoDTO);
        return ResponseEntity.created(new URI("/api/tipos-residuo/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tipos-residuo")
    public ResponseEntity<TipoResiduoDTO> updateTipoResiduo(@Valid @RequestBody TipoResiduoDTO tipoResiduoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoResiduo : {}", tipoResiduoDTO);
        if (tipoResiduoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoResiduoDTO result = tipoResiduoService.save(tipoResiduoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoResiduoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/tipos-residuo")
    public ResponseEntity<List<TipoResiduoDTO>> getAllTipoResiduos(Pageable pageable) {
        log.debug("REST request to get a page of TipoResiduos");
        Page<TipoResiduoDTO> page = tipoResiduoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/tipos-residuo/{id}")
    public ResponseEntity<TipoResiduoDTO> getTipoResiduo(@PathVariable Long id) {
        log.debug("REST request to get TipoResiduo : {}", id);
        Optional<TipoResiduoDTO> tipoResiduoDTO = tipoResiduoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoResiduoDTO);
    }

    @DeleteMapping("/tipos-residuo/{id}")
    public ResponseEntity<Void> deleteTipoResiduo(@PathVariable Long id) {
        log.debug("REST request to delete TipoResiduo : {}", id);
        tipoResiduoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/tipos-residuo")
    public ResponseEntity<List<TipoResiduoDTO>> searchTipoResiduos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoResiduos for query {}", query);
        Page<TipoResiduoDTO> page = tipoResiduoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
