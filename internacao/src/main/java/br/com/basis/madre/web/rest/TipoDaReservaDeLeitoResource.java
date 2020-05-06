package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.TipoDaReservaDeLeitoService;
import br.com.basis.madre.service.dto.TipoDaReservaDeLeitoDTO;
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
public class TipoDaReservaDeLeitoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDaReservaDeLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoTipoDaReservaDeLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDaReservaDeLeitoService tipoDaReservaDeLeitoService;

    /**
     * {@code POST  /tipo-da-reserva-de-leitos} : Create a new tipoDaReservaDeLeito.
     *
     * @param tipoDaReservaDeLeitoDTO the tipoDaReservaDeLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * tipoDaReservaDeLeitoDTO, or with status {@code 400 (Bad Request)} if the tipoDaReservaDeLeito
     * has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipos-de-reserva-de-leito")
    public ResponseEntity<TipoDaReservaDeLeitoDTO> createTipoDaReservaDeLeito(
        @Valid @RequestBody TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO)
        throws URISyntaxException {
        log.debug("REST request to save TipoDaReservaDeLeito : {}", tipoDaReservaDeLeitoDTO);
        if (tipoDaReservaDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new tipoDaReservaDeLeito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDaReservaDeLeitoDTO result = tipoDaReservaDeLeitoService.save(tipoDaReservaDeLeitoDTO);
        return ResponseEntity.created(new URI("/api/tipo-da-reserva-de-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-da-reserva-de-leitos} : Updates an existing tipoDaReservaDeLeito.
     *
     * @param tipoDaReservaDeLeitoDTO the tipoDaReservaDeLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * tipoDaReservaDeLeitoDTO, or with status {@code 400 (Bad Request)} if the
     * tipoDaReservaDeLeitoDTO is not valid, or with status {@code 500 (Internal Server Error)} if
     * the tipoDaReservaDeLeitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipos-de-reserva-de-leito")
    public ResponseEntity<TipoDaReservaDeLeitoDTO> updateTipoDaReservaDeLeito(
        @Valid @RequestBody TipoDaReservaDeLeitoDTO tipoDaReservaDeLeitoDTO)
        throws URISyntaxException {
        log.debug("REST request to update TipoDaReservaDeLeito : {}", tipoDaReservaDeLeitoDTO);
        if (tipoDaReservaDeLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDaReservaDeLeitoDTO result = tipoDaReservaDeLeitoService.save(tipoDaReservaDeLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                tipoDaReservaDeLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-da-reserva-de-leitos} : get all the tipoDaReservaDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * tipoDaReservaDeLeitos in body.
     */
    @GetMapping("/tipos-de-reserva-de-leito")
    public ResponseEntity<List<TipoDaReservaDeLeitoDTO>> getAllTipoDaReservaDeLeitos(
        Pageable pageable) {
        log.debug("REST request to get a page of TipoDaReservaDeLeitos");
        Page<TipoDaReservaDeLeitoDTO> page = tipoDaReservaDeLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-da-reserva-de-leitos/:id} : get the "id" tipoDaReservaDeLeito.
     *
     * @param id the id of the tipoDaReservaDeLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * tipoDaReservaDeLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipos-de-reserva-de-leito/{id}")
    public ResponseEntity<TipoDaReservaDeLeitoDTO> getTipoDaReservaDeLeito(@PathVariable Long id) {
        log.debug("REST request to get TipoDaReservaDeLeito : {}", id);
        Optional<TipoDaReservaDeLeitoDTO> tipoDaReservaDeLeitoDTO = tipoDaReservaDeLeitoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDaReservaDeLeitoDTO);
    }

    /**
     * {@code DELETE  /tipo-da-reserva-de-leitos/:id} : delete the "id" tipoDaReservaDeLeito.
     *
     * @param id the id of the tipoDaReservaDeLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipos-de-reserva-de-leito/{id}")
    public ResponseEntity<Void> deleteTipoDaReservaDeLeito(@PathVariable Long id) {
        log.debug("REST request to delete TipoDaReservaDeLeito : {}", id);
        tipoDaReservaDeLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-da-reserva-de-leitos?query=:query} : search for the
     * tipoDaReservaDeLeito corresponding to the query.
     *
     * @param query    the query of the tipoDaReservaDeLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipos-de-reserva-de-leito")
    public ResponseEntity<List<TipoDaReservaDeLeitoDTO>> searchTipoDaReservaDeLeitos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDaReservaDeLeitos for query {}", query);
        Page<TipoDaReservaDeLeitoDTO> page = tipoDaReservaDeLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
