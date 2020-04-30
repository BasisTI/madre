package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.PlanoDeSaudeService;
import br.com.basis.madre.service.dto.PlanoDeSaudeDTO;
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
public class PlanoDeSaudeResource {

    private final Logger log = LoggerFactory.getLogger(PlanoDeSaudeResource.class);

    private static final String ENTITY_NAME = "internacaoPlanoDeSaude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoDeSaudeService planoDeSaudeService;

    /**
     * {@code POST  /plano-de-saudes} : Create a new planoDeSaude.
     *
     * @param planoDeSaudeDTO the planoDeSaudeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * planoDeSaudeDTO, or with status {@code 400 (Bad Request)} if the planoDeSaude has already an
     * ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planos-de-saude")
    public ResponseEntity<PlanoDeSaudeDTO> createPlanoDeSaude(
        @Valid @RequestBody PlanoDeSaudeDTO planoDeSaudeDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoDeSaude : {}", planoDeSaudeDTO);
        if (planoDeSaudeDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoDeSaude cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        PlanoDeSaudeDTO result = planoDeSaudeService.save(planoDeSaudeDTO);
        return ResponseEntity.created(new URI("/api/plano-de-saudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plano-de-saudes} : Updates an existing planoDeSaude.
     *
     * @param planoDeSaudeDTO the planoDeSaudeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * planoDeSaudeDTO, or with status {@code 400 (Bad Request)} if the planoDeSaudeDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the planoDeSaudeDTO couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planos-de-saude")
    public ResponseEntity<PlanoDeSaudeDTO> updatePlanoDeSaude(
        @Valid @RequestBody PlanoDeSaudeDTO planoDeSaudeDTO) throws URISyntaxException {
        log.debug("REST request to update PlanoDeSaude : {}", planoDeSaudeDTO);
        if (planoDeSaudeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanoDeSaudeDTO result = planoDeSaudeService.save(planoDeSaudeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                planoDeSaudeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plano-de-saudes} : get all the planoDeSaudes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoDeSaudes
     * in body.
     */
    @GetMapping("/planos-de-saude")
    public ResponseEntity<List<PlanoDeSaudeDTO>> getAllPlanoDeSaudes(
        PlanoDeSaudeDTO planoDeSaudeDTO, Pageable pageable) {
        log.debug("REST request to get a page of PlanoDeSaudes");
        Page<PlanoDeSaudeDTO> page = planoDeSaudeService.findAll(planoDeSaudeDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-de-saudes/:id} : get the "id" planoDeSaude.
     *
     * @param id the id of the planoDeSaudeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
     * planoDeSaudeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planos-de-saude/{id}")
    public ResponseEntity<PlanoDeSaudeDTO> getPlanoDeSaude(@PathVariable Long id) {
        log.debug("REST request to get PlanoDeSaude : {}", id);
        Optional<PlanoDeSaudeDTO> planoDeSaudeDTO = planoDeSaudeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoDeSaudeDTO);
    }

    /**
     * {@code DELETE  /plano-de-saudes/:id} : delete the "id" planoDeSaude.
     *
     * @param id the id of the planoDeSaudeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planos-de-saude/{id}")
    public ResponseEntity<Void> deletePlanoDeSaude(@PathVariable Long id) {
        log.debug("REST request to delete PlanoDeSaude : {}", id);
        planoDeSaudeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/plano-de-saudes?query=:query} : search for the planoDeSaude
     * corresponding to the query.
     *
     * @param query    the query of the planoDeSaude search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/planos-de-saude")
    public ResponseEntity<List<PlanoDeSaudeDTO>> searchPlanoDeSaudes(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of PlanoDeSaudes for query {}", query);
        Page<PlanoDeSaudeDTO> page = planoDeSaudeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
