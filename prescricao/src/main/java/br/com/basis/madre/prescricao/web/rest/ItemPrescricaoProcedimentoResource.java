package br.com.basis.madre.prescricao.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.com.basis.madre.prescricao.service.ItemPrescricaoProcedimentoService;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento}.
 */
@RestController
@RequestMapping("/api")
public class ItemPrescricaoProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoProcedimentoResource.class);

    private static final String ENTITY_NAME = "prescricaoItemPrescricaoProcedimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPrescricaoProcedimentoService itemPrescricaoProcedimentoService;

    public ItemPrescricaoProcedimentoResource(ItemPrescricaoProcedimentoService itemPrescricaoProcedimentoService) {
        this.itemPrescricaoProcedimentoService = itemPrescricaoProcedimentoService;
    }

    /**
     * {@code POST  /item-prescricao-procedimentos} : Create a new itemPrescricaoProcedimento.
     *
     * @param itemPrescricaoProcedimentoDTO the itemPrescricaoProcedimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPrescricaoProcedimentoDTO, or with status {@code 400 (Bad Request)} if the itemPrescricaoProcedimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-prescricao-procedimentos")
    public ResponseEntity<ItemPrescricaoProcedimentoDTO> createItemPrescricaoProcedimento(@Valid @RequestBody ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPrescricaoProcedimento : {}", itemPrescricaoProcedimentoDTO);
        if (itemPrescricaoProcedimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPrescricaoProcedimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPrescricaoProcedimentoDTO result = itemPrescricaoProcedimentoService.save(itemPrescricaoProcedimentoDTO);
        return ResponseEntity.created(new URI("/api/item-prescricao-procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-prescricao-procedimentos} : Updates an existing itemPrescricaoProcedimento.
     *
     * @param itemPrescricaoProcedimentoDTO the itemPrescricaoProcedimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPrescricaoProcedimentoDTO,
     * or with status {@code 400 (Bad Request)} if the itemPrescricaoProcedimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPrescricaoProcedimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-prescricao-procedimentos")
    public ResponseEntity<ItemPrescricaoProcedimentoDTO> updateItemPrescricaoProcedimento(@Valid @RequestBody ItemPrescricaoProcedimentoDTO itemPrescricaoProcedimentoDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPrescricaoProcedimento : {}", itemPrescricaoProcedimentoDTO);
        if (itemPrescricaoProcedimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPrescricaoProcedimentoDTO result = itemPrescricaoProcedimentoService.save(itemPrescricaoProcedimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPrescricaoProcedimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-prescricao-procedimentos} : get all the itemPrescricaoProcedimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPrescricaoProcedimentos in body.
     */
    @GetMapping("/item-prescricao-procedimentos")
    public ResponseEntity<List<ItemPrescricaoProcedimentoDTO>> getAllItemPrescricaoProcedimentos(Pageable pageable) {
        log.debug("REST request to get a page of ItemPrescricaoProcedimentos");
        Page<ItemPrescricaoProcedimentoDTO> page = itemPrescricaoProcedimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-prescricao-procedimentos/:id} : get the "id" itemPrescricaoProcedimento.
     *
     * @param id the id of the itemPrescricaoProcedimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPrescricaoProcedimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-prescricao-procedimentos/{id}")
    public ResponseEntity<ItemPrescricaoProcedimentoDTO> getItemPrescricaoProcedimento(@PathVariable Long id) {
        log.debug("REST request to get ItemPrescricaoProcedimento : {}", id);
        Optional<ItemPrescricaoProcedimentoDTO> itemPrescricaoProcedimentoDTO = itemPrescricaoProcedimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPrescricaoProcedimentoDTO);
    }

    /**
     * {@code DELETE  /item-prescricao-procedimentos/:id} : delete the "id" itemPrescricaoProcedimento.
     *
     * @param id the id of the itemPrescricaoProcedimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-prescricao-procedimentos/{id}")
    public ResponseEntity<Void> deleteItemPrescricaoProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrescricaoProcedimento : {}", id);
        itemPrescricaoProcedimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-prescricao-procedimentos?query=:query} : search for the itemPrescricaoProcedimento corresponding
     * to the query.
     *
     * @param query the query of the itemPrescricaoProcedimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-prescricao-procedimentos")
    public ResponseEntity<List<ItemPrescricaoProcedimentoDTO>> searchItemPrescricaoProcedimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemPrescricaoProcedimentos for query {}", query);
        Page<ItemPrescricaoProcedimentoDTO> page = itemPrescricaoProcedimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
