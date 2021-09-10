package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.TipoAmostraService;
import br.com.basis.madre.madreexames.service.dto.TipoAmostraDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.TipoAmostra}.
 */
@RestController
@RequestMapping("/api")
public class TipoAmostraResource {

    private final Logger log = LoggerFactory.getLogger(TipoAmostraResource.class);

    private static final String ENTITY_NAME = "madreexamesTipoAmostra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoAmostraService tipoAmostraService;

    public TipoAmostraResource(TipoAmostraService tipoAmostraService) {
        this.tipoAmostraService = tipoAmostraService;
    }

    /**
     * {@code POST  /tipo-amostras} : Create a new tipoAmostra.
     *
     * @param tipoAmostraDTO the tipoAmostraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoAmostraDTO, or with status {@code 400 (Bad Request)} if the tipoAmostra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-amostras")
    public ResponseEntity<TipoAmostraDTO> createTipoAmostra(@Valid @RequestBody TipoAmostraDTO tipoAmostraDTO) throws URISyntaxException {
        log.debug("REST request to save TipoAmostra : {}", tipoAmostraDTO);
        if (tipoAmostraDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoAmostra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAmostraDTO result = tipoAmostraService.save(tipoAmostraDTO);
        return ResponseEntity.created(new URI("/api/tipo-amostras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-amostras} : Updates an existing tipoAmostra.
     *
     * @param tipoAmostraDTO the tipoAmostraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoAmostraDTO,
     * or with status {@code 400 (Bad Request)} if the tipoAmostraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoAmostraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-amostras")
    public ResponseEntity<TipoAmostraDTO> updateTipoAmostra(@Valid @RequestBody TipoAmostraDTO tipoAmostraDTO) throws URISyntaxException {
        log.debug("REST request to update TipoAmostra : {}", tipoAmostraDTO);
        if (tipoAmostraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoAmostraDTO result = tipoAmostraService.save(tipoAmostraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoAmostraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-amostras} : get all the tipoAmostras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoAmostras in body.
     */
    @GetMapping("/tipo-amostras")
    public ResponseEntity<List<TipoAmostraDTO>> getAllTipoAmostras(Pageable pageable) {
        log.debug("REST request to get a page of TipoAmostras");
        Page<TipoAmostraDTO> page = tipoAmostraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-amostras/:id} : get the "id" tipoAmostra.
     *
     * @param id the id of the tipoAmostraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoAmostraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-amostras/{id}")
    public ResponseEntity<TipoAmostraDTO> getTipoAmostra(@PathVariable Long id) {
        log.debug("REST request to get TipoAmostra : {}", id);
        Optional<TipoAmostraDTO> tipoAmostraDTO = tipoAmostraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoAmostraDTO);
    }

    /**
     * {@code DELETE  /tipo-amostras/:id} : delete the "id" tipoAmostra.
     *
     * @param id the id of the tipoAmostraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-amostras/{id}")
    public ResponseEntity<Void> deleteTipoAmostra(@PathVariable Long id) {
        log.debug("REST request to delete TipoAmostra : {}", id);
        tipoAmostraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-amostras?query=:query} : search for the tipoAmostra corresponding
     * to the query.
     *
     * @param query the query of the tipoAmostra search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-amostras")
    public ResponseEntity<List<TipoAmostraDTO>> searchTipoAmostras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoAmostras for query {}", query);
        Page<TipoAmostraDTO> page = tipoAmostraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
