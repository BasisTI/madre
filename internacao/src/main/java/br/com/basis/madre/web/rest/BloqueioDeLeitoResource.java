package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.BloqueioDeLeitoService;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
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
public class BloqueioDeLeitoResource {

    private final Logger log = LoggerFactory.getLogger(BloqueioDeLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoBloqueioDeLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BloqueioDeLeitoService bloqueioDeLeitoService;

    /**
     * {@code POST  /bloqueio-de-leitos} : Create a new bloqueioDeLeito.
     *
     * @param bloqueioDeLeitoDTO the bloqueioDeLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * bloqueioDeLeitoDTO, or with status {@code 400 (Bad Request)} if the bloqueioDeLeito has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bloqueio-de-leitos")
    public ResponseEntity<BloqueioDeLeitoDTO> createBloqueioDeLeito(
        @Valid @RequestBody BloqueioDeLeitoDTO bloqueioDeLeitoDTO) throws URISyntaxException {
        log.debug("REST request to save BloqueioDeLeito : {}", bloqueioDeLeitoDTO);
        if (bloqueioDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new bloqueioDeLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        BloqueioDeLeitoDTO result = bloqueioDeLeitoService.save(bloqueioDeLeitoDTO);
        return ResponseEntity.created(new URI("/api/bloqueio-de-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bloqueio-de-leitos} : Updates an existing bloqueioDeLeito.
     *
     * @param bloqueioDeLeitoDTO the bloqueioDeLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * bloqueioDeLeitoDTO, or with status {@code 400 (Bad Request)} if the bloqueioDeLeitoDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the bloqueioDeLeitoDTO couldn't
     * be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bloqueio-de-leitos")
    public ResponseEntity<BloqueioDeLeitoDTO> updateBloqueioDeLeito(
        @Valid @RequestBody BloqueioDeLeitoDTO bloqueioDeLeitoDTO) throws URISyntaxException {
        log.debug("REST request to update BloqueioDeLeito : {}", bloqueioDeLeitoDTO);
        if (bloqueioDeLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BloqueioDeLeitoDTO result = bloqueioDeLeitoService.save(bloqueioDeLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                bloqueioDeLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bloqueio-de-leitos} : get all the bloqueioDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * bloqueioDeLeitos in body.
     */
    @GetMapping("/bloqueio-de-leitos")
    public ResponseEntity<List<BloqueioDeLeitoDTO>> getAllBloqueioDeLeitos(Pageable pageable) {
        log.debug("REST request to get a page of BloqueioDeLeitos");
        Page<BloqueioDeLeitoDTO> page = bloqueioDeLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bloqueio-de-leitos/:id} : get the "id" bloqueioDeLeito.
     *
     * @param id the id of the bloqueioDeLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * bloqueioDeLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bloqueio-de-leitos/{id}")
    public ResponseEntity<BloqueioDeLeitoDTO> getBloqueioDeLeito(@PathVariable Long id) {
        log.debug("REST request to get BloqueioDeLeito : {}", id);
        Optional<BloqueioDeLeitoDTO> bloqueioDeLeitoDTO = bloqueioDeLeitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bloqueioDeLeitoDTO);
    }

    /**
     * {@code DELETE  /bloqueio-de-leitos/:id} : delete the "id" bloqueioDeLeito.
     *
     * @param id the id of the bloqueioDeLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bloqueio-de-leitos/{id}")
    public ResponseEntity<Void> deleteBloqueioDeLeito(@PathVariable Long id) {
        log.debug("REST request to delete BloqueioDeLeito : {}", id);
        bloqueioDeLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bloqueio-de-leitos?query=:query} : search for the bloqueioDeLeito
     * corresponding to the query.
     *
     * @param query    the query of the bloqueioDeLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/bloqueio-de-leitos")
    public ResponseEntity<List<BloqueioDeLeitoDTO>> searchBloqueioDeLeitos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BloqueioDeLeitos for query {}", query);
        Page<BloqueioDeLeitoDTO> page = bloqueioDeLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
