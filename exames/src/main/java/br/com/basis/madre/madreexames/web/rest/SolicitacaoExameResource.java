package br.com.basis.madre.madreexames.web.rest;

import br.com.basis.madre.madreexames.domain.SolicitacaoExame;
import br.com.basis.madre.madreexames.service.ExportarSolicitacaoService;
import br.com.basis.madre.madreexames.service.SolicitacaoExameService;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameCompletoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.madreexames.service.dto.SolicitacaoExameDTO;

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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.madreexames.domain.SolicitacaoExame}.
 */
@RestController
@RequestMapping("/api")
public class SolicitacaoExameResource {

    private final Logger log = LoggerFactory.getLogger(SolicitacaoExameResource.class);

    private static final String ENTITY_NAME = "madreexamesSolicitacaoExame";
    private final ExportarSolicitacaoService exportarSolicitacaoService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitacaoExameService solicitacaoExameService;

    public SolicitacaoExameResource(ExportarSolicitacaoService exportarSolicitacaoService, SolicitacaoExameService solicitacaoExameService) {
        this.exportarSolicitacaoService = exportarSolicitacaoService;
        this.solicitacaoExameService = solicitacaoExameService;
    }

    /**
     * {@code POST  /solicitacao-exames} : Create a new solicitacaoExame.
     *
     * @param solicitacaoExameDTO the solicitacaoExameDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitacaoExameDTO, or with status {@code 400 (Bad Request)} if the solicitacaoExame has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitacao-exames")
    public ResponseEntity<SolicitacaoExameCompletoDTO> createSolicitacaoExame(@Valid @RequestBody SolicitacaoExameCompletoDTO solicitacaoExameCompletoDTO) throws URISyntaxException {
        log.debug("REST request to save SolicitacaoExame : {}", solicitacaoExameCompletoDTO);
        if (solicitacaoExameCompletoDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitacaoExame cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitacaoExameCompletoDTO result = solicitacaoExameService.save(solicitacaoExameCompletoDTO);
        return ResponseEntity.created(new URI("/api/solicitacao-exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitacao-exames} : Updates an existing solicitacaoExame.
     *
     * @param solicitacaoExameDTO the solicitacaoExameDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitacaoExameDTO,
     * or with status {@code 400 (Bad Request)} if the solicitacaoExameDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitacaoExameDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitacao-exames")
    public ResponseEntity<SolicitacaoExameCompletoDTO> updateSolicitacaoExame(@Valid @RequestBody SolicitacaoExameCompletoDTO solicitacaoExameCompletoDTO) throws URISyntaxException {
        log.debug("REST request to update SolicitacaoExame : {}", solicitacaoExameCompletoDTO);
        if (solicitacaoExameCompletoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitacaoExameCompletoDTO result = solicitacaoExameService.save(solicitacaoExameCompletoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, solicitacaoExameCompletoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicitacao-exames} : get all the solicitacaoExames.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitacaoExames in body.
     */
    @GetMapping("/solicitacao-exames")
    public ResponseEntity<List<SolicitacaoExameDTO>> getAllSolicitacaoExames(Pageable pageable) {
        log.debug("REST request to get a page of SolicitacaoExames");
        Page<SolicitacaoExameDTO> page = solicitacaoExameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solicitacao-exames/:id} : get the "id" solicitacaoExame.
     *
     * @param id the id of the solicitacaoExameDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitacaoExameDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitacao-exames/{id}")
    public ResponseEntity<SolicitacaoExameDTO> getSolicitacaoExame(@PathVariable Long id) {
        log.debug("REST request to get SolicitacaoExame : {}", id);
        Optional<SolicitacaoExameDTO> solicitacaoExameDTO = solicitacaoExameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitacaoExameDTO);
    }

    /**
     * {@code DELETE  /solicitacao-exames/:id} : delete the "id" solicitacaoExame.
     *
     * @param id the id of the solicitacaoExameDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitacao-exames/{id}")
    public ResponseEntity<Void> deleteSolicitacaoExame(@PathVariable Long id) {
        log.debug("REST request to delete SolicitacaoExame : {}", id);
        solicitacaoExameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/solicitacao-exames?query=:query} : search for the solicitacaoExame corresponding
     * to the query.
     *
     * @param query the query of the solicitacaoExame search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/solicitacao-exames")
    public ResponseEntity<List<SolicitacaoExameDTO>> searchSolicitacaoExames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolicitacaoExames for query {}", query);
        Page<SolicitacaoExameDTO> page = solicitacaoExameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    @GetMapping("/_search/solicitacoes-exames")
    public ResponseEntity<List<SolicitacaoExame>> obterTodasSilicitacoes(Pageable pageable,
                                                                         @RequestParam(name = "id", required = false) String id,
                                                                         @RequestParam(name = "pedidoPrimeiroExame", required = false) String pedidoPrimeiroExame,
                                                                         @RequestParam(name = "usoAntimicrobianos24h", required = false) String usoAntimicrobianos24h
    ) {
        log.debug("Request REST para obter uma página de solicitações de exame.");
        Page<SolicitacaoExame> page = solicitacaoExameService.filtraSolicitacaoExame(pageable, id, pedidoPrimeiroExame, usoAntimicrobianos24h);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/solicitacao-exames/exportar")
    public void exportSolicitacaoExame(HttpServletResponse response) throws IOException {
        exportarSolicitacaoService.exportar(response);
    }
}
