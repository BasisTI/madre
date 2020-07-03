package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.EnderecoService;
import br.com.basis.madre.service.dto.EnderecoDTO;
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
 * REST controller for managing {@link br.com.basis.madre.domain.Endereco}.
 */
@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "pacientesEndereco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoService enderecoService;

    public EnderecoResource(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    /**
     * {@code POST  /enderecos} : Create a new endereco.
     *
     * @param enderecoDTO the enderecoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoDTO, or with status {@code 400 (Bad Request)} if the endereco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> createEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() != null) {
            throw new BadRequestAlertException("A new endereco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enderecos} : Updates an existing endereco.
     *
     * @param enderecoDTO the enderecoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enderecos")
    public ResponseEntity<EnderecoDTO> updateEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", enderecoDTO);
        if (enderecoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, enderecoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enderecos} : get all the enderecos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecos in body.
     */
    @GetMapping("/enderecos")
    public ResponseEntity<List<EnderecoDTO>> getAllEnderecos(Pageable pageable) {
        log.debug("REST request to get a page of Enderecos");
        Page<EnderecoDTO> page = enderecoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enderecos/:id} : get the "id" endereco.
     *
     * @param id the id of the enderecoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enderecos/{id}")
    public ResponseEntity<EnderecoDTO> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<EnderecoDTO> enderecoDTO = enderecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoDTO);
    }

    /**
     * {@code DELETE  /enderecos/:id} : delete the "id" endereco.
     *
     * @param id the id of the enderecoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/enderecos?query=:query} : search for the endereco corresponding
     * to the query.
     *
     * @param query the query of the endereco search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/enderecos")
    public ResponseEntity<List<EnderecoDTO>> searchEnderecos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Enderecos for query {}", query);
        Page<EnderecoDTO> page = enderecoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
