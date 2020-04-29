package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ModalidadeAssistencialService;
import br.com.basis.madre.service.dto.ModalidadeAssistencialDTO;
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
public class ModalidadeAssistencialResource {

    private final Logger log = LoggerFactory.getLogger(ModalidadeAssistencialResource.class);

    private static final String ENTITY_NAME = "internacaoModalidadeAssistencial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModalidadeAssistencialService modalidadeAssistencialService;

    /**
     * {@code POST  /modalidade-assistencials} : Create a new modalidadeAssistencial.
     *
     * @param modalidadeAssistencialDTO the modalidadeAssistencialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * modalidadeAssistencialDTO, or with status {@code 400 (Bad Request)} if the
     * modalidadeAssistencial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modalidades-assistenciais")
    public ResponseEntity<ModalidadeAssistencialDTO> createModalidadeAssistencial(
        @Valid @RequestBody ModalidadeAssistencialDTO modalidadeAssistencialDTO)
        throws URISyntaxException {
        log.debug("REST request to save ModalidadeAssistencial : {}", modalidadeAssistencialDTO);
        if (modalidadeAssistencialDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new modalidadeAssistencial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModalidadeAssistencialDTO result = modalidadeAssistencialService
            .save(modalidadeAssistencialDTO);
        return ResponseEntity.created(new URI("/api/modalidade-assistencials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modalidade-assistencials} : Updates an existing modalidadeAssistencial.
     *
     * @param modalidadeAssistencialDTO the modalidadeAssistencialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * modalidadeAssistencialDTO, or with status {@code 400 (Bad Request)} if the
     * modalidadeAssistencialDTO is not valid, or with status {@code 500 (Internal Server Error)} if
     * the modalidadeAssistencialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modalidades-assistenciais")
    public ResponseEntity<ModalidadeAssistencialDTO> updateModalidadeAssistencial(
        @Valid @RequestBody ModalidadeAssistencialDTO modalidadeAssistencialDTO)
        throws URISyntaxException {
        log.debug("REST request to update ModalidadeAssistencial : {}", modalidadeAssistencialDTO);
        if (modalidadeAssistencialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModalidadeAssistencialDTO result = modalidadeAssistencialService
            .save(modalidadeAssistencialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                modalidadeAssistencialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modalidade-assistencials} : get all the modalidadeAssistencials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * modalidadeAssistencials in body.
     */
    @GetMapping("/modalidades-assistenciais")
    public ResponseEntity<List<ModalidadeAssistencialDTO>> getAllModalidadeAssistencials(
        Pageable pageable) {
        log.debug("REST request to get a page of ModalidadeAssistencials");
        Page<ModalidadeAssistencialDTO> page = modalidadeAssistencialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modalidade-assistencials/:id} : get the "id" modalidadeAssistencial.
     *
     * @param id the id of the modalidadeAssistencialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * modalidadeAssistencialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modalidades-assistenciais/{id}")
    public ResponseEntity<ModalidadeAssistencialDTO> getModalidadeAssistencial(
        @PathVariable Long id) {
        log.debug("REST request to get ModalidadeAssistencial : {}", id);
        Optional<ModalidadeAssistencialDTO> modalidadeAssistencialDTO = modalidadeAssistencialService
            .findOne(id);
        return ResponseUtil.wrapOrNotFound(modalidadeAssistencialDTO);
    }

    /**
     * {@code DELETE  /modalidade-assistencials/:id} : delete the "id" modalidadeAssistencial.
     *
     * @param id the id of the modalidadeAssistencialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modalidades-assistenciais/{id}")
    public ResponseEntity<Void> deleteModalidadeAssistencial(@PathVariable Long id) {
        log.debug("REST request to delete ModalidadeAssistencial : {}", id);
        modalidadeAssistencialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/modalidade-assistencials?query=:query} : search for the
     * modalidadeAssistencial corresponding to the query.
     *
     * @param query    the query of the modalidadeAssistencial search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/modalidades-assistenciais")
    public ResponseEntity<List<ModalidadeAssistencialDTO>> searchModalidadeAssistencials(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ModalidadeAssistencials for query {}",
            query);
        Page<ModalidadeAssistencialDTO> page = modalidadeAssistencialService
            .search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
