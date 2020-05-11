package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.LeitoService;
import br.com.basis.madre.service.dto.LeitoDTO;
import br.com.basis.madre.service.dto.LiberacaoDeLeitoDTO;
import br.com.basis.madre.service.projection.LeitoProjection;
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
public class LeitoResource {

    private final Logger log = LoggerFactory.getLogger(LeitoResource.class);

    private static final String ENTITY_NAME = "internacaoLeito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeitoService leitoService;

    public static String getEntityName() {
        return ENTITY_NAME;
    }

    /**
     * {@code POST  /leitos} : Create a new leito.
     *
     * @param leitoDTO the leitoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * leitoDTO, or with status {@code 400 (Bad Request)} if the leito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leitos")
    public ResponseEntity<LeitoDTO> createLeito(@Valid @RequestBody LeitoDTO leitoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Leito : {}", leitoDTO);
        if (leitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new leito cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        LeitoDTO result = leitoService.save(leitoDTO);
        return ResponseEntity.created(new URI("/api/leitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leitos} : Updates an existing leito.
     *
     * @param leitoDTO the leitoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * leitoDTO, or with status {@code 400 (Bad Request)} if the leitoDTO is not valid, or with
     * status {@code 500 (Internal Server Error)} if the leitoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leitos")
    public ResponseEntity<LeitoDTO> updateLeito(@Valid @RequestBody LeitoDTO leitoDTO)
        throws URISyntaxException {
        log.debug("REST request to update Leito : {}", leitoDTO);
        if (leitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeitoDTO result = leitoService.save(leitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                leitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leitos} : get all the leitos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leitos in
     * body.
     */
    @GetMapping("/leitos")
    public ResponseEntity<List<LeitoDTO>> getAllLeitos(LeitoDTO leitoDTO, Pageable pageable) {
        log.debug("REST request to get a page of Leitos");
        Page<LeitoDTO> page = leitoService.findAll(leitoDTO, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/leitos/liberacao-de-leito")
    public ResponseEntity<LeitoDTO> liberarLeito(
        @RequestBody @Valid LiberacaoDeLeitoDTO liberacaoDeLeitoDTO) {
        return ResponseEntity.ok()
            .body(leitoService.liberarLeito(liberacaoDeLeitoDTO.getLeitoId()));
    }

    @GetMapping("/leitos/desocupados")
    public ResponseEntity<List<LeitoProjection>> getLeitosDesocupadosPorNome(
        @RequestParam(name = "nome", required = false, defaultValue = "") String nome,
        Pageable pageable) {
        log.debug("REST request to get a page of Leitos");
        List<LeitoProjection> leitos = leitoService.getLeitosDesocupadosPor(nome, pageable);
        return ResponseEntity.ok().body(leitos);
    }

    @GetMapping("/leitos/nao-desocupados")
    public ResponseEntity<List<LeitoProjection>> getLeitosNaoDesocupadosPorNome(
        @RequestParam(name = "nome", required = false, defaultValue = "") String nome,
        Pageable pageable) {
        log.debug("REST request to get a page of Leitos");
        List<LeitoProjection> leitos = leitoService.getLeitosNaoDesocupadosPor(nome, pageable);
        return ResponseEntity.ok().body(leitos);
    }

    /**
     * {@code GET  /leitos/:id} : get the "id" leito.
     *
     * @param id the id of the leitoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leitoDTO,
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leitos/{id}")
    public ResponseEntity<LeitoDTO> getLeito(@PathVariable Long id) {
        log.debug("REST request to get Leito : {}", id);
        Optional<LeitoDTO> leitoDTO = leitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leitoDTO);
    }

    /**
     * {@code DELETE  /leitos/:id} : delete the "id" leito.
     *
     * @param id the id of the leitoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leitos/{id}")
    public ResponseEntity<Void> deleteLeito(@PathVariable Long id) {
        log.debug("REST request to delete Leito : {}", id);
        leitoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/leitos?query=:query} : search for the leito corresponding to the
     * query.
     *
     * @param query    the query of the leito search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/leitos")
    public ResponseEntity<List<LeitoDTO>> searchLeitos(@RequestParam String query,
        Pageable pageable) {
        log.debug("REST request to search for a page of Leitos for query {}", query);
        Page<LeitoDTO> page = leitoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
