package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CIDService;
import br.com.basis.madre.service.dto.CidDTO;
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
import org.springframework.data.domain.Sort;
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
public class CIDResource {

    private final Logger log = LoggerFactory.getLogger(CIDResource.class);

    private static final String ENTITY_NAME = "internacaoCid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CIDService cidService;

    /**
     * {@code POST  /cids} : Create a new cID.
     *
     * @param cIDDTO the cIDDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
     * cIDDTO, or with status {@code 400 (Bad Request)} if the cID has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cids")
    public ResponseEntity<CidDTO> createCID(@Valid @RequestBody CidDTO cIDDTO)
        throws URISyntaxException {
        log.debug("REST request to save CID : {}", cIDDTO);
        if (cIDDTO.getId() != null) {
            throw new BadRequestAlertException("A new cID cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        CidDTO result = cidService.save(cIDDTO);
        return ResponseEntity.created(new URI("/api/cids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cids} : Updates an existing cID.
     *
     * @param cIDDTO the cIDDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
     * cIDDTO, or with status {@code 400 (Bad Request)} if the cIDDTO is not valid, or with status
     * {@code 500 (Internal Server Error)} if the cIDDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cids")
    public ResponseEntity<CidDTO> updateCID(@Valid @RequestBody CidDTO cIDDTO)
        throws URISyntaxException {
        log.debug("REST request to update CID : {}", cIDDTO);
        if (cIDDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CidDTO result = cidService.save(cIDDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                cIDDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/cids")
    public ResponseEntity<List<CidDTO>> getAllCIDS(Sort sort) {
        List<CidDTO> list = cidService.findAll(sort);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/cids/pais")
    public ResponseEntity<List<CidDTO>> getPais(Sort sort) {
        List<CidDTO> list = cidService.getPais(sort);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/cids/pais/{id}/filhos")
    public ResponseEntity<List<CidDTO>> getFilhosPeloIdDoPai(
        @PathVariable(name = "id", required = true) Long id, Sort sort) {
        List<CidDTO> list = cidService.getFilhosPeloIdDoPai(id, sort);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /cids/:id} : get the "id" cID.
     *
     * @param id the id of the cIDDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cIDDTO, or
     * with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cids/{id}")
    public ResponseEntity<CidDTO> getCID(@PathVariable Long id) {
        log.debug("REST request to get CID : {}", id);
        Optional<CidDTO> cIDDTO = cidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cIDDTO);
    }

    /**
     * {@code DELETE  /cids/:id} : delete the "id" cID.
     *
     * @param id the id of the cIDDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cids/{id}")
    public ResponseEntity<Void> deleteCID(@PathVariable Long id) {
        log.debug("REST request to delete CID : {}", id);
        cidService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil
            .createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cids?query=:query} : search for the cID corresponding to the query.
     *
     * @param query    the query of the cID search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cids")
    public ResponseEntity<List<CidDTO>> searchCIDS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CIDS for query {}", query);
        Page<CidDTO> page = cidService.search(query, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
