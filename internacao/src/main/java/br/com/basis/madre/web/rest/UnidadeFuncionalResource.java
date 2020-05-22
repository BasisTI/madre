package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.UnidadeFuncionalService;
import br.com.basis.madre.service.dto.UnidadeFuncionalDTO;
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
public class UnidadeFuncionalResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeFuncionalResource.class);

    private static final String ENTITY_NAME = "internacaoUnidadeFuncional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeFuncionalService unidadeFuncionalService;

    /**
     * {@code POST  /unidade-funcionals} : Create a new unidadeFuncional.
     *
     * @param unidadeFuncionalDTO the unidadeFuncionalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * unidadeFuncionalDTO, or with status {@code 400 (Bad Request)} if the unidadeFuncional has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidades-funcionais")
    public ResponseEntity<UnidadeFuncionalDTO> createUnidadeFuncional(
        @Valid @RequestBody UnidadeFuncionalDTO unidadeFuncionalDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadeFuncional : {}", unidadeFuncionalDTO);
        if (unidadeFuncionalDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadeFuncional cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        UnidadeFuncionalDTO result = unidadeFuncionalService.save(unidadeFuncionalDTO);
        return ResponseEntity.created(new URI("/api/unidade-funcionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-funcionals} : Updates an existing unidadeFuncional.
     *
     * @param unidadeFuncionalDTO the unidadeFuncionalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * unidadeFuncionalDTO, or with status {@code 400 (Bad Request)} if the unidadeFuncionalDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the unidadeFuncionalDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidades-funcionais")
    public ResponseEntity<UnidadeFuncionalDTO> updateUnidadeFuncional(
        @Valid @RequestBody UnidadeFuncionalDTO unidadeFuncionalDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadeFuncional : {}", unidadeFuncionalDTO);
        if (unidadeFuncionalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeFuncionalDTO result = unidadeFuncionalService.save(unidadeFuncionalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                unidadeFuncionalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-funcionals} : get all the unidadeFuncionals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * unidadeFuncionals in body.
     */
    @GetMapping("/unidades-funcionais")
    public ResponseEntity<List<UnidadeFuncionalDTO>> getAllUnidadeFuncionals(Pageable pageable) {
        log.debug("REST request to get a page of UnidadeFuncionals");
        Page<UnidadeFuncionalDTO> page = unidadeFuncionalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidade-funcionals/:id} : get the "id" unidadeFuncional.
     *
     * @param id the id of the unidadeFuncionalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * unidadeFuncionalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidades-funcionais{id}")
    public ResponseEntity<UnidadeFuncionalDTO> getUnidadeFuncional(@PathVariable Long id) {
        log.debug("REST request to get UnidadeFuncional : {}", id);
        Optional<UnidadeFuncionalDTO> unidadeFuncionalDTO = unidadeFuncionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeFuncionalDTO);
    }

    /**
     * {@code DELETE  /unidade-funcionals/:id} : delete the "id" unidadeFuncional.
     *
     * @param id the id of the unidadeFuncionalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidades-funcionais/{id}")
    public ResponseEntity<Void> deleteUnidadeFuncional(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeFuncional : {}", id);
        unidadeFuncionalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-funcionals?query=:query} : search for the unidadeFuncional
     * corresponding to the query.
     *
     * @param query    the query of the unidadeFuncional search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidades-funcionais")
    public ResponseEntity<List<UnidadeFuncionalDTO>> searchUnidadeFuncionals(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeFuncionals for query {}", query);
        Page<UnidadeFuncionalDTO> page = unidadeFuncionalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
