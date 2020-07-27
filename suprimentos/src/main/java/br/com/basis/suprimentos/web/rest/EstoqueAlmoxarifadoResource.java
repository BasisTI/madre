package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.domain.projection.Estoque;
import br.com.basis.suprimentos.service.EstoqueAlmoxarifadoService;
import br.com.basis.suprimentos.service.dto.ConsultaEstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.EstoqueAlmoxarifadoDTO;
import br.com.basis.suprimentos.service.dto.InclusaoSaldoEstoqueDTO;
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
public class EstoqueAlmoxarifadoResource {
    private static final String ENTITY_NAME = "madresuprimentosEstoqueAlmoxarifado";
    private final EstoqueAlmoxarifadoService estoqueAlmoxarifadoService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostMapping("/estoque-almoxarifados")
    public ResponseEntity<EstoqueAlmoxarifadoDTO> createEstoqueAlmoxarifado(@Valid @RequestBody EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to save EstoqueAlmoxarifado : {}", estoqueAlmoxarifadoDTO);
        if (estoqueAlmoxarifadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estoqueAlmoxarifado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstoqueAlmoxarifadoDTO result = estoqueAlmoxarifadoService.save(estoqueAlmoxarifadoDTO);
        return ResponseEntity.created(new URI("/api/estoque-almoxarifados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/estoque-almoxarifados")
    public ResponseEntity<EstoqueAlmoxarifadoDTO> updateEstoqueAlmoxarifado(@Valid @RequestBody EstoqueAlmoxarifadoDTO estoqueAlmoxarifadoDTO) throws URISyntaxException {
        log.debug("REST request to update EstoqueAlmoxarifado : {}", estoqueAlmoxarifadoDTO);
        if (estoqueAlmoxarifadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstoqueAlmoxarifadoDTO result = estoqueAlmoxarifadoService.save(estoqueAlmoxarifadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estoqueAlmoxarifadoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/estoque-almoxarifados")
    public ResponseEntity<List<Estoque>> getAllEstoqueAlmoxarifados(Pageable pageable, ConsultaEstoqueAlmoxarifadoDTO consultaEstoqueAlmoxarifadoDTO) {
        log.debug("REST request to get a page of EstoqueAlmoxarifados");
        Page<Estoque> page = estoqueAlmoxarifadoService.consultarEstoqueAlmoxarifado(pageable, consultaEstoqueAlmoxarifadoDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/estoque-almoxarifados/{id}")
    public ResponseEntity<EstoqueAlmoxarifadoDTO> getEstoqueAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to get EstoqueAlmoxarifado : {}", id);
        Optional<EstoqueAlmoxarifadoDTO> estoqueAlmoxarifadoDTO = estoqueAlmoxarifadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estoqueAlmoxarifadoDTO);
    }

    @DeleteMapping("/estoque-almoxarifados/{id}")
    public ResponseEntity<Void> deleteEstoqueAlmoxarifado(@PathVariable Long id) {
        log.debug("REST request to delete EstoqueAlmoxarifado : {}", id);
        estoqueAlmoxarifadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/estoque-almoxarifados")
    public ResponseEntity<List<EstoqueAlmoxarifadoDTO>> searchEstoqueAlmoxarifados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EstoqueAlmoxarifados for query {}", query);
        Page<EstoqueAlmoxarifadoDTO> page = estoqueAlmoxarifadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
