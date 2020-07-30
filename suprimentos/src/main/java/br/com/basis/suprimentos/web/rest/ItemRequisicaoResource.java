package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.ItemRequisicaoService;
import br.com.basis.suprimentos.service.dto.ItemRequisicaoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ItemRequisicaoResource {
    private static final String ENTITY_NAME = "madresuprimentosItemRequisicao";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final ItemRequisicaoService itemRequisicaoService;

    @PostMapping("/itens-requisicao")
    public ResponseEntity<ItemRequisicaoDTO> createItemRequisicao(@Valid @RequestBody ItemRequisicaoDTO itemRequisicaoDTO) throws URISyntaxException {
        log.debug("REST request to save ItemRequisicao : {}", itemRequisicaoDTO);
        if (itemRequisicaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemRequisicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemRequisicaoDTO result = itemRequisicaoService.save(itemRequisicaoDTO);
        return ResponseEntity.created(new URI("/api/itens-requisicao/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/itens-requisicao")
    public ResponseEntity<ItemRequisicaoDTO> updateItemRequisicao(@Valid @RequestBody ItemRequisicaoDTO itemRequisicaoDTO) throws URISyntaxException {
        log.debug("REST request to update ItemRequisicao : {}", itemRequisicaoDTO);
        if (itemRequisicaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemRequisicaoDTO result = itemRequisicaoService.save(itemRequisicaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemRequisicaoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/itens-requisicao")
    public ResponseEntity<List<ItemRequisicaoDTO>> getAllItemRequisicaos(Pageable pageable) {
        log.debug("REST request to get a page of ItemRequisicaos");
        Page<ItemRequisicaoDTO> page = itemRequisicaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/itens-requisicao/{id}")
    public ResponseEntity<ItemRequisicaoDTO> getItemRequisicao(@PathVariable Long id) {
        log.debug("REST request to get ItemRequisicao : {}", id);
        Optional<ItemRequisicaoDTO> itemRequisicaoDTO = itemRequisicaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemRequisicaoDTO);
    }

    @DeleteMapping("/itens-requisicao/{id}")
    public ResponseEntity<Void> deleteItemRequisicao(@PathVariable Long id) {
        log.debug("REST request to delete ItemRequisicao : {}", id);
        itemRequisicaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/itens-requisicao")
    public ResponseEntity<List<ItemRequisicaoDTO>> searchItemRequisicaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemRequisicaos for query {}", query);
        Page<ItemRequisicaoDTO> page = itemRequisicaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
