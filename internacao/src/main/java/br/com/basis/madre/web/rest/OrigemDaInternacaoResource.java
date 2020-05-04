package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.OrigemDaInternacaoService;
import br.com.basis.madre.service.dto.OrigemDaInternacaoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrigemDaInternacaoResource {

    private final Logger log = LoggerFactory.getLogger(OrigemDaInternacaoResource.class);

    private static final String ENTITY_NAME = "internacaoOrigemDaInternacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrigemDaInternacaoService origemDaInternacaoService;

    /**
     * {@code POST  /origem-da-internacaos} : Create a new origemDaInternacao.
     *
     * @param origemDaInternacaoDTO the origemDaInternacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * origemDaInternacaoDTO, or with status {@code 400 (Bad Request)} if the origemDaInternacao has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origens-das-internacoes")
    public ResponseEntity<OrigemDaInternacaoDTO> createOrigemDaInternacao(
        @Valid @RequestBody OrigemDaInternacaoDTO origemDaInternacaoDTO) throws URISyntaxException {
        log.debug("REST request to save OrigemDaInternacao : {}", origemDaInternacaoDTO);
        if (origemDaInternacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new origemDaInternacao cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        OrigemDaInternacaoDTO result = origemDaInternacaoService.save(origemDaInternacaoDTO);
        return ResponseEntity.created(new URI("/api/origem-da-internacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origem-da-internacaos} : Updates an existing origemDaInternacao.
     *
     * @param origemDaInternacaoDTO the origemDaInternacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * origemDaInternacaoDTO, or with status {@code 400 (Bad Request)} if the origemDaInternacaoDTO
     * is not valid, or with status {@code 500 (Internal Server Error)} if the origemDaInternacaoDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origens-das-internacoes")
    public ResponseEntity<OrigemDaInternacaoDTO> updateOrigemDaInternacao(
        @Valid @RequestBody OrigemDaInternacaoDTO origemDaInternacaoDTO) throws URISyntaxException {
        log.debug("REST request to update OrigemDaInternacao : {}", origemDaInternacaoDTO);
        if (origemDaInternacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrigemDaInternacaoDTO result = origemDaInternacaoService.save(origemDaInternacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                origemDaInternacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /origem-da-internacaos} : get all the origemDaInternacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * origemDaInternacaos in body.
     */
    @GetMapping("/origens-das-internacoes")
    public ResponseEntity<List<OrigemDaInternacaoDTO>> getAllOrigemDaInternacaos(
        OrigemDaInternacaoDTO origemDaInternacaoDTO,
        Pageable pageable) {
        log.debug("REST request to get a page of OrigemDaInternacaos");
        Page<OrigemDaInternacaoDTO> page = origemDaInternacaoService.findAll(origemDaInternacaoDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /origem-da-internacaos/:id} : get the "id" origemDaInternacao.
     *
     * @param id the id of the origemDaInternacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * origemDaInternacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origens-das-internacoes/{id}")
    public ResponseEntity<OrigemDaInternacaoDTO> getOrigemDaInternacao(@PathVariable Long id) {
        log.debug("REST request to get OrigemDaInternacao : {}", id);
        Optional<OrigemDaInternacaoDTO> origemDaInternacaoDTO = origemDaInternacaoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(origemDaInternacaoDTO);
    }

    /**
     * {@code DELETE  /origem-da-internacaos/:id} : delete the "id" origemDaInternacao.
     *
     * @param id the id of the origemDaInternacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origens-das-internacoes/{id}")
    public ResponseEntity<Void> deleteOrigemDaInternacao(@PathVariable Long id) {
        log.debug("REST request to delete OrigemDaInternacao : {}", id);
        origemDaInternacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/origem-da-internacaos?query=:query} : search for the
     * origemDaInternacao corresponding to the query.
     *
     * @param query    the query of the origemDaInternacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/origens-das-internacoes")
    public ResponseEntity<List<OrigemDaInternacaoDTO>> searchOrigemDaInternacaos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrigemDaInternacaos for query {}", query);
        Page<OrigemDaInternacaoDTO> page = origemDaInternacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
