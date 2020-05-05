package br.com.basis.madre.prescricao.web.rest;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.repository.search.ItemPrescricaoMedicamentoSearchRepository;
import br.com.basis.madre.prescricao.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository;

    private final ItemPrescricaoMedicamentoSearchRepository itemPrescricaoMedicamentoSearchRepository;

    public ItemPrescricaoMedicamentoResource(ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository, ItemPrescricaoMedicamentoSearchRepository itemPrescricaoMedicamentoSearchRepository) {
        this.itemPrescricaoMedicamentoRepository = itemPrescricaoMedicamentoRepository;
        this.itemPrescricaoMedicamentoSearchRepository = itemPrescricaoMedicamentoSearchRepository;
    }

    /**
     * {@code POST  /item-prescricao-medicamentos} : Create a new itemPrescricaoMedicamento.
     *
     * @param itemPrescricaoMedicamento the itemPrescricaoMedicamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPrescricaoMedicamento, or with status {@code 400 (Bad Request)} if the itemPrescricaoMedicamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-prescricao-medicamentos")
    public ResponseEntity<ItemPrescricaoMedicamento> createItemPrescricaoMedicamento(@Valid @RequestBody ItemPrescricaoMedicamento itemPrescricaoMedicamento) throws URISyntaxException {
        log.debug("REST request to save ItemPrescricaoMedicamento : {}", itemPrescricaoMedicamento);
        if (itemPrescricaoMedicamento.getId() != null) {
            throw new BadRequestAlertException("A new itemPrescricaoMedicamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPrescricaoMedicamento result = itemPrescricaoMedicamentoRepository.save(itemPrescricaoMedicamento);
        itemPrescricaoMedicamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/item-prescricao-medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-prescricao-medicamentos} : Updates an existing itemPrescricaoMedicamento.
     *
     * @param itemPrescricaoMedicamento the itemPrescricaoMedicamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPrescricaoMedicamento,
     * or with status {@code 400 (Bad Request)} if the itemPrescricaoMedicamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPrescricaoMedicamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-prescricao-medicamentos")
    public ResponseEntity<ItemPrescricaoMedicamento> updateItemPrescricaoMedicamento(@Valid @RequestBody ItemPrescricaoMedicamento itemPrescricaoMedicamento) throws URISyntaxException {
        log.debug("REST request to update ItemPrescricaoMedicamento : {}", itemPrescricaoMedicamento);
        if (itemPrescricaoMedicamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPrescricaoMedicamento result = itemPrescricaoMedicamentoRepository.save(itemPrescricaoMedicamento);
        itemPrescricaoMedicamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPrescricaoMedicamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-prescricao-medicamentos} : get all the itemPrescricaoMedicamentos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPrescricaoMedicamentos in body.
     */
    @GetMapping("/item-prescricao-medicamentos")
    public List<ItemPrescricaoMedicamento> getAllItemPrescricaoMedicamentos() {
        log.debug("REST request to get all ItemPrescricaoMedicamentos");
        return itemPrescricaoMedicamentoRepository.findAll();
    }

    /**
     * {@code GET  /item-prescricao-medicamentos/:id} : get the "id" itemPrescricaoMedicamento.
     *
     * @param id the id of the itemPrescricaoMedicamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPrescricaoMedicamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-prescricao-medicamentos/{id}")
    public ResponseEntity<ItemPrescricaoMedicamento> getItemPrescricaoMedicamento(@PathVariable Long id) {
        log.debug("REST request to get ItemPrescricaoMedicamento : {}", id);
        Optional<ItemPrescricaoMedicamento> itemPrescricaoMedicamento = itemPrescricaoMedicamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemPrescricaoMedicamento);
    }

    /**
     * {@code DELETE  /item-prescricao-medicamentos/:id} : delete the "id" itemPrescricaoMedicamento.
     *
     * @param id the id of the itemPrescricaoMedicamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-prescricao-medicamentos/{id}")
    public ResponseEntity<Void> deleteItemPrescricaoMedicamento(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrescricaoMedicamento : {}", id);
        itemPrescricaoMedicamentoRepository.deleteById(id);
        itemPrescricaoMedicamentoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-prescricao-medicamentos?query=:query} : search for the itemPrescricaoMedicamento corresponding
     * to the query.
     *
     * @param query the query of the itemPrescricaoMedicamento search.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-prescricao-medicamentos")
    public List<ItemPrescricaoMedicamento> searchItemPrescricaoMedicamentos(@RequestParam String query) {
        log.debug("REST request to search ItemPrescricaoMedicamentos for query {}", query);
        return StreamSupport
            .stream(itemPrescricaoMedicamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
