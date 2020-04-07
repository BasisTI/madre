package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CertidaoService;
import br.com.basis.madre.service.dto.CertidaoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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

/**
 * REST controller for managing {@link br.com.basis.madre.domain.Certidao}.
 */
@RestController
@RequestMapping("/api")
public class CertidaoResource {

    private final Logger log = LoggerFactory.getLogger(CertidaoResource.class);

    private static final String ENTITY_NAME = "pacientesCertidao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CertidaoService certidaoService;

    public CertidaoResource(CertidaoService certidaoService) {
        this.certidaoService = certidaoService;
    }

    /**
     * {@code POST  /certidaos} : Create a new certidao.
     *
     * @param certidaoDTO the certidaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new certidaoDTO, or with status {@code 400 (Bad Request)} if the certidao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/certidaos")
    public ResponseEntity<CertidaoDTO> createCertidao(@RequestBody CertidaoDTO certidaoDTO) throws URISyntaxException {
        log.debug("REST request to save Certidao : {}", certidaoDTO);
        if (certidaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new certidao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CertidaoDTO result = certidaoService.save(certidaoDTO);
        return ResponseEntity.created(new URI("/api/certidaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /certidaos} : Updates an existing certidao.
     *
     * @param certidaoDTO the certidaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated certidaoDTO,
     * or with status {@code 400 (Bad Request)} if the certidaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the certidaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/certidaos")
    public ResponseEntity<CertidaoDTO> updateCertidao(@RequestBody CertidaoDTO certidaoDTO) throws URISyntaxException {
        log.debug("REST request to update Certidao : {}", certidaoDTO);
        if (certidaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CertidaoDTO result = certidaoService.save(certidaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, certidaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /certidaos} : get all the certidaos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of certidaos in body.
     */
    @GetMapping("/certidaos")
    public ResponseEntity<List<CertidaoDTO>> getAllCertidaos(Pageable pageable) {
        log.debug("REST request to get a page of Certidaos");
        Page<CertidaoDTO> page = certidaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /certidaos/:id} : get the "id" certidao.
     *
     * @param id the id of the certidaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the certidaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/certidaos/{id}")
    public ResponseEntity<CertidaoDTO> getCertidao(@PathVariable Long id) {
        log.debug("REST request to get Certidao : {}", id);
        Optional<CertidaoDTO> certidaoDTO = certidaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(certidaoDTO);
    }

    /**
     * {@code DELETE  /certidaos/:id} : delete the "id" certidao.
     *
     * @param id the id of the certidaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/certidaos/{id}")
    public ResponseEntity<Void> deleteCertidao(@PathVariable Long id) {
        log.debug("REST request to delete Certidao : {}", id);
        certidaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/certidaos?query=:query} : search for the certidao corresponding
     * to the query.
     *
     * @param query the query of the certidao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/certidaos")
    public ResponseEntity<List<CertidaoDTO>> searchCertidaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Certidaos for query {}", query);
        Page<CertidaoDTO> page = certidaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
