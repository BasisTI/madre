package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.TipoDoEventoLeitoService;
import br.com.basis.madre.service.dto.TipoDoEventoLeitoDTO;
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
public class TipoDoEventoLeitoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDoEventoLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoTipoDoEventoLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDoEventoLeitoService tipoDoEventoLeitoService;

    /**
     * {@code POST  /tipo-do-evento-leitos} : Create a new tipoDoEventoLeito.
     *
     * @param tipoDoEventoLeitoDTO the tipoDoEventoLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * tipoDoEventoLeitoDTO, or with status {@code 400 (Bad Request)} if the tipoDoEventoLeito has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipos-de-eventos-de-leito")
    public ResponseEntity<TipoDoEventoLeitoDTO> createTipoDoEventoLeito(
        @Valid @RequestBody TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDoEventoLeito : {}", tipoDoEventoLeitoDTO);
        if (tipoDoEventoLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDoEventoLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        TipoDoEventoLeitoDTO result = tipoDoEventoLeitoService.save(tipoDoEventoLeitoDTO);
        return ResponseEntity.created(new URI("/api/tipo-do-evento-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-do-evento-leitos} : Updates an existing tipoDoEventoLeito.
     *
     * @param tipoDoEventoLeitoDTO the tipoDoEventoLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * tipoDoEventoLeitoDTO, or with status {@code 400 (Bad Request)} if the tipoDoEventoLeitoDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the tipoDoEventoLeitoDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipos-de-eventos-de-leito")
    public ResponseEntity<TipoDoEventoLeitoDTO> updateTipoDoEventoLeito(
        @Valid @RequestBody TipoDoEventoLeitoDTO tipoDoEventoLeitoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDoEventoLeito : {}", tipoDoEventoLeitoDTO);
        if (tipoDoEventoLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDoEventoLeitoDTO result = tipoDoEventoLeitoService.save(tipoDoEventoLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                tipoDoEventoLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-do-evento-leitos} : get all the tipoDoEventoLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * tipoDoEventoLeitos in body.
     */
    @GetMapping("/tipos-de-eventos-de-leito")
    public ResponseEntity<List<TipoDoEventoLeitoDTO>> getAllTipoDoEventoLeitos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDoEventoLeitos");
        Page<TipoDoEventoLeitoDTO> page = tipoDoEventoLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-do-evento-leitos/:id} : get the "id" tipoDoEventoLeito.
     *
     * @param id the id of the tipoDoEventoLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * tipoDoEventoLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipos-de-eventos-de-leito/{id}")
    public ResponseEntity<TipoDoEventoLeitoDTO> getTipoDoEventoLeito(@PathVariable Long id) {
        log.debug("REST request to get TipoDoEventoLeito : {}", id);
        Optional<TipoDoEventoLeitoDTO> tipoDoEventoLeitoDTO = tipoDoEventoLeitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDoEventoLeitoDTO);
    }

    /**
     * {@code DELETE  /tipo-do-evento-leitos/:id} : delete the "id" tipoDoEventoLeito.
     *
     * @param id the id of the tipoDoEventoLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipos-de-eventos-de-leito/{id}")
    public ResponseEntity<Void> deleteTipoDoEventoLeito(@PathVariable Long id) {
        log.debug("REST request to delete TipoDoEventoLeito : {}", id);
        tipoDoEventoLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-do-evento-leitos?query=:query} : search for the
     * tipoDoEventoLeito corresponding to the query.
     *
     * @param query    the query of the tipoDoEventoLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipos-de-eventos-de-leito")
    public ResponseEntity<List<TipoDoEventoLeitoDTO>> searchTipoDoEventoLeitos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDoEventoLeitos for query {}", query);
        Page<TipoDoEventoLeitoDTO> page = tipoDoEventoLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
