package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.OrigemDaReservaDeLeitoService;
import br.com.basis.madre.service.dto.OrigemDaReservaDeLeitoDTO;
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
public class OrigemDaReservaDeLeitoResource {

    private final Logger log = LoggerFactory.getLogger(OrigemDaReservaDeLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoOrigemDaReservaDeLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrigemDaReservaDeLeitoService origemDaReservaDeLeitoService;

    /**
     * {@code POST  /origem-da-reserva-de-leitos} : Create a new origemDaReservaDeLeito.
     *
     * @param origemDaReservaDeLeitoDTO the origemDaReservaDeLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * origemDaReservaDeLeitoDTO, or with status {@code 400 (Bad Request)} if the
     * origemDaReservaDeLeito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/origens-da-reserva-de-leito")
    public ResponseEntity<OrigemDaReservaDeLeitoDTO> createOrigemDaReservaDeLeito(
        @Valid @RequestBody OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrigemDaReservaDeLeito : {}", origemDaReservaDeLeitoDTO);
        if (origemDaReservaDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new origemDaReservaDeLeito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrigemDaReservaDeLeitoDTO result = origemDaReservaDeLeitoService
            .save(origemDaReservaDeLeitoDTO);
        return ResponseEntity.created(new URI("/api/origem-da-reserva-de-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /origem-da-reserva-de-leitos} : Updates an existing origemDaReservaDeLeito.
     *
     * @param origemDaReservaDeLeitoDTO the origemDaReservaDeLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * origemDaReservaDeLeitoDTO, or with status {@code 400 (Bad Request)} if the
     * origemDaReservaDeLeitoDTO is not valid, or with status {@code 500 (Internal Server Error)} if
     * the origemDaReservaDeLeitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/origens-da-reserva-de-leito")
    public ResponseEntity<OrigemDaReservaDeLeitoDTO> updateOrigemDaReservaDeLeito(
        @Valid @RequestBody OrigemDaReservaDeLeitoDTO origemDaReservaDeLeitoDTO)
        throws URISyntaxException {
        log.debug("REST request to update OrigemDaReservaDeLeito : {}", origemDaReservaDeLeitoDTO);
        if (origemDaReservaDeLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrigemDaReservaDeLeitoDTO result = origemDaReservaDeLeitoService
            .save(origemDaReservaDeLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                origemDaReservaDeLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /origem-da-reserva-de-leitos} : get all the origemDaReservaDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * origemDaReservaDeLeitos in body.
     */
    @GetMapping("/origens-da-reserva-de-leito")
    public ResponseEntity<List<OrigemDaReservaDeLeitoDTO>> getAllOrigemDaReservaDeLeitos(
        Pageable pageable) {
        log.debug("REST request to get a page of OrigemDaReservaDeLeitos");
        Page<OrigemDaReservaDeLeitoDTO> page = origemDaReservaDeLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /origem-da-reserva-de-leitos/:id} : get the "id" origemDaReservaDeLeito.
     *
     * @param id the id of the origemDaReservaDeLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * origemDaReservaDeLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/origens-da-reserva-de-leito/{id}")
    public ResponseEntity<OrigemDaReservaDeLeitoDTO> getOrigemDaReservaDeLeito(
        @PathVariable Long id) {
        log.debug("REST request to get OrigemDaReservaDeLeito : {}", id);
        Optional<OrigemDaReservaDeLeitoDTO> origemDaReservaDeLeitoDTO = origemDaReservaDeLeitoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(origemDaReservaDeLeitoDTO);
    }

    /**
     * {@code DELETE  /origem-da-reserva-de-leitos/:id} : delete the "id" origemDaReservaDeLeito.
     *
     * @param id the id of the origemDaReservaDeLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/origens-da-reserva-de-leito/{id}")
    public ResponseEntity<Void> deleteOrigemDaReservaDeLeito(@PathVariable Long id) {
        log.debug("REST request to delete OrigemDaReservaDeLeito : {}", id);
        origemDaReservaDeLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/origem-da-reserva-de-leitos?query=:query} : search for the
     * origemDaReservaDeLeito corresponding to the query.
     *
     * @param query    the query of the origemDaReservaDeLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/origens-da-reserva-de-leito")
    public ResponseEntity<List<OrigemDaReservaDeLeitoDTO>> searchOrigemDaReservaDeLeitos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrigemDaReservaDeLeitos for query {}",
            query);
        Page<OrigemDaReservaDeLeitoDTO> page = origemDaReservaDeLeitoService
            .search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
