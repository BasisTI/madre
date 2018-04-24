package br.com.basis.madre.cadastros.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.service.UnidadeHospitalarService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing UnidadeHospitalar.
 */
@RestController
@RequestMapping("/api")
public class UnidadeHospitalarResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeHospitalarResource.class);

    private static final String ENTITY_NAME = "unidadeHospitalar";

    private final UnidadeHospitalarService unidadeHospitalarService;

    public UnidadeHospitalarResource(UnidadeHospitalarService unidadeHospitalarService) {
        this.unidadeHospitalarService = unidadeHospitalarService;
    }

    /**
     * POST  /unidade-hospitalars : Create a new unidadeHospitalar.
     *
     * @param unidadeHospitalar the unidadeHospitalar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidadeHospitalar, or with status 400 (Bad Request) if the unidadeHospitalar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidade-hospitalars")
    @Timed
    public ResponseEntity<UnidadeHospitalar> createUnidadeHospitalar(@Valid @RequestBody UnidadeHospitalar unidadeHospitalar) throws URISyntaxException {
        log.debug("REST request to save UnidadeHospitalar : {}", unidadeHospitalar);
        if (unidadeHospitalar.getId() != null) {
            throw new BadRequestAlertException("A new unidadeHospitalar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeHospitalar result = unidadeHospitalarService.save(unidadeHospitalar);
        return ResponseEntity.created(new URI("/api/unidade-hospitalars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidade-hospitalars : Updates an existing unidadeHospitalar.
     *
     * @param unidadeHospitalar the unidadeHospitalar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidadeHospitalar,
     * or with status 400 (Bad Request) if the unidadeHospitalar is not valid,
     * or with status 500 (Internal Server Error) if the unidadeHospitalar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidade-hospitalars")
    @Timed
    public ResponseEntity<UnidadeHospitalar> updateUnidadeHospitalar(@Valid @RequestBody UnidadeHospitalar unidadeHospitalar) throws URISyntaxException {
        log.debug("REST request to update UnidadeHospitalar : {}", unidadeHospitalar);
        if (unidadeHospitalar.getId() == null) {
            return createUnidadeHospitalar(unidadeHospitalar);
        }
        UnidadeHospitalar result = unidadeHospitalarService.save(unidadeHospitalar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidadeHospitalar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidade-hospitalars : get all the unidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of unidadeHospitalars in body
     */
    @GetMapping("/unidade-hospitalars")
    @Timed
    public ResponseEntity<List<UnidadeHospitalar>> getAllUnidadeHospitalars(Pageable pageable) {
        log.debug("REST request to get a page of UnidadeHospitalars");
        Page<UnidadeHospitalar> page = unidadeHospitalarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unidade-hospitalars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unidade-hospitalars/:id : get the "id" unidadeHospitalar.
     *
     * @param id the id of the unidadeHospitalar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidadeHospitalar, or with status 404 (Not Found)
     */
    @GetMapping("/unidade-hospitalars/{id}")
    @Timed
    public ResponseEntity<UnidadeHospitalar> getUnidadeHospitalar(@PathVariable Long id) {
        log.debug("REST request to get UnidadeHospitalar : {}", id);
        UnidadeHospitalar unidadeHospitalar = unidadeHospitalarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(unidadeHospitalar));
    }

    /**
     * DELETE  /unidade-hospitalars/:id : delete the "id" unidadeHospitalar.
     *
     * @param id the id of the unidadeHospitalar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unidade-hospitalars/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnidadeHospitalar(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeHospitalar : {}", id);
        unidadeHospitalarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/unidade-hospitalars?query=:query : search for the unidadeHospitalar corresponding
     * to the query.
     *
     * @param query the query of the unidadeHospitalar search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/unidade-hospitalars")
    @Timed
    public ResponseEntity<List<UnidadeHospitalar>> searchUnidadeHospitalars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeHospitalars for query {}", query);
        Page<UnidadeHospitalar> page = unidadeHospitalarService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/unidade-hospitalars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
