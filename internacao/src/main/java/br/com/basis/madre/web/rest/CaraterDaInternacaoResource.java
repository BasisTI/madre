package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CaraterDaInternacaoService;
import br.com.basis.madre.service.dto.CaraterDaInternacaoDTO;
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
public class CaraterDaInternacaoResource {

    private final Logger log = LoggerFactory.getLogger(CaraterDaInternacaoResource.class);

    private static final String ENTITY_NAME = "internacaoCaraterDaInternacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaraterDaInternacaoService caraterDaInternacaoService;

    /**
     * {@code POST  /carater-da-internacaos} : Create a new caraterDaInternacao.
     *
     * @param caraterDaInternacaoDTO the caraterDaInternacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * caraterDaInternacaoDTO, or with status {@code 400 (Bad Request)} if the caraterDaInternacao
     * has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/caracteres-da-internacao")
    public ResponseEntity<CaraterDaInternacaoDTO> createCaraterDaInternacao(
        @Valid @RequestBody CaraterDaInternacaoDTO caraterDaInternacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to save CaraterDaInternacao : {}", caraterDaInternacaoDTO);
        if (caraterDaInternacaoDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new caraterDaInternacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaraterDaInternacaoDTO result = caraterDaInternacaoService.save(caraterDaInternacaoDTO);
        return ResponseEntity.created(new URI("/api/carater-da-internacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carater-da-internacaos} : Updates an existing caraterDaInternacao.
     *
     * @param caraterDaInternacaoDTO the caraterDaInternacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * caraterDaInternacaoDTO, or with status {@code 400 (Bad Request)} if the
     * caraterDaInternacaoDTO is not valid, or with status {@code 500 (Internal Server Error)} if
     * the caraterDaInternacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/caracteres-da-internacao")
    public ResponseEntity<CaraterDaInternacaoDTO> updateCaraterDaInternacao(
        @Valid @RequestBody CaraterDaInternacaoDTO caraterDaInternacaoDTO)
        throws URISyntaxException {
        log.debug("REST request to update CaraterDaInternacao : {}", caraterDaInternacaoDTO);
        if (caraterDaInternacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaraterDaInternacaoDTO result = caraterDaInternacaoService.save(caraterDaInternacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                caraterDaInternacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carater-da-internacaos} : get all the caraterDaInternacaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * caraterDaInternacaos in body.
     */
    @GetMapping("/caracteres-da-internacao")
    public ResponseEntity<List<CaraterDaInternacaoDTO>> getAllCaraterDaInternacaos(
        Pageable pageable) {
        log.debug("REST request to get a page of CaraterDaInternacaos");
        Page<CaraterDaInternacaoDTO> page = caraterDaInternacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carater-da-internacaos/:id} : get the "id" caraterDaInternacao.
     *
     * @param id the id of the caraterDaInternacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * caraterDaInternacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/caracteres-da-internacao/{id}")
    public ResponseEntity<CaraterDaInternacaoDTO> getCaraterDaInternacao(@PathVariable Long id) {
        log.debug("REST request to get CaraterDaInternacao : {}", id);
        Optional<CaraterDaInternacaoDTO> caraterDaInternacaoDTO = caraterDaInternacaoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(caraterDaInternacaoDTO);
    }

    /**
     * {@code DELETE  /carater-da-internacaos/:id} : delete the "id" caraterDaInternacao.
     *
     * @param id the id of the caraterDaInternacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/caracteres-da-internacao/{id}")
    public ResponseEntity<Void> deleteCaraterDaInternacao(@PathVariable Long id) {
        log.debug("REST request to delete CaraterDaInternacao : {}", id);
        caraterDaInternacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/carater-da-internacaos?query=:query} : search for the
     * caraterDaInternacao corresponding to the query.
     *
     * @param query    the query of the caraterDaInternacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/caracteres-da-internacao")
    public ResponseEntity<List<CaraterDaInternacaoDTO>> searchCaraterDaInternacaos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CaraterDaInternacaos for query {}", query);
        Page<CaraterDaInternacaoDTO> page = caraterDaInternacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
