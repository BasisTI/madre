package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.enumeration.CodigoDeSituacaoDeLeito;
import br.com.basis.madre.service.EventoLeitoService;
import br.com.basis.madre.service.LeitoService;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import br.com.basis.madre.service.dto.EventoLeitoDTO;
import br.com.basis.madre.service.dto.LeitoDTO;
import br.com.basis.madre.service.dto.LiberacaoDeLeitoDTO;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
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
public class EventoLeitoResource {

    private final Logger log = LoggerFactory.getLogger(EventoLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoEventoLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoLeitoService eventoLeitoService;

    private final LeitoService leitoService;

    @PostMapping("/eventos-de-leito/liberacoes")
    public ResponseEntity<EventoLeitoDTO> liberarLeito(
        @Valid @RequestBody LiberacaoDeLeitoDTO liberacaoDeLeitoDTO) throws URISyntaxException {
        if (liberacaoDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventoLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }

        LeitoDTO leitoDTO = leitoService.findOne(liberacaoDeLeitoDTO.getLeitoId()).orElseThrow(
            () -> new BadRequestAlertException("O leito informado não existe.",
                LeitoResource.getEntityName(), "idnotexists"));
        CodigoDeSituacaoDeLeito idSituacao = CodigoDeSituacaoDeLeito.DESOCUPADO;

        if (leitoDTO.getSituacaoId().equals(idSituacao.getValor())) {
            throw new BadRequestAlertException("Este leito já está liberado.",
                LeitoResource.getEntityName(), "idinvalid");
        }

        EventoLeitoDTO eventoLeitoDTO = eventoLeitoService.liberarLeito(liberacaoDeLeitoDTO, leitoDTO);
        return ResponseEntity.created(new URI("/api/evento-leitos/" + eventoLeitoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                eventoLeitoDTO.getId().toString()))
            .body(eventoLeitoDTO);
    }

    @PostMapping("/eventos-de-leito/reservas")
    public ResponseEntity<EventoLeitoDTO> reservarLeito(
        @Valid @RequestBody ReservaDeLeitoDTO reservaDeLeitoDTO) throws URISyntaxException {
        if (reservaDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventoLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }

        LeitoDTO leitoDTO = leitoService.findOne(reservaDeLeitoDTO.getLeitoId()).orElseThrow(
            () -> new BadRequestAlertException("O leito informado não existe.",
                LeitoResource.getEntityName(), "idnotexists"));
        CodigoDeSituacaoDeLeito idSituacao = CodigoDeSituacaoDeLeito.DESOCUPADO;

        if (!(leitoDTO.getSituacaoId().equals(idSituacao.getValor()))) {
            throw new BadRequestAlertException("O leito precisa estar desocupado.",
                LeitoResource.getEntityName(), "idinvalid");
        }

        EventoLeitoDTO eventoLeitoDTO = eventoLeitoService.reservarLeito(reservaDeLeitoDTO, leitoDTO);
        return ResponseEntity.created(new URI("/api/evento-leitos/" + eventoLeitoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                eventoLeitoDTO.getId().toString()))
            .body(eventoLeitoDTO);
    }

    @PostMapping("/eventos-de-leito/bloqueios")
    public ResponseEntity<EventoLeitoDTO> bloquearLeito(
        @Valid @RequestBody BloqueioDeLeitoDTO bloqueioDeLeitoDTO) throws URISyntaxException {
        if (bloqueioDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventoLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }

        LeitoDTO leitoDTO = leitoService.findOne(bloqueioDeLeitoDTO.getLeitoId()).orElseThrow(
            () -> new BadRequestAlertException("O leito informado não existe.",
                LeitoResource.getEntityName(), "idnotexists"));
        CodigoDeSituacaoDeLeito idSituacao = CodigoDeSituacaoDeLeito.DESOCUPADO;

        if (!(leitoDTO.getSituacaoId().equals(idSituacao.getValor()))) {
            throw new BadRequestAlertException("O leito precisa estar desocupado.",
                LeitoResource.getEntityName(), "idinvalid");
        }

        EventoLeitoDTO eventoLeitoDTO = eventoLeitoService.bloquearLeito(bloqueioDeLeitoDTO, leitoDTO);
        return ResponseEntity.created(new URI("/api/evento-leitos/" + eventoLeitoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                eventoLeitoDTO.getId().toString()))
            .body(eventoLeitoDTO);
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
