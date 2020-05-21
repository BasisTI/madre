package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.ItemPrescricaoDietaService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDietaDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta}.
 */
@RestController
@RequestMapping("/api")
public class ItemPrescricaoDietaResource {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoDietaResource.class);

    private static final String ENTITY_NAME = "prescricaoItemPrescricaoDieta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPrescricaoDietaService itemPrescricaoDietaService;

    public ItemPrescricaoDietaResource(ItemPrescricaoDietaService itemPrescricaoDietaService) {
        this.itemPrescricaoDietaService = itemPrescricaoDietaService;
    }

    /**
     * {@code POST  /item-prescricao-dietas} : Create a new itemPrescricaoDieta.
     *
     * @param itemPrescricaoDietaDTO the itemPrescricaoDietaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPrescricaoDietaDTO, or with status {@code 400 (Bad Request)} if the itemPrescricaoDieta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-prescricao-dietas")
    public ResponseEntity<ItemPrescricaoDietaDTO> createItemPrescricaoDieta(@Valid @RequestBody ItemPrescricaoDietaDTO itemPrescricaoDietaDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPrescricaoDieta : {}", itemPrescricaoDietaDTO);
        if (itemPrescricaoDietaDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPrescricaoDieta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPrescricaoDietaDTO result = itemPrescricaoDietaService.save(itemPrescricaoDietaDTO);
        return ResponseEntity.created(new URI("/api/item-prescricao-dietas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-prescricao-dietas} : Updates an existing itemPrescricaoDieta.
     *
     * @param itemPrescricaoDietaDTO the itemPrescricaoDietaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPrescricaoDietaDTO,
     * or with status {@code 400 (Bad Request)} if the itemPrescricaoDietaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPrescricaoDietaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-prescricao-dietas")
    public ResponseEntity<ItemPrescricaoDietaDTO> updateItemPrescricaoDieta(@Valid @RequestBody ItemPrescricaoDietaDTO itemPrescricaoDietaDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPrescricaoDieta : {}", itemPrescricaoDietaDTO);
        if (itemPrescricaoDietaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPrescricaoDietaDTO result = itemPrescricaoDietaService.save(itemPrescricaoDietaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPrescricaoDietaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-prescricao-dietas} : get all the itemPrescricaoDietas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPrescricaoDietas in body.
     */
    @GetMapping("/item-prescricao-dietas")
    public ResponseEntity<List<ItemPrescricaoDietaDTO>> getAllItemPrescricaoDietas(Pageable pageable) {
        log.debug("REST request to get a page of ItemPrescricaoDietas");
        Page<ItemPrescricaoDietaDTO> page = itemPrescricaoDietaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-prescricao-dietas/:id} : get the "id" itemPrescricaoDieta.
     *
     * @param id the id of the itemPrescricaoDietaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPrescricaoDietaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-prescricao-dietas/{id}")
    public ResponseEntity<ItemPrescricaoDietaDTO> getItemPrescricaoDieta(@PathVariable Long id) {
        log.debug("REST request to get ItemPrescricaoDieta : {}", id);
        Optional<ItemPrescricaoDietaDTO> itemPrescricaoDietaDTO = itemPrescricaoDietaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPrescricaoDietaDTO);
    }

    /**
     * {@code DELETE  /item-prescricao-dietas/:id} : delete the "id" itemPrescricaoDieta.
     *
     * @param id the id of the itemPrescricaoDietaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-prescricao-dietas/{id}")
    public ResponseEntity<Void> deleteItemPrescricaoDieta(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrescricaoDieta : {}", id);
        itemPrescricaoDietaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-prescricao-dietas?query=:query} : search for the itemPrescricaoDieta corresponding
     * to the query.
     *
     * @param query the query of the itemPrescricaoDieta search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-prescricao-dietas")
    public ResponseEntity<List<ItemPrescricaoDietaDTO>> searchItemPrescricaoDietas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemPrescricaoDietas for query {}", query);
        Page<ItemPrescricaoDietaDTO> page = itemPrescricaoDietaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
