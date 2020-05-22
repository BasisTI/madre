package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.InternacaoService;
import br.com.basis.madre.service.dto.InternacaoDTO;
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
public class InternacaoResource {

    private final Logger log = LoggerFactory.getLogger(InternacaoResource.class);

    private static final String ENTITY_NAME = "internacaoInternacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InternacaoService internacaoService;

    @PostMapping("/internacoes")
    public ResponseEntity<InternacaoDTO> createInternacao(
        @Valid @RequestBody InternacaoDTO internacaoDTO) throws URISyntaxException {
        if (internacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new internacao cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        InternacaoDTO result = internacaoService.save(internacaoDTO);
        return ResponseEntity.created(new URI("/api/internacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /internacaos} : Updates an existing internacao.
     *
     * @param internacaoDTO the internacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * internacaoDTO, or with status {@code 400 (Bad Request)} if the internacaoDTO is not valid, or
     * with status {@code 500 (Internal Server Error)} if the internacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/internacoes")
    public ResponseEntity<InternacaoDTO> updateInternacao(
        @Valid @RequestBody InternacaoDTO internacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Internacao : {}", internacaoDTO);
        if (internacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InternacaoDTO result = internacaoService.save(internacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                internacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /internacaos} : get all the internacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of internacaos
     * in body.
     */
    @GetMapping("/internacoes")
    public ResponseEntity<List<InternacaoDTO>> getAllInternacaos(Pageable pageable) {
        log.debug("REST request to get a page of Internacaos");
        Page<InternacaoDTO> page = internacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /internacaos/:id} : get the "id" internacao.
     *
     * @param id the id of the internacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * internacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/internacoes/{id}")
    public ResponseEntity<InternacaoDTO> getInternacao(@PathVariable Long id) {
        log.debug("REST request to get Internacao : {}", id);
        Optional<InternacaoDTO> internacaoDTO = internacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(internacaoDTO);
    }

    /**
     * {@code DELETE  /internacaos/:id} : delete the "id" internacao.
     *
     * @param id the id of the internacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/internacoes/{id}")
    public ResponseEntity<Void> deleteInternacao(@PathVariable Long id) {
        log.debug("REST request to delete Internacao : {}", id);
        internacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/internacaos?query=:query} : search for the internacao corresponding
     * to the query.
     *
     * @param query    the query of the internacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/internacoes")
    public ResponseEntity<List<InternacaoDTO>> searchInternacaos(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of Internacaos for query {}", query);
        Page<InternacaoDTO> page = internacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
