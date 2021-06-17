package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.ItemSolicitacaoExameService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.ItemSolicitacaoExameDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link br.com.basis.madre.exames.domain.ItemSolicitacaoExame}.
 */
@RestController
@RequestMapping("/api")
public class ItemSolicitacaoExameResource {

    private final Logger log = LoggerFactory.getLogger(ItemSolicitacaoExameResource.class);

    private static final String ENTITY_NAME = "madreexamesItemSolicitacaoExame";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemSolicitacaoExameService itemSolicitacaoExameService;

    public ItemSolicitacaoExameResource(ItemSolicitacaoExameService itemSolicitacaoExameService) {
        this.itemSolicitacaoExameService = itemSolicitacaoExameService;
    }

    /**
     * {@code POST  /item-solicitacao-exames} : Create a new itemSolicitacaoExame.
     *
     * @param itemSolicitacaoExameDTO the itemSolicitacaoExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemSolicitacaoExameDTO, or with status {@code 400 (Bad Request)} if the itemSolicitacaoExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-solicitacao-exames")
    public ResponseEntity<ItemSolicitacaoExameDTO> createItemSolicitacaoExame(@Valid @RequestBody ItemSolicitacaoExameDTO itemSolicitacaoExameDTO) throws URISyntaxException {
        log.debug("REST request to save ItemSolicitacaoExame : {}", itemSolicitacaoExameDTO);
        if (itemSolicitacaoExameDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemSolicitacaoExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemSolicitacaoExameDTO result = itemSolicitacaoExameService.save(itemSolicitacaoExameDTO);
        return ResponseEntity.created(new URI("/api/item-solicitacao-exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-solicitacao-exames} : Updates an existing itemSolicitacaoExame.
     *
     * @param itemSolicitacaoExameDTO the itemSolicitacaoExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemSolicitacaoExameDTO,
     * or with status {@code 400 (Bad Request)} if the itemSolicitacaoExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemSolicitacaoExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-solicitacao-exames")
    public ResponseEntity<ItemSolicitacaoExameDTO> updateItemSolicitacaoExame(@Valid @RequestBody ItemSolicitacaoExameDTO itemSolicitacaoExameDTO) throws URISyntaxException {
        log.debug("REST request to update ItemSolicitacaoExame : {}", itemSolicitacaoExameDTO);
        if (itemSolicitacaoExameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemSolicitacaoExameDTO result = itemSolicitacaoExameService.save(itemSolicitacaoExameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemSolicitacaoExameDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-solicitacao-exames} : get all the itemSolicitacaoExames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemSolicitacaoExames in body.
     */
    @GetMapping("/item-solicitacao-exames")
    public List<ItemSolicitacaoExameDTO> getAllItemSolicitacaoExames() {
        log.debug("REST request to get all ItemSolicitacaoExames");
        return itemSolicitacaoExameService.findAll();
    }

    /**
     * {@code GET  /item-solicitacao-exames/:id} : get the "id" itemSolicitacaoExame.
     *
     * @param id the id of the itemSolicitacaoExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemSolicitacaoExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-solicitacao-exames/{id}")
    public ResponseEntity<ItemSolicitacaoExameDTO> getItemSolicitacaoExame(@PathVariable Long id) {
        log.debug("REST request to get ItemSolicitacaoExame : {}", id);
        Optional<ItemSolicitacaoExameDTO> itemSolicitacaoExameDTO = itemSolicitacaoExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemSolicitacaoExameDTO);
    }

    /**
     * {@code DELETE  /item-solicitacao-exames/:id} : delete the "id" itemSolicitacaoExame.
     *
     * @param id the id of the itemSolicitacaoExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-solicitacao-exames/{id}")
    public ResponseEntity<Void> deleteItemSolicitacaoExame(@PathVariable Long id) {
        log.debug("REST request to delete ItemSolicitacaoExame : {}", id);
        itemSolicitacaoExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-solicitacao-exames?query=:query} : search for the itemSolicitacaoExame corresponding
     * to the query.
     *
     * @param query the query of the itemSolicitacaoExame search.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-solicitacao-exames")
    public List<ItemSolicitacaoExameDTO> searchItemSolicitacaoExames(@RequestParam String query) {
        log.debug("REST request to search ItemSolicitacaoExames for query {}", query);
        return itemSolicitacaoExameService.search(query);
    }
}
