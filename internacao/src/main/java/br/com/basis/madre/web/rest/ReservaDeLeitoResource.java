package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.LeitoService;
import br.com.basis.madre.service.ReservaDeLeitoService;
import br.com.basis.madre.service.SituacaoDeLeitoService;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import br.com.basis.madre.service.dto.SituacaoDeLeitoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
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
public class ReservaDeLeitoResource {

    private final Logger log = LoggerFactory.getLogger(ReservaDeLeitoResource.class);

    private static final String ENTITY_NAME = "internacaoReservaDeLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservaDeLeitoService reservaDeLeitoService;

    private final LeitoService leitoService;

    private final SituacaoDeLeitoService situacaoDeLeitoService;

    /**
     * {@code POST  /reserva-de-leitos} : Create a new reservaDeLeito.
     *
     * @param reservaDeLeitoDTO the reservaDeLeitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * reservaDeLeitoDTO, or with status {@code 400 (Bad Request)} if the reservaDeLeito has already
     * an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservas-de-leitos")
    public ResponseEntity<ReservaDeLeitoDTO> createReservaDeLeito(
        @Valid @RequestBody ReservaDeLeitoDTO reservaDeLeitoDTO) throws URISyntaxException {
        if (reservaDeLeitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservaDeLeito cannot already have an ID",
                ENTITY_NAME, "idexists");
        }

        Long idSituacaoDoLeito = leitoService.findOne(reservaDeLeitoDTO.getLeitoId())
            .orElseThrow(() -> new BadRequestAlertException(
                "O leito informado não existe.",
                LeitoResource.getEntityName(), "idnotexists")).getSituacaoId();
        SituacaoDeLeitoDTO desocupado = situacaoDeLeitoService.findByNomeIgnoreCase("desocupado");

        if (Objects.isNull(desocupado) && !idSituacaoDoLeito.equals(desocupado.getId())) {
            throw new BadRequestAlertException("O leito precisa estar desocupado.",
                LeitoResource.getEntityName(), "idinvalid");
        }

        SituacaoDeLeitoDTO reservado = situacaoDeLeitoService.findByNomeIgnoreCase("reservado");

        ReservaDeLeitoDTO result = reservaDeLeitoService.save(reservaDeLeitoDTO, reservado);
        return ResponseEntity.created(new URI("/api/reserva-de-leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reserva-de-leitos} : Updates an existing reservaDeLeito.
     *
     * @param reservaDeLeitoDTO the reservaDeLeitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * reservaDeLeitoDTO, or with status {@code 400 (Bad Request)} if the reservaDeLeitoDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the reservaDeLeitoDTO couldn't
     * be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservas-de-leitos")
    public ResponseEntity<ReservaDeLeitoDTO> updateReservaDeLeito(
        @Valid @RequestBody ReservaDeLeitoDTO reservaDeLeitoDTO) throws URISyntaxException {
        log.debug("REST request to update ReservaDeLeito : {}", reservaDeLeitoDTO);
        if (reservaDeLeitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReservaDeLeitoDTO result = reservaDeLeitoService.save(reservaDeLeitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                reservaDeLeitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reserva-de-leitos} : get all the reservaDeLeitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * reservaDeLeitos in body.
     */
    @GetMapping("/reservas-de-leitos")
    public ResponseEntity<List<ReservaDeLeitoDTO>> getAllReservaDeLeitos(Pageable pageable) {
        log.debug("REST request to get a page of ReservaDeLeitos");
        Page<ReservaDeLeitoDTO> page = reservaDeLeitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reserva-de-leitos/:id} : get the "id" reservaDeLeito.
     *
     * @param id the id of the reservaDeLeitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * reservaDeLeitoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservas-de-leitos/{id}")
    public ResponseEntity<ReservaDeLeitoDTO> getReservaDeLeito(@PathVariable Long id) {
        log.debug("REST request to get ReservaDeLeito : {}", id);
        Optional<ReservaDeLeitoDTO> reservaDeLeitoDTO = reservaDeLeitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservaDeLeitoDTO);
    }

    /**
     * {@code DELETE  /reserva-de-leitos/:id} : delete the "id" reservaDeLeito.
     *
     * @param id the id of the reservaDeLeitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservas-de-leitos/{id}")
    public ResponseEntity<Void> deleteReservaDeLeito(@PathVariable Long id) {
        log.debug("REST request to delete ReservaDeLeito : {}", id);
        reservaDeLeitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/reserva-de-leitos?query=:query} : search for the reservaDeLeito
     * corresponding to the query.
     *
     * @param query    the query of the reservaDeLeito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/reservas-de-leitos")
    public ResponseEntity<List<ReservaDeLeitoDTO>> searchReservaDeLeitos(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of ReservaDeLeitos for query {}", query);
        Page<ReservaDeLeitoDTO> page = reservaDeLeitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
