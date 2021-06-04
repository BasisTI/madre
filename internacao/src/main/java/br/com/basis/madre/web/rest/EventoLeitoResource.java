package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.EventoLeitoService;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import br.com.basis.madre.service.dto.EventoLeitoDTO;
import br.com.basis.madre.service.dto.LiberacaoDeLeitoDTO;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import br.com.basis.madre.service.projection.EventoCalendario;
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
import org.springframework.http.HttpStatus;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EventoLeitoResource {

    private final Logger log = LoggerFactory.getLogger(EventoLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoEventoLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoLeitoService eventoLeitoService;

    @PostMapping("/reservas")
    public ResponseEntity<ReservaDeLeitoDTO> reservarLeito(
        @RequestBody @Valid ReservaDeLeitoDTO reservaDeLeitoDTO) {
        return ResponseEntity.ok(eventoLeitoService.reservarLeito(reservaDeLeitoDTO));
    }

    @PostMapping("/bloqueios")
    public ResponseEntity<BloqueioDeLeitoDTO> bloquearLeito(
        @RequestBody @Valid BloqueioDeLeitoDTO bloqueioDeLeitoDTO) {
        return ResponseEntity.ok(eventoLeitoService.bloquearLeito(bloqueioDeLeitoDTO));
    }

    @PostMapping("/liberacoes")
    public ResponseEntity<LiberacaoDeLeitoDTO> liberarLeito(
        @RequestBody @Valid LiberacaoDeLeitoDTO liberacaoDeLeitoDTO) {
        Long leitoId = liberacaoDeLeitoDTO.getLeitoId();
        eventoLeitoService.desocuparLeito(leitoId);
        return ResponseEntity.ok(eventoLeitoService.liberarLeito(liberacaoDeLeitoDTO));
    }

    @GetMapping("/eventos-calendario")
    public ResponseEntity<List<EventoCalendario>> obterEventosCalendario(Pageable pageable) {
        Page<EventoCalendario> page = eventoLeitoService.obterEventosCalendario(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code PUT  /evento-leitos} : Updates an existing eventoLeito.
     *
     * @param eventoLeitoDTO the eventoLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * eventoLeitoDTO, or with status {@code 400 (Bad Request)} if the eventoLeitoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventoLeitoDTO couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eventos-de-leito")
    public ResponseEntity<EventoLeitoDTO> updateEventoLeito(
        @Valid @RequestBody EventoLeitoDTO eventoLeitoDTO) throws URISyntaxException {
        log.debug("REST request to update EventoLeito : {}", eventoLeitoDTO);
        if (eventoLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventoLeitoDTO result = eventoLeitoService.save(eventoLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                eventoLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /evento-leitos} : get all the eventoLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventoLeitos
     * in body.
     */
    @GetMapping("/eventos-de-leito")
    public ResponseEntity<List<EventoLeitoDTO>> getAllEventoLeitos(Pageable pageable) {
        log.debug("REST request to get a page of EventoLeitos");
        Page<EventoLeitoDTO> page = eventoLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evento-leitos/:id} : get the "id" eventoLeito.
     *
     * @param id the id of the eventoLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * eventoLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eventos-de-leito/{id}")
    public ResponseEntity<EventoLeitoDTO> getEventoLeito(@PathVariable Long id) {
        log.debug("REST request to get EventoLeito : {}", id);
        Optional<EventoLeitoDTO> eventoLeitoDTO = eventoLeitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventoLeitoDTO);
    }

    /**
     * {@code DELETE  /evento-leitos/:id} : delete the "id" eventoLeito.
     *
     * @param id the id of the eventoLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eventos-de-leito/{id}")
    public ResponseEntity<Void> deleteEventoLeito(@PathVariable Long id) {
        log.debug("REST request to delete EventoLeito : {}", id);
        eventoLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/evento-leitos?query=:query} : search for the eventoLeito
     * corresponding to the query.
     *
     * @param query    the query of the eventoLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/eventos-de-leito")
    public ResponseEntity<List<EventoLeitoDTO>> searchEventoLeitos(@RequestParam String query,
                                                                   Pageable pageable) {
        log.debug("REST request to search for a page of EventoLeitos for query {}", query);
        Page<EventoLeitoDTO> page = eventoLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
