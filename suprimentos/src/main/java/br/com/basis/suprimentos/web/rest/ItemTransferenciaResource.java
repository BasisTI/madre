package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.ItemTransferenciaService;
import br.com.basis.suprimentos.service.dto.ItemTransferenciaDTO;
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
public class ItemTransferenciaResource {
    private static final String ENTITY_NAME = "madresuprimentosItemTransferencia";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final ItemTransferenciaService itemTransferenciaService;

    @PostMapping("/itens-transferencias")
    public ResponseEntity<ItemTransferenciaDTO> createItemTransferencia(@Valid @RequestBody ItemTransferenciaDTO itemTransferenciaDTO) throws URISyntaxException {
        log.debug("REST request to save ItemTransferencia : {}", itemTransferenciaDTO);
        if (itemTransferenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemTransferencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemTransferenciaDTO result = itemTransferenciaService.save(itemTransferenciaDTO);
        return ResponseEntity.created(new URI("/api/itens-transferencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/itens-transferencias")
    public ResponseEntity<ItemTransferenciaDTO> updateItemTransferencia(@Valid @RequestBody ItemTransferenciaDTO itemTransferenciaDTO) throws URISyntaxException {
        log.debug("REST request to update ItemTransferencia : {}", itemTransferenciaDTO);
        if (itemTransferenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemTransferenciaDTO result = itemTransferenciaService.save(itemTransferenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemTransferenciaDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/itens-transferencias")
    public ResponseEntity<List<ItemTransferenciaDTO>> getAllItemTransferencias(Pageable pageable) {
        log.debug("REST request to get a page of ItemTransferencias");
        Page<ItemTransferenciaDTO> page = itemTransferenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/itens-transferencias/{id}")
    public ResponseEntity<ItemTransferenciaDTO> getItemTransferencia(@PathVariable Long id) {
        log.debug("REST request to get ItemTransferencia : {}", id);
        Optional<ItemTransferenciaDTO> itemTransferenciaDTO = itemTransferenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemTransferenciaDTO);
    }

    @DeleteMapping("/itens-transferencias/{id}")
    public ResponseEntity<Void> deleteItemTransferencia(@PathVariable Long id) {
        log.debug("REST request to delete ItemTransferencia : {}", id);
        itemTransferenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/itens-transferencias")
    public ResponseEntity<List<ItemTransferenciaDTO>> searchItemTransferencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemTransferencias for query {}", query);
        Page<ItemTransferenciaDTO> page = itemTransferenciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
