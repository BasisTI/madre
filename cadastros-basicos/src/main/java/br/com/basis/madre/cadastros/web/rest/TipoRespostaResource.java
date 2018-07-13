package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.repository.TipoRespostaRepository;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.util.MadreUtil;
import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.cadastros.domain.TipoResposta;
import br.com.basis.madre.cadastros.service.TipoRespostaService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.core.io.InputStreamResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.print.DocFlavor;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TipoResposta.
 */
@RestController
@RequestMapping("/api")
public class TipoRespostaResource {

    private final Logger log = LoggerFactory.getLogger(TipoRespostaResource.class);

    private static final String ENTITY_NAME = "tipoResposta";

    private final TipoRespostaService tipoRespostaService;

    private final TipoRespostaRepository tipoRespostaRepository;

    public TipoRespostaResource(TipoRespostaService tipoRespostaService, TipoRespostaRepository tipoRespostaRepository) {
        this.tipoRespostaService = tipoRespostaService;
        this.tipoRespostaRepository = tipoRespostaRepository;
    }

    /**
     * POST  /tipo-respostas : Create a new tipoResposta.
     *
     * @param tipoResposta the tipoResposta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoResposta, or with status 400 (Bad Request) if the tipoResposta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-respostas")
    @Timed
    public ResponseEntity<TipoResposta> createTipoResposta(@Valid @RequestBody TipoResposta tipoResposta) throws URISyntaxException {
        log.debug("REST request to save TipoResposta : {}", tipoResposta);
        tipoResposta.setResposta(MadreUtil.removeCaracteresEmBranco(tipoResposta.getResposta()));

        if (tipoResposta.getId() != null) {
            throw new BadRequestAlertException("A new tipoResposta cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if(tipoRespostaRepository.findOneByRespostaIgnoreCase(MadreUtil.removeCaracteresEmBranco(tipoResposta.getResposta())).isPresent()){
            throw new BadRequestAlertException("Enuciado já cadastrado", ENTITY_NAME, "enunciadoexists");

        }
        TipoResposta result = tipoRespostaService.save(tipoResposta);
        return ResponseEntity.created(new URI("/api/tipo-respostas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-respostas : Updates an existing tipoResposta.
     *
     * @param tipoResposta the tipoResposta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoResposta,
     * or with status 400 (Bad Request) if the tipoResposta is not valid,
     * or with status 500 (Internal Server Error) if the tipoResposta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-respostas")
    @Timed
    public ResponseEntity<TipoResposta> updateTipoResposta(@Valid @RequestBody TipoResposta tipoResposta) throws URISyntaxException {
        log.debug("REST request to update TipoResposta : {}", tipoResposta);
        tipoResposta.setResposta(MadreUtil.removeCaracteresEmBranco(tipoResposta.getResposta()));
        if (tipoResposta.getId() == null) {
            return createTipoResposta(tipoResposta);
        }

        if(tipoRespostaRepository.findOneByRespostaIgnoreCase(MadreUtil.removeCaracteresEmBranco(tipoResposta.getResposta())).isPresent()){
            throw new BadRequestAlertException("Enuciado já cadastrado", ENTITY_NAME, "enunciadoexists");

        }
        TipoResposta result = tipoRespostaService.save(tipoResposta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoResposta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-respostas : get all the tipoRespostas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoRespostas in body
     */
    @GetMapping("/tipo-respostas")
    @Timed
    public ResponseEntity<List<TipoResposta>> getAllTipoRespostas(Pageable pageable) {
        log.debug("REST request to get a page of TipoRespostas");
        Page<TipoResposta> page = tipoRespostaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-respostas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-respostas/:id : get the "id" tipoResposta.
     *
     * @param id the id of the tipoResposta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoResposta, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-respostas/{id}")
    @Timed
    public ResponseEntity<TipoResposta> getTipoResposta(@PathVariable Long id) {
        log.debug("REST request to get TipoResposta : {}", id);
        TipoResposta tipoResposta = tipoRespostaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoResposta));
    }

    /**
     * DELETE  /tipo-respostas/:id : delete the "id" tipoResposta.
     *
     * @param id the id of the tipoResposta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-respostas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoResposta(@PathVariable Long id) {
        log.debug("REST request to delete TipoResposta : {}", id);
        tipoRespostaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-respostas?query=:query : search for the tipoResposta corresponding
     * to the query.
     *
     * @param query the query of the tipoResposta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-respostas")
    @Timed
    public ResponseEntity<List<TipoResposta>> searchTipoRespostas(@RequestParam(defaultValue = "*") String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoRespostas for query {}", query);
        Page<TipoResposta> page = tipoRespostaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-respostas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/tipoResposta/exportacao/{tipoRelatorio}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public ResponseEntity<InputStreamResource> getRelatorioExportacao (@PathVariable String tipoRelatorio, @RequestParam String query){
        try{
            return tipoRespostaService.gerarRelatorioExportacao(tipoRelatorio,query);
        }catch(RelatorioException e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, RelatorioException.getCodeEntidade(), e.getMessage())).body(null);
        }
    }
}
