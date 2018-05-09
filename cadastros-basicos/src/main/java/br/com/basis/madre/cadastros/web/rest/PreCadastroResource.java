package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import br.com.basis.madre.cadastros.service.PreCadastroService;
import br.com.basis.madre.cadastros.service.dto.PreCadastroDTO;
import br.com.basis.madre.cadastros.service.exception.PreCadastroException;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PreCadastro.
 */
@RestController
@RequestMapping("/api")
public class PreCadastroResource {

    private final Logger log = LoggerFactory.getLogger(PreCadastroResource.class);

    private static final String ENTITY_NAME = "preCadastro";

    private final PreCadastroService preCadastroService;

    public PreCadastroResource(PreCadastroService preCadastroService) {
        this.preCadastroService = preCadastroService;
    }
    /**
     * POST  /pre-cadastros : Create a new preCadastro.
     *
     * @param preCadastroDTO the preCadastro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preCadastro, or with status 400 (Bad Request) if the preCadastro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pre-cadastros")
    @Timed
    public ResponseEntity<PreCadastroDTO> createPreCadastro(@Valid @RequestBody PreCadastroDTO preCadastroDTO) throws URISyntaxException {
        try{
        log.debug("REST request to save PreCadastro : {}", preCadastroDTO);
            if (preCadastroDTO.getId() != null) {
                throw new BadRequestAlertException("A new preCadastro cannot already have an ID", ENTITY_NAME, "idexists");
            }
            PreCadastroDTO result = preCadastroService.save(preCadastroDTO);
            return ResponseEntity.created(new URI("/api/pre-cadastros/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (PreCadastroException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, PreCadastroException.getCodeRegistroExisteBase(), e.getMessage()))
                .body(preCadastroDTO);
        }
    }

    /**
     * PUT  /pre-cadastros : Updates an existing preCadastro.
     *
     * @param preCadastroDTO the preCadastro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preCadastro,
     * or with status 400 (Bad Request) if the preCadastro is not valid,
     * or with status 500 (Internal Server Error) if the preCadastro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pre-cadastros")
    @Timed
    public ResponseEntity<PreCadastroDTO> updateParecerPadrao(@Valid @RequestBody PreCadastroDTO preCadastroDTO) throws URISyntaxException {
        try {
            log.debug("REST request to update ParecerPadrao : {}", preCadastroDTO);
            if (preCadastroDTO.getId() == null) {
                return createPreCadastro(preCadastroDTO);
            }
            PreCadastroDTO result = preCadastroService.save(preCadastroDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preCadastroDTO.getId().toString()))
                .body(result);
        } catch (PreCadastroException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, PreCadastroException.getCodeRegistroExisteBase(), e.getMessage()))
                .body(preCadastroDTO);
        }
    }

    /**
     * GET  /pre-cadastros : get all the preCadastros.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of preCadastros in body
     */
    @GetMapping("/pre-cadastros")
    @Timed
    public ResponseEntity<List<PreCadastroDTO>> getAllParecerPadraos(@RequestParam(value = "query") Optional<String> query ,
        @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ParecerPadraos");
        Page<PreCadastroDTO> page = preCadastroService.findAll(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pre-cadastros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /pre-cadastros/:id : get the "id" preCadastro.
     *
     * @param id the id of the preCadastro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preCadastro, or with status 404 (Not Found)
     */
    @GetMapping("/pre-cadastros/{id}")
    @Timed
    public ResponseEntity<PreCadastroDTO> getPreCadastro(@PathVariable Long id) {
        log.debug("REST request to get PreCadastro : {}", id);
        PreCadastroDTO preCadastroDTO = preCadastroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preCadastroDTO));
    }
    /**
     * DELETE  /pre-cadastros/:id : delete the "id" preCadastro.
     *
     * @param id the id of the preCadastro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pre-cadastros/{id}")
    @Timed
    public ResponseEntity<Void> deletePreCadastro(@PathVariable Long id) {
        log.debug("REST request to delete PreCadastro : {}", id);
        preCadastroService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pre-cadastros?query=:query : search for the preCadastro corresponding
     * to the query.
     *
     * @param query the query of the preCadastro search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pre-cadastros")
    @Timed
    public ResponseEntity<List<PreCadastro>> searchPreCadastros(@RequestParam(defaultValue = "*") String query, Pageable pageable) {
        log.debug("REST request to search for a page of PreCadastros for query {}", query);
        Page<PreCadastro> page = preCadastroService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pre-cadastros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /precadastro/:id : get jasper of  usuarios.
     *
     * @param tipoRelatorio
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping(value = "/precadastro/exportacao/{tipoRelatorio}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public ResponseEntity<InputStreamResource> getRelatorioExportacao(@PathVariable String tipoRelatorio) {
        try {
            return preCadastroService.gerarRelatorioExportacao(tipoRelatorio);
        } catch (RelatorioException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, RelatorioException.getCodeEntidade(), e.getMessage())).body(null);
        }
    }

}
