package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.service.ItemPrescricaoMedicamentoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento}.
 */
@RestController
@RequestMapping("/api")
public class ItemPrescricaoMedicamentoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoMedicamentoResource.class);

    private static final String ENTITY_NAME = "prescricaoItemPrescricaoMedicamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPrescricaoMedicamentoService itemPrescricaoMedicamentoService;

    public ItemPrescricaoMedicamentoResource(ItemPrescricaoMedicamentoService itemPrescricaoMedicamentoService) {
        this.itemPrescricaoMedicamentoService = itemPrescricaoMedicamentoService;
    }

    /**
     * {@code POST  /item-prescricao-medicamentos} : Create a new itemPrescricaoMedicamento.
     *
     * @param itemPrescricaoMedicamentoDTO the itemPrescricaoMedicamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPrescricaoMedicamentoDTO, or with status {@code 400 (Bad Request)} if the itemPrescricaoMedicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-prescricao-medicamentos")
    public ResponseEntity<ItemPrescricaoMedicamentoDTO> createItemPrescricaoMedicamento(@Valid @RequestBody ItemPrescricaoMedicamentoDTO itemPrescricaoMedicamentoDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPrescricaoMedicamento : {}", itemPrescricaoMedicamentoDTO);
        if (itemPrescricaoMedicamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPrescricaoMedicamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPrescricaoMedicamentoDTO result = itemPrescricaoMedicamentoService.save(itemPrescricaoMedicamentoDTO);
        return ResponseEntity.created(new URI("/api/item-prescricao-medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-prescricao-medicamentos} : Updates an existing itemPrescricaoMedicamento.
     *
     * @param itemPrescricaoMedicamentoDTO the itemPrescricaoMedicamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPrescricaoMedicamentoDTO,
     * or with status {@code 400 (Bad Request)} if the itemPrescricaoMedicamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPrescricaoMedicamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-prescricao-medicamentos")
    public ResponseEntity<ItemPrescricaoMedicamentoDTO> updateItemPrescricaoMedicamento(@Valid @RequestBody ItemPrescricaoMedicamentoDTO itemPrescricaoMedicamentoDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPrescricaoMedicamento : {}", itemPrescricaoMedicamentoDTO);
        if (itemPrescricaoMedicamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPrescricaoMedicamentoDTO result = itemPrescricaoMedicamentoService.save(itemPrescricaoMedicamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPrescricaoMedicamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-prescricao-medicamentos} : get all the itemPrescricaoMedicamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPrescricaoMedicamentos in body.
     */
    @GetMapping("/item-prescricao-medicamentos")
    public ResponseEntity<List<ItemPrescricaoMedicamentoDTO>> getAllItemPrescricaoMedicamentos(Pageable pageable) {
        log.debug("REST request to get a page of ItemPrescricaoMedicamentos");
        Page<ItemPrescricaoMedicamentoDTO> page = itemPrescricaoMedicamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-prescricao-medicamentos/:id} : get the "id" itemPrescricaoMedicamento.
     *
     * @param id the id of the itemPrescricaoMedicamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPrescricaoMedicamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-prescricao-medicamentos/{id}")
    public ResponseEntity<ItemPrescricaoMedicamentoDTO> getItemPrescricaoMedicamento(@PathVariable Long id) {
        log.debug("REST request to get ItemPrescricaoMedicamento : {}", id);
        Optional<ItemPrescricaoMedicamentoDTO> itemPrescricaoMedicamentoDTO = itemPrescricaoMedicamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPrescricaoMedicamentoDTO);
    }

    /**
     * {@code DELETE  /item-prescricao-medicamentos/:id} : delete the "id" itemPrescricaoMedicamento.
     *
     * @param id the id of the itemPrescricaoMedicamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-prescricao-medicamentos/{id}")
    public ResponseEntity<Void> deleteItemPrescricaoMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrescricaoMedicamento : {}", id);
        itemPrescricaoMedicamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-prescricao-medicamentos?query=:query} : search for the itemPrescricaoMedicamento corresponding
     * to the query.
     *
     * @param query the query of the itemPrescricaoMedicamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-prescricao-medicamentos")
    public ResponseEntity<List<ItemPrescricaoMedicamentoDTO>> searchItemPrescricaoMedicamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemPrescricaoMedicamentos for query {}", query);
        Page<ItemPrescricaoMedicamentoDTO> page = itemPrescricaoMedicamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
