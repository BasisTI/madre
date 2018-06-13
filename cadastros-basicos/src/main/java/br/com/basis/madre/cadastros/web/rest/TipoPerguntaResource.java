package br.com.basis.madre.cadastros.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.cadastros.service.TipoPerguntaService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import br.com.basis.madre.cadastros.service.dto.TipoPerguntaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing TipoPergunta.
 */
@RestController
@RequestMapping("/api")
public class TipoPerguntaResource {

    private final Logger log = LoggerFactory.getLogger(TipoPerguntaResource.class);

    private static final String ENTITY_NAME = "tipoPergunta";

    private final TipoPerguntaService tipoPerguntaService;

    public TipoPerguntaResource(TipoPerguntaService tipoPerguntaService) {
        this.tipoPerguntaService = tipoPerguntaService;
    }

    /**
     * POST  /tipo-perguntas : Create a new tipoPergunta.
     *
     * @param tipoPerguntaDTO the tipoPerguntaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoPerguntaDTO, or with status 400 (Bad Request) if the tipoPergunta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-perguntas")
    @Timed
    public ResponseEntity<TipoPerguntaDTO> createTipoPergunta(@Valid @RequestBody TipoPerguntaDTO tipoPerguntaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoPergunta : {}", tipoPerguntaDTO);
        if (tipoPerguntaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoPergunta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPerguntaDTO result = tipoPerguntaService.save(tipoPerguntaDTO);
        return ResponseEntity.created(new URI("/api/tipo-perguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-perguntas : Updates an existing tipoPergunta.
     *
     * @param tipoPerguntaDTO the tipoPerguntaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoPerguntaDTO,
     * or with status 400 (Bad Request) if the tipoPerguntaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoPerguntaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-perguntas")
    @Timed
    public ResponseEntity<TipoPerguntaDTO> updateTipoPergunta(@Valid @RequestBody TipoPerguntaDTO tipoPerguntaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoPergunta : {}", tipoPerguntaDTO);
        if (tipoPerguntaDTO.getId() == null) {
            return createTipoPergunta(tipoPerguntaDTO);
        }
        TipoPerguntaDTO result = tipoPerguntaService.save(tipoPerguntaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoPerguntaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-perguntas : get all the tipoPerguntas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoPerguntas in body
     */
    @GetMapping("/tipo-perguntas")
    @Timed
    public ResponseEntity<List<TipoPerguntaDTO>> getAllTipoPerguntas(Pageable pageable) {
        log.debug("REST request to get a page of TipoPerguntas");
        Page<TipoPerguntaDTO> page = tipoPerguntaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-perguntas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-perguntas/:id : get the "id" tipoPergunta.
     *
     * @param id the id of the tipoPerguntaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoPerguntaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-perguntas/{id}")
    @Timed
    public ResponseEntity<TipoPerguntaDTO> getTipoPergunta(@PathVariable Long id) {
        log.debug("REST request to get TipoPergunta : {}", id);
        TipoPerguntaDTO tipoPerguntaDTO = tipoPerguntaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoPerguntaDTO));
    }

    /**
     * DELETE  /tipo-perguntas/:id : delete the "id" tipoPergunta.
     *
     * @param id the id of the tipoPerguntaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-perguntas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoPergunta(@PathVariable Long id) {
        log.debug("REST request to delete TipoPergunta : {}", id);
        tipoPerguntaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-perguntas?query=:query : search for the tipoPergunta corresponding
     * to the query.
     *
     * @param query the query of the tipoPergunta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-perguntas")
    @Timed
    public ResponseEntity<List<TipoPerguntaDTO>> searchTipoPerguntas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoPerguntas for query {}", query);
        Page<TipoPerguntaDTO> page = tipoPerguntaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-perguntas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
