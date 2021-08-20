package br.com.basis.madre.seguranca.web.rest;

import br.com.basis.madre.seguranca.service.ConselhosProfissionaisService;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.seguranca.service.dto.ConselhosProfissionaisDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.ConselhosProfissionais}.
 */
@RestController
@RequestMapping("/api")
public class ConselhosProfissionaisResource {

    private final Logger log = LoggerFactory.getLogger(ConselhosProfissionaisResource.class);

    private static final String ENTITY_NAME = "madresegurancaConselhosProfissionais";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConselhosProfissionaisService conselhosProfissionaisService;

    public ConselhosProfissionaisResource(ConselhosProfissionaisService conselhosProfissionaisService) {
        this.conselhosProfissionaisService = conselhosProfissionaisService;
    }

    /**
     * {@code POST  /conselhos-profissionais} : Create a new conselhosProfissionais.
     *
     * @param conselhosProfissionaisDTO the conselhosProfissionaisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conselhosProfissionaisDTO, or with status {@code 400 (Bad Request)} if the conselhosProfissionais has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conselhos-profissionais")
    public ResponseEntity<ConselhosProfissionaisDTO> createConselhosProfissionais(@Valid @RequestBody ConselhosProfissionaisDTO conselhosProfissionaisDTO) throws URISyntaxException {
        log.debug("REST request to save ConselhosProfissionais : {}", conselhosProfissionaisDTO);
        if (conselhosProfissionaisDTO.getId() != null) {
            throw new BadRequestAlertException("A new conselhosProfissionais cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConselhosProfissionaisDTO result = conselhosProfissionaisService.save(conselhosProfissionaisDTO);
        return ResponseEntity.created(new URI("/api/conselhos-profissionais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conselhos-profissionais} : Updates an existing conselhosProfissionais.
     *
     * @param conselhosProfissionaisDTO the conselhosProfissionaisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conselhosProfissionaisDTO,
     * or with status {@code 400 (Bad Request)} if the conselhosProfissionaisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conselhosProfissionaisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conselhos-profissionais")
    public ResponseEntity<ConselhosProfissionaisDTO> updateConselhosProfissionais(@Valid @RequestBody ConselhosProfissionaisDTO conselhosProfissionaisDTO) throws URISyntaxException {
        log.debug("REST request to update ConselhosProfissionais : {}", conselhosProfissionaisDTO);
        if (conselhosProfissionaisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConselhosProfissionaisDTO result = conselhosProfissionaisService.save(conselhosProfissionaisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, conselhosProfissionaisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /conselhos-profissionais} : get all the conselhosProfissionais.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conselhosProfissionais in body.
     */
    @GetMapping("/conselhos-profissionais")
    public ResponseEntity<List<ConselhosProfissionaisDTO>> getAllConselhosProfissionais(Pageable pageable) {
        log.debug("REST request to get a page of ConselhosProfissionais");
        Page<ConselhosProfissionaisDTO> page = conselhosProfissionaisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conselhos-profissionais/:id} : get the "id" conselhosProfissionais.
     *
     * @param id the id of the conselhosProfissionaisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conselhosProfissionaisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conselhos-profissionais/{id}")
    public ResponseEntity<ConselhosProfissionaisDTO> getConselhosProfissionais(@PathVariable Long id) {
        log.debug("REST request to get ConselhosProfissionais : {}", id);
        Optional<ConselhosProfissionaisDTO> conselhosProfissionaisDTO = conselhosProfissionaisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conselhosProfissionaisDTO);
    }

    /**
     * {@code DELETE  /conselhos-profissionais/:id} : delete the "id" conselhosProfissionais.
     *
     * @param id the id of the conselhosProfissionaisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conselhos-profissionais/{id}")
    public ResponseEntity<Void> deleteConselhosProfissionais(@PathVariable Long id) {
        log.debug("REST request to delete ConselhosProfissionais : {}", id);
        conselhosProfissionaisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/conselhos-profissionais?query=:query} : search for the conselhosProfissionais corresponding
     * to the query.
     *
     * @param query the query of the conselhosProfissionais search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/conselhos-profissionais")
    public ResponseEntity<List<ConselhosProfissionaisDTO>> searchConselhosProfissionais(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConselhosProfissionais for query {}", query);
        Page<ConselhosProfissionaisDTO> page = conselhosProfissionaisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
