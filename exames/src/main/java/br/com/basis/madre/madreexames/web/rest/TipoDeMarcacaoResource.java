package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.service.TipoDeMarcacaoService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.TipoDeMarcacaoDTO;

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
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.TipoDeMarcacao}.
 */
@RestController
@RequestMapping("/api")
public class TipoDeMarcacaoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeMarcacaoResource.class);

    private static final String ENTITY_NAME = "madreexamesTipoDeMarcacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDeMarcacaoService tipoDeMarcacaoService;

    public TipoDeMarcacaoResource(TipoDeMarcacaoService tipoDeMarcacaoService) {
        this.tipoDeMarcacaoService = tipoDeMarcacaoService;
    }

    /**
     * {@code POST  /tipo-de-marcacaos} : Create a new tipoDeMarcacao.
     *
     * @param tipoDeMarcacaoDTO the tipoDeMarcacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDeMarcacaoDTO, or with status {@code 400 (Bad Request)} if the tipoDeMarcacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-de-marcacaos")
    public ResponseEntity<TipoDeMarcacaoDTO> createTipoDeMarcacao(@Valid @RequestBody TipoDeMarcacaoDTO tipoDeMarcacaoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeMarcacao : {}", tipoDeMarcacaoDTO);
        if (tipoDeMarcacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeMarcacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeMarcacaoDTO result = tipoDeMarcacaoService.save(tipoDeMarcacaoDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-marcacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-de-marcacaos} : Updates an existing tipoDeMarcacao.
     *
     * @param tipoDeMarcacaoDTO the tipoDeMarcacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDeMarcacaoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDeMarcacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDeMarcacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-de-marcacaos")
    public ResponseEntity<TipoDeMarcacaoDTO> updateTipoDeMarcacao(@Valid @RequestBody TipoDeMarcacaoDTO tipoDeMarcacaoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeMarcacao : {}", tipoDeMarcacaoDTO);
        if (tipoDeMarcacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeMarcacaoDTO result = tipoDeMarcacaoService.save(tipoDeMarcacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoDeMarcacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-de-marcacaos} : get all the tipoDeMarcacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDeMarcacaos in body.
     */
    @GetMapping("/tipo-de-marcacaos")
    public ResponseEntity<List<TipoDeMarcacaoDTO>> getAllTipoDeMarcacaos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeMarcacaos");
        Page<TipoDeMarcacaoDTO> page = tipoDeMarcacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-de-marcacaos/:id} : get the "id" tipoDeMarcacao.
     *
     * @param id the id of the tipoDeMarcacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDeMarcacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-de-marcacaos/{id}")
    public ResponseEntity<TipoDeMarcacaoDTO> getTipoDeMarcacao(@PathVariable Long id) {
        log.debug("REST request to get TipoDeMarcacao : {}", id);
        Optional<TipoDeMarcacaoDTO> tipoDeMarcacaoDTO = tipoDeMarcacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeMarcacaoDTO);
    }

    /**
     * {@code DELETE  /tipo-de-marcacaos/:id} : delete the "id" tipoDeMarcacao.
     *
     * @param id the id of the tipoDeMarcacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-de-marcacaos/{id}")
    public ResponseEntity<Void> deleteTipoDeMarcacao(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeMarcacao : {}", id);
        tipoDeMarcacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-de-marcacaos?query=:query} : search for the tipoDeMarcacao corresponding
     * to the query.
     *
     * @param query the query of the tipoDeMarcacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-de-marcacaos")
    public ResponseEntity<List<TipoDeMarcacaoDTO>> searchTipoDeMarcacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDeMarcacaos for query {}", query);
        Page<TipoDeMarcacaoDTO> page = tipoDeMarcacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
