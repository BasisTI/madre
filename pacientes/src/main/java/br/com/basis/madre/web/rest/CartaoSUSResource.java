package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.CartaoSUSService;
import br.com.basis.madre.service.dto.CartaoSUSDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.CartaoSUS}.
 */
@RestController
@RequestMapping("/api")
public class CartaoSUSResource {

    private final Logger log = LoggerFactory.getLogger(CartaoSUSResource.class);

    private static final String ENTITY_NAME = "pacientesCartaoSus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartaoSUSService cartaoSUSService;

    public CartaoSUSResource(CartaoSUSService cartaoSUSService) {
        this.cartaoSUSService = cartaoSUSService;
    }

    /**
     * {@code POST  /cartao-suses} : Create a new cartaoSUS.
     *
     * @param cartaoSUSDTO the cartaoSUSDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartaoSUSDTO, or with status {@code 400 (Bad Request)} if the cartaoSUS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cartao-suses")
    public ResponseEntity<CartaoSUSDTO> createCartaoSUS(@Valid @RequestBody CartaoSUSDTO cartaoSUSDTO) throws URISyntaxException {
        log.debug("REST request to save CartaoSUS : {}", cartaoSUSDTO);
        if (cartaoSUSDTO.getId() != null) {
            throw new BadRequestAlertException("A new cartaoSUS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartaoSUSDTO result = cartaoSUSService.save(cartaoSUSDTO);
        return ResponseEntity.created(new URI("/api/cartao-suses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cartao-suses} : Updates an existing cartaoSUS.
     *
     * @param cartaoSUSDTO the cartaoSUSDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartaoSUSDTO,
     * or with status {@code 400 (Bad Request)} if the cartaoSUSDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartaoSUSDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cartao-suses")
    public ResponseEntity<CartaoSUSDTO> updateCartaoSUS(@Valid @RequestBody CartaoSUSDTO cartaoSUSDTO) throws URISyntaxException {
        log.debug("REST request to update CartaoSUS : {}", cartaoSUSDTO);
        if (cartaoSUSDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartaoSUSDTO result = cartaoSUSService.save(cartaoSUSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cartaoSUSDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cartao-suses} : get all the cartaoSUSES.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartaoSUSES in body.
     */
    @GetMapping("/cartao-suses")
    public ResponseEntity<List<CartaoSUSDTO>> getAllCartaoSUSES(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("paciente-is-null".equals(filter)) {
            log.debug("REST request to get all CartaoSUSs where paciente is null");
            return new ResponseEntity<>(cartaoSUSService.findAllWherePacienteIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of CartaoSUSES");
        Page<CartaoSUSDTO> page = cartaoSUSService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cartao-suses/:id} : get the "id" cartaoSUS.
     *
     * @param id the id of the cartaoSUSDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartaoSUSDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cartao-suses/{id}")
    public ResponseEntity<CartaoSUSDTO> getCartaoSUS(@PathVariable Long id) {
        log.debug("REST request to get CartaoSUS : {}", id);
        Optional<CartaoSUSDTO> cartaoSUSDTO = cartaoSUSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartaoSUSDTO);
    }

    /**
     * {@code DELETE  /cartao-suses/:id} : delete the "id" cartaoSUS.
     *
     * @param id the id of the cartaoSUSDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cartao-suses/{id}")
    public ResponseEntity<Void> deleteCartaoSUS(@PathVariable Long id) {
        log.debug("REST request to delete CartaoSUS : {}", id);
        cartaoSUSService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cartao-suses?query=:query} : search for the cartaoSUS corresponding
     * to the query.
     *
     * @param query the query of the cartaoSUS search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cartao-suses")
    public ResponseEntity<List<CartaoSUSDTO>> searchCartaoSUSES(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CartaoSUSES for query {}", query);
        Page<CartaoSUSDTO> page = cartaoSUSService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
