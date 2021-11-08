package br.com.basis.madre.seguranca.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.com.basis.madre.seguranca.service.ServidorService;
import br.com.basis.madre.seguranca.service.dto.ServidorDTO;
import br.com.basis.madre.seguranca.service.projection.ServidorResumo;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.basis.madre.seguranca.domain.Servidor}.
 */
@RestController
@RequestMapping("/api")
public class ServidorResource {

    private final Logger log = LoggerFactory.getLogger(ServidorResource.class);

    private static final String ENTITY_NAME = "madresegurancaServidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServidorService servidorService;

    public ServidorResource(ServidorService servidorService) {
        this.servidorService = servidorService;
    }

    /**
     * {@code POST  /servidors} : Create a new servidor.
     *
     * @param servidorDTO the servidorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servidorDTO, or with status {@code 400 (Bad Request)} if the servidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servidors")
    public ResponseEntity<ServidorDTO> createServidor(@Valid @RequestBody ServidorDTO servidorDTO) throws URISyntaxException {
        log.debug("REST request to save Servidor : {}", servidorDTO);
        if (servidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new servidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServidorDTO result = servidorService.save(servidorDTO);
        return ResponseEntity.created(new URI("/api/servidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servidors} : Updates an existing servidor.
     *
     * @param servidorDTO the servidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servidorDTO,
     * or with status {@code 400 (Bad Request)} if the servidorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servidors")
    public ResponseEntity<ServidorDTO> updateServidor(@Valid @RequestBody ServidorDTO servidorDTO) throws URISyntaxException {
        log.debug("REST request to update Servidor : {}", servidorDTO);
        if (servidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServidorDTO result = servidorService.save(servidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, servidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /servidors} : get all the servidors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servidors in body.
     */
    @GetMapping("/servidors")
    public ResponseEntity<List<ServidorDTO>> getAllServidors(Pageable pageable) {
        log.debug("REST request to get a page of Servidors");
        Page<ServidorDTO> page = servidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servidors/:id} : get the "id" servidor.
     *
     * @param id the id of the servidorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servidorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servidors/{id}")
    public ResponseEntity<ServidorDTO> getServidor(@PathVariable Long id) {
        log.debug("REST request to get Servidor : {}", id);
        Optional<ServidorDTO> servidorDTO = servidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servidorDTO);
    }

    /**
     * {@code DELETE  /servidors/:id} : delete the "id" servidor.
     *
     * @param id the id of the servidorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servidors/{id}")
    public ResponseEntity<Void> deleteServidor(@PathVariable Long id) {
        log.debug("REST request to delete Servidor : {}", id);
        servidorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/servidors?query=:query} : search for the servidor corresponding
     * to the query.
     *
     * @param query the query of the servidor search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/servidors")
    public ResponseEntity<List<ServidorDTO>> searchServidors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Servidors for query {}", query);
        Page<ServidorDTO> page = servidorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/servidors/_resumo")
    public ResponseEntity<Page<ServidorResumo>> findAllProjectedServidorResumoBy(@RequestParam(required = false,defaultValue = "") String matricula, Pageable pageable) {
        return ResponseEntity.ok(servidorService.findAllProjectedServidorResumoBy(matricula,pageable));
    }
}
