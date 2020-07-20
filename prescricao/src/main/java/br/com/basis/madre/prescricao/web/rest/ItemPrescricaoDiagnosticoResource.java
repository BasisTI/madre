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

import br.com.basis.madre.prescricao.service.ItemPrescricaoDiagnosticoService;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDiagnosticoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico}.
 */
@RestController
@RequestMapping("/api")
public class ItemPrescricaoDiagnosticoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPrescricaoDiagnosticoResource.class);

    private static final String ENTITY_NAME = "prescricaoItemPrescricaoDiagnostico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemPrescricaoDiagnosticoService itemPrescricaoDiagnosticoService;

    public ItemPrescricaoDiagnosticoResource(ItemPrescricaoDiagnosticoService itemPrescricaoDiagnosticoService) {
        this.itemPrescricaoDiagnosticoService = itemPrescricaoDiagnosticoService;
    }

    /**
     * {@code POST  /item-prescricao-diagnosticos} : Create a new itemPrescricaoDiagnostico.
     *
     * @param itemPrescricaoDiagnosticoDTO the itemPrescricaoDiagnosticoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemPrescricaoDiagnosticoDTO, or with status {@code 400 (Bad Request)} if the itemPrescricaoDiagnostico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-prescricao-diagnosticos")
    public ResponseEntity<ItemPrescricaoDiagnosticoDTO> createItemPrescricaoDiagnostico(@Valid @RequestBody ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO) throws URISyntaxException {
        log.debug("REST request to save ItemPrescricaoDiagnostico : {}", itemPrescricaoDiagnosticoDTO);
        if (itemPrescricaoDiagnosticoDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemPrescricaoDiagnostico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemPrescricaoDiagnosticoDTO result = itemPrescricaoDiagnosticoService.save(itemPrescricaoDiagnosticoDTO);
        return ResponseEntity.created(new URI("/api/item-prescricao-diagnosticos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-prescricao-diagnosticos} : Updates an existing itemPrescricaoDiagnostico.
     *
     * @param itemPrescricaoDiagnosticoDTO the itemPrescricaoDiagnosticoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemPrescricaoDiagnosticoDTO,
     * or with status {@code 400 (Bad Request)} if the itemPrescricaoDiagnosticoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemPrescricaoDiagnosticoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-prescricao-diagnosticos")
    public ResponseEntity<ItemPrescricaoDiagnosticoDTO> updateItemPrescricaoDiagnostico(@Valid @RequestBody ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO) throws URISyntaxException {
        log.debug("REST request to update ItemPrescricaoDiagnostico : {}", itemPrescricaoDiagnosticoDTO);
        if (itemPrescricaoDiagnosticoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemPrescricaoDiagnosticoDTO result = itemPrescricaoDiagnosticoService.save(itemPrescricaoDiagnosticoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemPrescricaoDiagnosticoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-prescricao-diagnosticos} : get all the itemPrescricaoDiagnosticos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemPrescricaoDiagnosticos in body.
     */
    @GetMapping("/item-prescricao-diagnosticos")
    public ResponseEntity<List<ItemPrescricaoDiagnosticoDTO>> getAllItemPrescricaoDiagnosticos(Pageable pageable) {
        log.debug("REST request to get a page of ItemPrescricaoDiagnosticos");
        Page<ItemPrescricaoDiagnosticoDTO> page = itemPrescricaoDiagnosticoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-prescricao-diagnosticos/:id} : get the "id" itemPrescricaoDiagnostico.
     *
     * @param id the id of the itemPrescricaoDiagnosticoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemPrescricaoDiagnosticoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-prescricao-diagnosticos/{id}")
    public ResponseEntity<ItemPrescricaoDiagnosticoDTO> getItemPrescricaoDiagnostico(@PathVariable Long id) {
        log.debug("REST request to get ItemPrescricaoDiagnostico : {}", id);
        Optional<ItemPrescricaoDiagnosticoDTO> itemPrescricaoDiagnosticoDTO = itemPrescricaoDiagnosticoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemPrescricaoDiagnosticoDTO);
    }

    /**
     * {@code DELETE  /item-prescricao-diagnosticos/:id} : delete the "id" itemPrescricaoDiagnostico.
     *
     * @param id the id of the itemPrescricaoDiagnosticoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-prescricao-diagnosticos/{id}")
    public ResponseEntity<Void> deleteItemPrescricaoDiagnostico(@PathVariable Long id) {
        log.debug("REST request to delete ItemPrescricaoDiagnostico : {}", id);
        itemPrescricaoDiagnosticoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/item-prescricao-diagnosticos?query=:query} : search for the itemPrescricaoDiagnostico corresponding
     * to the query.
     *
     * @param query the query of the itemPrescricaoDiagnostico search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/item-prescricao-diagnosticos")
    public ResponseEntity<List<ItemPrescricaoDiagnosticoDTO>> searchItemPrescricaoDiagnosticos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ItemPrescricaoDiagnosticos for query {}", query);
        Page<ItemPrescricaoDiagnosticoDTO> page = itemPrescricaoDiagnosticoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
