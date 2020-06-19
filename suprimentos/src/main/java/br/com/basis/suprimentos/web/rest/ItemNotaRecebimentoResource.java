package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.ItemNotaRecebimentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.suprimentos.service.dto.ItemNotaRecebimentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.suprimentos.domain.ItemNotaRecebimento}.
 */
@RestController
@RequestMapping("/api")
public class ItemNotaRecebimentoResource {

    private final Logger log = LoggerFactory.getLogger(ItemNotaRecebimentoResource.class);

    private static final String ENTITY_NAME = "madresuprimentosItemNotaRecebimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemNotaRecebimentoService itemNotaRecebimentoService;

    public ItemNotaRecebimentoResource(ItemNotaRecebimentoService itemNotaRecebimentoService) {
        this.itemNotaRecebimentoService = itemNotaRecebimentoService;
    }

    /**
     * {@code POST  /item-nota-recebimentos} : Create a new itemNotaRecebimento.
     *
     * @param itemNotaRecebimentoDTO the itemNotaRecebimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemNotaRecebimentoDTO, or with status {@code 400 (Bad Request)} if the itemNotaRecebimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-nota-recebimentos")
    public ResponseEntity<ItemNotaRecebimentoDTO> createItemNotaRecebimento(@Valid @RequestBody ItemNotaRecebimentoDTO itemNotaRecebimentoDTO) throws URISyntaxException {
        log.debug("REST request to save ItemNotaRecebimento : {}", itemNotaRecebimentoDTO);
        if (itemNotaRecebimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemNotaRecebimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemNotaRecebimentoDTO result = itemNotaRecebimentoService.save(itemNotaRecebimentoDTO);
        return ResponseEntity.created(new URI("/api/item-nota-recebimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-nota-recebimentos} : Updates an existing itemNotaRecebimento.
     *
     * @param itemNotaRecebimentoDTO the itemNotaRecebimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemNotaRecebimentoDTO,
     * or with status {@code 400 (Bad Request)} if the itemNotaRecebimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemNotaRecebimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-nota-recebimentos")
    public ResponseEntity<ItemNotaRecebimentoDTO> updateItemNotaRecebimento(@Valid @RequestBody ItemNotaRecebimentoDTO itemNotaRecebimentoDTO) throws URISyntaxException {
        log.debug("REST request to update ItemNotaRecebimento : {}", itemNotaRecebimentoDTO);
        if (itemNotaRecebimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemNotaRecebimentoDTO result = itemNotaRecebimentoService.save(itemNotaRecebimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemNotaRecebimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-nota-recebimentos} : get all the itemNotaRecebimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemNotaRecebimentos in body.
     */
    @GetMapping("/item-nota-recebimentos")
    public ResponseEntity<List<ItemNotaRecebimentoDTO>> getAllItemNotaRecebimentos(Pageable pageable) {
        log.debug("REST request to get a page of ItemNotaRecebimentos");
        Page<ItemNotaRecebimentoDTO> page = itemNotaRecebimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-nota-recebimentos/:id} : get the "id" itemNotaRecebimento.
     *
     * @param id the id of the itemNotaRecebimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemNotaRecebimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-nota-recebimentos/{id}")
    public ResponseEntity<ItemNotaRecebimentoDTO> getItemNotaRecebimento(@PathVariable Long id) {
        log.debug("REST request to get ItemNotaRecebimento : {}", id);
        Optional<ItemNotaRecebimentoDTO> itemNotaRecebimentoDTO = itemNotaRecebimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemNotaRecebimentoDTO);
    }

    /**
     * {@code DELETE  /item-nota-recebimentos/:id} : delete the "id" itemNotaRecebimento.
     *
     * @param id the id of the itemNotaRecebimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-nota-recebimentos/{id}")
    public ResponseEntity<Void> deleteItemNotaRecebimento(@PathVariable Long id) {
        log.debug("REST request to delete ItemNotaRecebimento : {}", id);
        itemNotaRecebimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-nota-recebimentos?query=:query} : search for the itemNotaRecebimento corresponding
     * to the query.
     *
     * @param query the query of the itemNotaRecebimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-nota-recebimentos")
    public ResponseEntity<List<ItemNotaRecebimentoDTO>> searchItemNotaRecebimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemNotaRecebimentos for query {}", query);
        Page<ItemNotaRecebimentoDTO> page = itemNotaRecebimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
