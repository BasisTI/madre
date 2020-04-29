package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.EquipeService;
import br.com.basis.madre.service.dto.EquipeDTO;
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
public class EquipeResource {

    private final Logger log = LoggerFactory.getLogger(EquipeResource.class);

    private static final String ENTITY_NAME = "internacaoEquipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipeService equipeService;

    /**
     * {@code POST  /equipes} : Create a new equipe.
     *
     * @param equipeDTO the equipeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * equipeDTO, or with status {@code 400 (Bad Request)} if the equipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipes")
    public ResponseEntity<EquipeDTO> createEquipe(@Valid @RequestBody EquipeDTO equipeDTO)
        throws URISyntaxException {
        log.debug("REST request to save Equipe : {}", equipeDTO);
        if (equipeDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipe cannot already have an ID",
                ENTITY_NAME, "idexists");
        }
        EquipeDTO result = equipeService.save(equipeDTO);
        return ResponseEntity.created(new URI("/api/equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipes} : Updates an existing equipe.
     *
     * @param equipeDTO the equipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * equipeDTO, or with status {@code 400 (Bad Request)} if the equipeDTO is not valid, or with
     * status {@code 500 (Internal Server Error)} if the equipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipes")
    public ResponseEntity<EquipeDTO> updateEquipe(@Valid @RequestBody EquipeDTO equipeDTO)
        throws URISyntaxException {
        log.debug("REST request to update Equipe : {}", equipeDTO);
        if (equipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EquipeDTO result = equipeService.save(equipeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                equipeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipes} : get all the equipes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipes in
     * body.
     */
    @GetMapping("/equipes")
    public ResponseEntity<List<EquipeDTO>> getAllEquipes(Pageable pageable) {
        log.debug("REST request to get a page of Equipes");
        Page<EquipeDTO> page = equipeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipes/:id} : get the "id" equipe.
     *
     * @param id the id of the equipeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipeDTO,
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipes/{id}")
    public ResponseEntity<EquipeDTO> getEquipe(@PathVariable Long id) {
        log.debug("REST request to get Equipe : {}", id);
        Optional<EquipeDTO> equipeDTO = equipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipeDTO);
    }

    /**
     * {@code DELETE  /equipes/:id} : delete the "id" equipe.
     *
     * @param id the id of the equipeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipes/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        log.debug("REST request to delete Equipe : {}", id);
        equipeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/equipes?query=:query} : search for the equipe corresponding to the
     * query.
     *
     * @param query    the query of the equipe search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/equipes")
    public ResponseEntity<List<EquipeDTO>> searchEquipes(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of Equipes for query {}", query);
        Page<EquipeDTO> page = equipeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
