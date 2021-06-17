package br.com.basis.madre.exames.web.rest;

import br.com.basis.madre.exames.service.TipoAmostraService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.exames.service.dto.TipoAmostraDTO;

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
 * REST controller for managing {@link br.com.basis.madre.exames.domain.TipoAmostra}.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoAmostras in body.
     */
    @GetMapping("/tipo-amostras")
    public List<TipoAmostraDTO> getAllTipoAmostras() {
        log.debug("REST request to get all TipoAmostras");
        return tipoAmostraService.findAll();
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
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-amostras")
    public List<TipoAmostraDTO> searchTipoAmostras(@RequestParam String query) {
        log.debug("REST request to search TipoAmostras for query {}", query);
        return tipoAmostraService.search(query);
    }
}
