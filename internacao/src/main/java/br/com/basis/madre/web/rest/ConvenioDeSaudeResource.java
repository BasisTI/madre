package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.ConvenioDeSaudeService;
import br.com.basis.madre.service.dto.ConvenioDeSaudeDTO;
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
public class ConvenioDeSaudeResource {

    private final Logger log = LoggerFactory.getLogger(ConvenioDeSaudeResource.class);

    private static final String ENTITY_NAME = "internacaoConvenioDeSaude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConvenioDeSaudeService convenioDeSaudeService;

    /**
     * {@code POST  /convenio-de-saudes} : Create a new convenioDeSaude.
     *
     * @param convenioDeSaudeDTO the convenioDeSaudeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * convenioDeSaudeDTO, or with status {@code 400 (Bad Request)} if the convenioDeSaude has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/convenios-de-saude")
    public ResponseEntity<ConvenioDeSaudeDTO> createConvenioDeSaude(
        @Valid @RequestBody ConvenioDeSaudeDTO convenioDeSaudeDTO) throws URISyntaxException {
        log.debug("REST request to save ConvenioDeSaude : {}", convenioDeSaudeDTO);
        if (convenioDeSaudeDTO.getId() != null) {
            throw new BadRequestAlertException("A new convenioDeSaude cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        ConvenioDeSaudeDTO result = convenioDeSaudeService.save(convenioDeSaudeDTO);
        return ResponseEntity.created(new URI("/api/convenio-de-saudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /convenio-de-saudes} : Updates an existing convenioDeSaude.
     *
     * @param convenioDeSaudeDTO the convenioDeSaudeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * convenioDeSaudeDTO, or with status {@code 400 (Bad Request)} if the convenioDeSaudeDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the convenioDeSaudeDTO couldn't
     * be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/convenios-de-saude")
    public ResponseEntity<ConvenioDeSaudeDTO> updateConvenioDeSaude(
        @Valid @RequestBody ConvenioDeSaudeDTO convenioDeSaudeDTO) throws URISyntaxException {
        log.debug("REST request to update ConvenioDeSaude : {}", convenioDeSaudeDTO);
        if (convenioDeSaudeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConvenioDeSaudeDTO result = convenioDeSaudeService.save(convenioDeSaudeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                convenioDeSaudeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /convenio-de-saudes} : get all the convenioDeSaudes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
     * convenioDeSaudes in body.
     */
    @GetMapping("/convenios-de-saude")
    public ResponseEntity<List<ConvenioDeSaudeDTO>> getAllConvenioDeSaudes(
        ConvenioDeSaudeDTO convenioDeSaudeDTO, Pageable pageable) {
        log.debug("REST request to get a page of ConvenioDeSaudes");
        Page<ConvenioDeSaudeDTO> page = convenioDeSaudeService.findAll(convenioDeSaudeDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /convenio-de-saudes/:id} : get the "id" convenioDeSaude.
     *
     * @param id the id of the convenioDeSaudeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * convenioDeSaudeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/convenios-de-saude/{id}")
    public ResponseEntity<ConvenioDeSaudeDTO> getConvenioDeSaude(@PathVariable Long id) {
        log.debug("REST request to get ConvenioDeSaude : {}", id);
        Optional<ConvenioDeSaudeDTO> convenioDeSaudeDTO = convenioDeSaudeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(convenioDeSaudeDTO);
    }

    /**
     * {@code DELETE  /convenio-de-saudes/:id} : delete the "id" convenioDeSaude.
     *
     * @param id the id of the convenioDeSaudeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/convenios-de-saude/{id}")
    public ResponseEntity<Void> deleteConvenioDeSaude(@PathVariable Long id) {
        log.debug("REST request to delete ConvenioDeSaude : {}", id);
        convenioDeSaudeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/convenio-de-saudes?query=:query} : search for the convenioDeSaude
     * corresponding to the query.
     *
     * @param query    the query of the convenioDeSaude search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/convenios-de-saude")
    public ResponseEntity<List<ConvenioDeSaudeDTO>> searchConvenioDeSaudes(
        @RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConvenioDeSaudes for query {}", query);
        Page<ConvenioDeSaudeDTO> page = convenioDeSaudeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
