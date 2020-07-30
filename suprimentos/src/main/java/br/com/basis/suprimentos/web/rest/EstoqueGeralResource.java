package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.EstoqueGeralService;
import br.com.basis.suprimentos.service.dto.EstoqueGeralDTO;
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
public class EstoqueGeralResource {
    private static final String ENTITY_NAME = "madresuprimentosEstoqueGeral";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final EstoqueGeralService estoqueGeralService;

    @PostMapping("/estoques-gerais/saldo")
    public ResponseEntity<EstoqueGeralDTO> incluirSaldoEstoque(@RequestBody @Valid InclusaoSaldoEstoqueDTO inclusaoSaldoEstoqueDTO) {
        return ResponseEntity.ok(estoqueGeralService.incluirSaldoEstoque(inclusaoSaldoEstoqueDTO));
    }

    @PostMapping("/estoques-gerais")
    public ResponseEntity<EstoqueGeralDTO> createEstoqueGeral(@Valid @RequestBody EstoqueGeralDTO estoqueGeralDTO) throws URISyntaxException {
        log.debug("REST request to save EstoqueGeral : {}", estoqueGeralDTO);
        if (estoqueGeralDTO.getId() != null) {
            throw new BadRequestAlertException("A new estoqueGeral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstoqueGeralDTO result = estoqueGeralService.save(estoqueGeralDTO);
        return ResponseEntity.created(new URI("/api/estoques-gerais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/estoques-gerais")
    public ResponseEntity<EstoqueGeralDTO> updateEstoqueGeral(@Valid @RequestBody EstoqueGeralDTO estoqueGeralDTO) throws URISyntaxException {
        log.debug("REST request to update EstoqueGeral : {}", estoqueGeralDTO);
        if (estoqueGeralDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstoqueGeralDTO result = estoqueGeralService.save(estoqueGeralDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estoqueGeralDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/estoques-gerais")
    public ResponseEntity<List<EstoqueGeralDTO>> getAllEstoqueGerals(Pageable pageable) {
        log.debug("REST request to get a page of EstoqueGerals");
        Page<EstoqueGeralDTO> page = estoqueGeralService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/estoques-gerais/{id}")
    public ResponseEntity<EstoqueGeralDTO> getEstoqueGeral(@PathVariable Long id) {
        log.debug("REST request to get EstoqueGeral : {}", id);
        Optional<EstoqueGeralDTO> estoqueGeralDTO = estoqueGeralService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estoqueGeralDTO);
    }

    @DeleteMapping("/estoques-gerais/{id}")
    public ResponseEntity<Void> deleteEstoqueGeral(@PathVariable Long id) {
        log.debug("REST request to delete EstoqueGeral : {}", id);
        estoqueGeralService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/estoques-gerais")
    public ResponseEntity<List<EstoqueGeralDTO>> searchEstoqueGerals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EstoqueGerals for query {}", query);
        Page<EstoqueGeralDTO> page = estoqueGeralService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
