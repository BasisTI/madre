package br.com.basis.madre.cadastros.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.basis.madre.cadastros.domain.TaUsuarioUnidadeHospitalar;
import br.com.basis.madre.cadastros.service.TaUsuarioUnidadeHospitalarService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaUsuarioUnidadeHospitalar.
 */
@RestController
@RequestMapping("/api")
public class TaUsuarioUnidadeHospitalarResource {

    private final Logger log = LoggerFactory.getLogger(TaUsuarioUnidadeHospitalarResource.class);

    private static final String ENTITY_NAME = "taUsuarioUnidadeHospitalar";

    private final TaUsuarioUnidadeHospitalarService taUsuarioUnidadeHospitalarService;

    public TaUsuarioUnidadeHospitalarResource(TaUsuarioUnidadeHospitalarService taUsuarioUnidadeHospitalarService) {
        this.taUsuarioUnidadeHospitalarService = taUsuarioUnidadeHospitalarService;
    }

    /**
     * POST  /ta-usuario-unidade-hospitalars : Create a new taUsuarioUnidadeHospitalar.
     *
     * @param taUsuarioUnidadeHospitalar the taUsuarioUnidadeHospitalar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taUsuarioUnidadeHospitalar, or with status 400 (Bad Request) if the taUsuarioUnidadeHospitalar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ta-usuario-unidade-hospitalars")
    @Timed
    public ResponseEntity<TaUsuarioUnidadeHospitalar> createTaUsuarioUnidadeHospitalar(@RequestBody TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar) throws URISyntaxException {
        log.debug("REST request to save TaUsuarioUnidadeHospitalar : {}", taUsuarioUnidadeHospitalar);
        if (taUsuarioUnidadeHospitalar.getId() != null) {
            throw new BadRequestAlertException("A new taUsuarioUnidadeHospitalar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaUsuarioUnidadeHospitalar result = taUsuarioUnidadeHospitalarService.save(taUsuarioUnidadeHospitalar);
        return ResponseEntity.created(new URI("/api/ta-usuario-unidade-hospitalars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ta-usuario-unidade-hospitalars : Updates an existing taUsuarioUnidadeHospitalar.
     *
     * @param taUsuarioUnidadeHospitalar the taUsuarioUnidadeHospitalar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taUsuarioUnidadeHospitalar,
     * or with status 400 (Bad Request) if the taUsuarioUnidadeHospitalar is not valid,
     * or with status 500 (Internal Server Error) if the taUsuarioUnidadeHospitalar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ta-usuario-unidade-hospitalars")
    @Timed
    public ResponseEntity<TaUsuarioUnidadeHospitalar> updateTaUsuarioUnidadeHospitalar(@RequestBody TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar) throws URISyntaxException {
        log.debug("REST request to update TaUsuarioUnidadeHospitalar : {}", taUsuarioUnidadeHospitalar);
        if (taUsuarioUnidadeHospitalar.getId() == null) {
            return createTaUsuarioUnidadeHospitalar(taUsuarioUnidadeHospitalar);
        }
        TaUsuarioUnidadeHospitalar result = taUsuarioUnidadeHospitalarService.save(taUsuarioUnidadeHospitalar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taUsuarioUnidadeHospitalar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ta-usuario-unidade-hospitalars : get all the taUsuarioUnidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taUsuarioUnidadeHospitalars in body
     */
    @GetMapping("/ta-usuario-unidade-hospitalars")
    @Timed
    public ResponseEntity<List<TaUsuarioUnidadeHospitalar>> getAllTaUsuarioUnidadeHospitalars(Pageable pageable) {
        log.debug("REST request to get a page of TaUsuarioUnidadeHospitalars");
        Page<TaUsuarioUnidadeHospitalar> page = taUsuarioUnidadeHospitalarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ta-usuario-unidade-hospitalars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ta-usuario-unidade-hospitalars/:id : get the "id" taUsuarioUnidadeHospitalar.
     *
     * @param id the id of the taUsuarioUnidadeHospitalar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taUsuarioUnidadeHospitalar, or with status 404 (Not Found)
     */
    @GetMapping("/ta-usuario-unidade-hospitalars/{id}")
    @Timed
    public ResponseEntity<TaUsuarioUnidadeHospitalar> getTaUsuarioUnidadeHospitalar(@PathVariable Long id) {
        log.debug("REST request to get TaUsuarioUnidadeHospitalar : {}", id);
        TaUsuarioUnidadeHospitalar taUsuarioUnidadeHospitalar = taUsuarioUnidadeHospitalarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taUsuarioUnidadeHospitalar));
    }

    /**
     * DELETE  /ta-usuario-unidade-hospitalars/:id : delete the "id" taUsuarioUnidadeHospitalar.
     *
     * @param id the id of the taUsuarioUnidadeHospitalar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ta-usuario-unidade-hospitalars/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaUsuarioUnidadeHospitalar(@PathVariable Long id) {
        log.debug("REST request to delete TaUsuarioUnidadeHospitalar : {}", id);
        taUsuarioUnidadeHospitalarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ta-usuario-unidade-hospitalars?query=:query : search for the taUsuarioUnidadeHospitalar corresponding
     * to the query.
     *
     * @param query the query of the taUsuarioUnidadeHospitalar search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ta-usuario-unidade-hospitalars")
    @Timed
    public ResponseEntity<List<TaUsuarioUnidadeHospitalar>> searchTaUsuarioUnidadeHospitalars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaUsuarioUnidadeHospitalars for query {}", query);
        Page<TaUsuarioUnidadeHospitalar> page = taUsuarioUnidadeHospitalarService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ta-usuario-unidade-hospitalars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
