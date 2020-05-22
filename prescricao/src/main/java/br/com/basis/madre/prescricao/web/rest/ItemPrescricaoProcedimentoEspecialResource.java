package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.ItemPrescricaoProcedimentoEspecialService;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoProcedimentoEspecialDTO;

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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimentoEspecial}.
 */
@RestController
@RequestMapping("/api")
public class ItemPrescricaoProcedimentoEspecialResource {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoProcedimentoEspecialResource.class);

    private static final String ENTITY_NAME = "prescricaoItemPrescricaoProcedimentoEspecial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPrescricaoProcedimentoEspecialService itemPrescricaoProcedimentoEspecialService;

    public ItemPrescricaoProcedimentoEspecialResource(ItemPrescricaoProcedimentoEspecialService itemPrescricaoProcedimentoEspecialService) {
        this.itemPrescricaoProcedimentoEspecialService = itemPrescricaoProcedimentoEspecialService;
    }

    /**
     * {@code POST  /item-prescricao-procedimento-especials} : Create a new itemPrescricaoProcedimentoEspecial.
     *
     * @param itemPrescricaoProcedimentoEspecialDTO the itemPrescricaoProcedimentoEspecialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPrescricaoProcedimentoEspecialDTO, or with status {@code 400 (Bad Request)} if the itemPrescricaoProcedimentoEspecial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-prescricao-procedimento-especials")
    public ResponseEntity<ItemPrescricaoProcedimentoEspecialDTO> createItemPrescricaoProcedimentoEspecial(@Valid @RequestBody ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPrescricaoProcedimentoEspecial : {}", itemPrescricaoProcedimentoEspecialDTO);
        if (itemPrescricaoProcedimentoEspecialDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPrescricaoProcedimentoEspecial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPrescricaoProcedimentoEspecialDTO result = itemPrescricaoProcedimentoEspecialService.save(itemPrescricaoProcedimentoEspecialDTO);
        return ResponseEntity.created(new URI("/api/item-prescricao-procedimento-especials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-prescricao-procedimento-especials} : Updates an existing itemPrescricaoProcedimentoEspecial.
     *
     * @param itemPrescricaoProcedimentoEspecialDTO the itemPrescricaoProcedimentoEspecialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPrescricaoProcedimentoEspecialDTO,
     * or with status {@code 400 (Bad Request)} if the itemPrescricaoProcedimentoEspecialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPrescricaoProcedimentoEspecialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-prescricao-procedimento-especials")
    public ResponseEntity<ItemPrescricaoProcedimentoEspecialDTO> updateItemPrescricaoProcedimentoEspecial(@Valid @RequestBody ItemPrescricaoProcedimentoEspecialDTO itemPrescricaoProcedimentoEspecialDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPrescricaoProcedimentoEspecial : {}", itemPrescricaoProcedimentoEspecialDTO);
        if (itemPrescricaoProcedimentoEspecialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPrescricaoProcedimentoEspecialDTO result = itemPrescricaoProcedimentoEspecialService.save(itemPrescricaoProcedimentoEspecialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPrescricaoProcedimentoEspecialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-prescricao-procedimento-especials} : get all the itemPrescricaoProcedimentoEspecials.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPrescricaoProcedimentoEspecials in body.
     */
    @GetMapping("/item-prescricao-procedimento-especials")
    public ResponseEntity<List<ItemPrescricaoProcedimentoEspecialDTO>> getAllItemPrescricaoProcedimentoEspecials(Pageable pageable) {
        log.debug("REST request to get a page of ItemPrescricaoProcedimentoEspecials");
        Page<ItemPrescricaoProcedimentoEspecialDTO> page = itemPrescricaoProcedimentoEspecialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-prescricao-procedimento-especials/:id} : get the "id" itemPrescricaoProcedimentoEspecial.
     *
     * @param id the id of the itemPrescricaoProcedimentoEspecialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPrescricaoProcedimentoEspecialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-prescricao-procedimento-especials/{id}")
    public ResponseEntity<ItemPrescricaoProcedimentoEspecialDTO> getItemPrescricaoProcedimentoEspecial(@PathVariable Long id) {
        log.debug("REST request to get ItemPrescricaoProcedimentoEspecial : {}", id);
        Optional<ItemPrescricaoProcedimentoEspecialDTO> itemPrescricaoProcedimentoEspecialDTO = itemPrescricaoProcedimentoEspecialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPrescricaoProcedimentoEspecialDTO);
    }

    /**
     * {@code DELETE  /item-prescricao-procedimento-especials/:id} : delete the "id" itemPrescricaoProcedimentoEspecial.
     *
     * @param id the id of the itemPrescricaoProcedimentoEspecialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-prescricao-procedimento-especials/{id}")
    public ResponseEntity<Void> deleteItemPrescricaoProcedimentoEspecial(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrescricaoProcedimentoEspecial : {}", id);
        itemPrescricaoProcedimentoEspecialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-prescricao-procedimento-especials?query=:query} : search for the itemPrescricaoProcedimentoEspecial corresponding
     * to the query.
     *
     * @param query the query of the itemPrescricaoProcedimentoEspecial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-prescricao-procedimento-especials")
    public ResponseEntity<List<ItemPrescricaoProcedimentoEspecialDTO>> searchItemPrescricaoProcedimentoEspecials(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemPrescricaoProcedimentoEspecials for query {}", query);
        Page<ItemPrescricaoProcedimentoEspecialDTO> page = itemPrescricaoProcedimentoEspecialService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
