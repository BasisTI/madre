package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.LocalDeAtendimentoService;
import br.com.basis.madre.service.dto.LocalDeAtendimentoDTO;
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
public class LocalDeAtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(LocalDeAtendimentoResource.class);

    private static final String ENTITY_NAME = "internacaoLocalDeAtendimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalDeAtendimentoService localDeAtendimentoService;

    /**
     * {@code POST  /local-de-atendimentos} : Create a new localDeAtendimento.
     *
     * @param localDeAtendimentoDTO the localDeAtendimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * localDeAtendimentoDTO, or with status {@code 400 (Bad Request)} if the localDeAtendimento has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locais-de-atendimento")
    public ResponseEntity<LocalDeAtendimentoDTO> createLocalDeAtendimento(
        @Valid @RequestBody LocalDeAtendimentoDTO localDeAtendimentoDTO) throws URISyntaxException {
        log.debug("REST request to save LocalDeAtendimento : {}", localDeAtendimentoDTO);
        if (localDeAtendimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new localDeAtendimento cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        LocalDeAtendimentoDTO result = localDeAtendimentoService.save(localDeAtendimentoDTO);
        return ResponseEntity.created(new URI("/api/local-de-atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /local-de-atendimentos} : Updates an existing localDeAtendimento.
     *
     * @param localDeAtendimentoDTO the localDeAtendimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * localDeAtendimentoDTO, or with status {@code 400 (Bad Request)} if the localDeAtendimentoDTO
     * is not valid, or with status {@code 500 (Internal Server Error)} if the localDeAtendimentoDTO
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locais-de-atendimento")
    public ResponseEntity<LocalDeAtendimentoDTO> updateLocalDeAtendimento(
        @Valid @RequestBody LocalDeAtendimentoDTO localDeAtendimentoDTO) throws URISyntaxException {
        log.debug("REST request to update LocalDeAtendimento : {}", localDeAtendimentoDTO);
        if (localDeAtendimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalDeAtendimentoDTO result = localDeAtendimentoService.save(localDeAtendimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                localDeAtendimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /local-de-atendimentos} : get all the localDeAtendimentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * localDeAtendimentos in body.
     */
    @GetMapping("/locais-de-atendimento")
    public ResponseEntity<List<LocalDeAtendimentoDTO>> getAllLocalDeAtendimentos(
        Pageable pageable) {
        log.debug("REST request to get a page of LocalDeAtendimentos");
        Page<LocalDeAtendimentoDTO> page = localDeAtendimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /local-de-atendimentos/:id} : get the "id" localDeAtendimento.
     *
     * @param id the id of the localDeAtendimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * localDeAtendimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locais-de-atendimento/{id}")
    public ResponseEntity<LocalDeAtendimentoDTO> getLocalDeAtendimento(@PathVariable Long id) {
        log.debug("REST request to get LocalDeAtendimento : {}", id);
        Optional<LocalDeAtendimentoDTO> localDeAtendimentoDTO = localDeAtendimentoService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(localDeAtendimentoDTO);
    }

    /**
     * {@code DELETE  /local-de-atendimentos/:id} : delete the "id" localDeAtendimento.
     *
     * @param id the id of the localDeAtendimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locais-de-atendimento/{id}")
    public ResponseEntity<Void> deleteLocalDeAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete LocalDeAtendimento : {}", id);
        localDeAtendimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/local-de-atendimentos?query=:query} : search for the
     * localDeAtendimento corresponding to the query.
     *
     * @param query    the query of the localDeAtendimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/locais-de-atendimento")
    public ResponseEntity<List<LocalDeAtendimentoDTO>> searchLocalDeAtendimentos(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LocalDeAtendimentos for query {}", query);
        Page<LocalDeAtendimentoDTO> page = localDeAtendimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
