package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.SituacaoDeLeitoService;
import br.com.basis.madre.service.dto.SituacaoDeLeitoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SituacaoDeLeitoResource {

    private final Logger log = LoggerFactory.getLogger(SituacaoDeLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoSituacaoDeLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituacaoDeLeitoService situacaoDeLeitoService;

    /**
     * {@code POST  /situacao-de-leitos} : Create a new situacaoDeLeito.
     *
     * @param situacaoDeLeitoDTO the situacaoDeLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * situacaoDeLeitoDTO, or with status {@code 400 (Bad Request)} if the situacaoDeLeito has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situacoes-de-leito")
    public ResponseEntity<SituacaoDeLeitoDTO> createSituacaoDeLeito(
        @Valid @RequestBody SituacaoDeLeitoDTO situacaoDeLeitoDTO) throws URISyntaxException {
        log.debug("REST request to save SituacaoDeLeito : {}", situacaoDeLeitoDTO);
        if (situacaoDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new situacaoDeLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        SituacaoDeLeitoDTO result = situacaoDeLeitoService.save(situacaoDeLeitoDTO);
        return ResponseEntity.created(new URI("/api/situacao-de-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situacao-de-leitos} : Updates an existing situacaoDeLeito.
     *
     * @param situacaoDeLeitoDTO the situacaoDeLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * situacaoDeLeitoDTO, or with status {@code 400 (Bad Request)} if the situacaoDeLeitoDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the situacaoDeLeitoDTO couldn't
     * be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situacoes-de-leito")
    public ResponseEntity<SituacaoDeLeitoDTO> updateSituacaoDeLeito(
        @Valid @RequestBody SituacaoDeLeitoDTO situacaoDeLeitoDTO) throws URISyntaxException {
        log.debug("REST request to update SituacaoDeLeito : {}", situacaoDeLeitoDTO);
        if (situacaoDeLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SituacaoDeLeitoDTO result = situacaoDeLeitoService.save(situacaoDeLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                situacaoDeLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /situacao-de-leitos} : get all the situacaoDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * situacaoDeLeitos in body.
     */
    @GetMapping("/situacoes-de-leito")
    public ResponseEntity<List<SituacaoDeLeitoDTO>> getAllSituacaoDeLeitos(Pageable pageable) {
        log.debug("REST request to get a page of SituacaoDeLeitos");
        Page<SituacaoDeLeitoDTO> page = situacaoDeLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situacao-de-leitos/:id} : get the "id" situacaoDeLeito.
     *
     * @param id the id of the situacaoDeLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * situacaoDeLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situacoes-de-leito/{id}")
    public ResponseEntity<SituacaoDeLeitoDTO> getSituacaoDeLeito(@PathVariable Long id) {
        log.debug("REST request to get SituacaoDeLeito : {}", id);
        Optional<SituacaoDeLeitoDTO> situacaoDeLeitoDTO = situacaoDeLeitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situacaoDeLeitoDTO);
    }

    /**
     * {@code DELETE  /situacao-de-leitos/:id} : delete the "id" situacaoDeLeito.
     *
     * @param id the id of the situacaoDeLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situacoes-de-leito/{id}")
    public ResponseEntity<Void> deleteSituacaoDeLeito(@PathVariable Long id) {
        log.debug("REST request to delete SituacaoDeLeito : {}", id);
        situacaoDeLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/situacao-de-leitos?query=:query} : search for the situacaoDeLeito
     * corresponding to the query.
     *
     * @param query    the query of the situacaoDeLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/situacoes-de-leito")
    public ResponseEntity<List<SituacaoDeLeitoDTO>> searchSituacaoDeLeitos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SituacaoDeLeitos for query {}", query);
        Page<SituacaoDeLeitoDTO> page = situacaoDeLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
