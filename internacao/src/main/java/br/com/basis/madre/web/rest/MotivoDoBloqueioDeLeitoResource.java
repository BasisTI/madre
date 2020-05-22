package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.MotivoDoBloqueioDeLeitoService;
import br.com.basis.madre.service.dto.MotivoDoBloqueioDeLeitoDTO;
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
public class MotivoDoBloqueioDeLeitoResource {

    private final Logger log = LoggerFactory.getLogger(MotivoDoBloqueioDeLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoMotivoDoBloqueioDeLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivoDoBloqueioDeLeitoService motivoDoBloqueioDeLeitoService;

    /**
     * {@code POST  /motivo-do-bloqueio-de-leitos} : Create a new motivoDoBloqueioDeLeito.
     *
     * @param motivoDoBloqueioDeLeitoDTO the motivoDoBloqueioDeLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * motivoDoBloqueioDeLeitoDTO, or with status {@code 400 (Bad Request)} if the
     * motivoDoBloqueioDeLeito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivos-do-bloqueio-de-leito")
    public ResponseEntity<MotivoDoBloqueioDeLeitoDTO> createMotivoDoBloqueioDeLeito(
        @Valid @RequestBody MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO)
        throws URISyntaxException {
        log.debug("REST request to save MotivoDoBloqueioDeLeito : {}", motivoDoBloqueioDeLeitoDTO);
        if (motivoDoBloqueioDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new motivoDoBloqueioDeLeito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotivoDoBloqueioDeLeitoDTO result = motivoDoBloqueioDeLeitoService
            .save(motivoDoBloqueioDeLeitoDTO);
        return ResponseEntity
            .created(new URI("/api/motivo-do-bloqueio-de-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motivo-do-bloqueio-de-leitos} : Updates an existing motivoDoBloqueioDeLeito.
     *
     * @param motivoDoBloqueioDeLeitoDTO the motivoDoBloqueioDeLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * motivoDoBloqueioDeLeitoDTO, or with status {@code 400 (Bad Request)} if the
     * motivoDoBloqueioDeLeitoDTO is not valid, or with status {@code 500 (Internal Server Error)}
     * if the motivoDoBloqueioDeLeitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivos-do-bloqueio-de-leito")
    public ResponseEntity<MotivoDoBloqueioDeLeitoDTO> updateMotivoDoBloqueioDeLeito(
        @Valid @RequestBody MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO)
        throws URISyntaxException {
        log.debug("REST request to update MotivoDoBloqueioDeLeito : {}",
            motivoDoBloqueioDeLeitoDTO);
        if (motivoDoBloqueioDeLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MotivoDoBloqueioDeLeitoDTO result = motivoDoBloqueioDeLeitoService
            .save(motivoDoBloqueioDeLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                motivoDoBloqueioDeLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /motivo-do-bloqueio-de-leitos} : get all the motivoDoBloqueioDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * motivoDoBloqueioDeLeitos in body.
     */
    @GetMapping("/motivos-do-bloqueio-de-leito")
    public ResponseEntity<List<MotivoDoBloqueioDeLeitoDTO>> getAllMotivoDoBloqueioDeLeitos(
        MotivoDoBloqueioDeLeitoDTO motivoDoBloqueioDeLeitoDTO,
        Pageable pageable) {
        return ResponseEntity.ok().body(motivoDoBloqueioDeLeitoService.findAll(motivoDoBloqueioDeLeitoDTO, pageable));
    }

    /**
     * {@code GET  /motivo-do-bloqueio-de-leitos/:id} : get the "id" motivoDoBloqueioDeLeito.
     *
     * @param id the id of the motivoDoBloqueioDeLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * motivoDoBloqueioDeLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivos-do-bloqueio-de-leito/{id}")
    public ResponseEntity<MotivoDoBloqueioDeLeitoDTO> getMotivoDoBloqueioDeLeito(
        @PathVariable Long id) {
        log.debug("REST request to get MotivoDoBloqueioDeLeito : {}", id);
        Optional<MotivoDoBloqueioDeLeitoDTO> motivoDoBloqueioDeLeitoDTO = motivoDoBloqueioDeLeitoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(motivoDoBloqueioDeLeitoDTO);
    }

    /**
     * {@code DELETE  /motivo-do-bloqueio-de-leitos/:id} : delete the "id" motivoDoBloqueioDeLeito.
     *
     * @param id the id of the motivoDoBloqueioDeLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivos-do-bloqueio-de-leito/{id}")
    public ResponseEntity<Void> deleteMotivoDoBloqueioDeLeito(@PathVariable Long id) {
        log.debug("REST request to delete MotivoDoBloqueioDeLeito : {}", id);
        motivoDoBloqueioDeLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/motivo-do-bloqueio-de-leitos?query=:query} : search for the
     * motivoDoBloqueioDeLeito corresponding to the query.
     *
     * @param query    the query of the motivoDoBloqueioDeLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/motivos-do-bloqueio-de-leito")
    public ResponseEntity<List<MotivoDoBloqueioDeLeitoDTO>> searchMotivoDoBloqueioDeLeitos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MotivoDoBloqueioDeLeitos for query {}",
            query);
        Page<MotivoDoBloqueioDeLeitoDTO> page = motivoDoBloqueioDeLeitoService
            .search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
