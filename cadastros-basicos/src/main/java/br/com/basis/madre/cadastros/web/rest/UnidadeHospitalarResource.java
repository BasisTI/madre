package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.UploadedFilesRepository;
import br.com.basis.madre.cadastros.service.UnidadeHospitalarService;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.exception.UnidadeHospitalarException;
import br.com.basis.madre.cadastros.util.MadreUtil;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UnidadeHospitalar.
 */
@RestController
@RequestMapping("/api")
public class UnidadeHospitalarResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeHospitalarResource.class);

    private static final String ENTITY_NAME = "unidadeHospitalar";

    private final UnidadeHospitalarService unidadeHospitalarService;

    private final UnidadeHospitalarRepository unidadeHospitalarRepository;

    @Autowired
    private UploadedFilesRepository filesRepository;

    public UnidadeHospitalarResource(UnidadeHospitalarService unidadeHospitalarService,
                                     UnidadeHospitalarRepository unidadeHospitalarRepository) {
        this.unidadeHospitalarService = unidadeHospitalarService;
        this.unidadeHospitalarRepository = unidadeHospitalarRepository;
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
    public ResponseEntity<UnidadeHospitalar> createUnidadeHospitalar(
        @Valid @RequestBody UnidadeHospitalar unidadeHospitalar) throws URISyntaxException {
        try {
            log.debug("REST request to save UnidadeHospitalar : {}", unidadeHospitalar);
            if (unidadeHospitalar.getId() != null) {
                throw new BadRequestAlertException("A new unidadeHospitalar cannot already have an ID", ENTITY_NAME, "idexists");
            }
            if (validaNome(unidadeHospitalar) || validaSigla(unidadeHospitalar)) {
                return ResponseEntity.badRequest() .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "unidadeexists", "Nome/Sigla already in use")) .body(null);
            }
            UnidadeHospitalar result = unidadeHospitalarService.save(unidadeHospitalar);
            return ResponseEntity.created(new URI("/api/unidade-hospitalars/" + result.getId())) .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())) .body(result);
        } catch (UnidadeHospitalarException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest() .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, UnidadeHospitalarException.getCodeRegistroExisteBase(), e.getMessage())) .body(unidadeHospitalar);
        }

    }

    private boolean validaNome(@Valid @RequestBody UnidadeHospitalar unidadeHospitalar) {
        //Lança uma  exceção se um dos campos já estiver cadastrado no banco de dados
        if (unidadeHospitalarRepository.findOneByNomeIgnoreCase(MadreUtil.removeCaracteresEmBranco(unidadeHospitalar.getNome())).isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private boolean validaSigla(@Valid @RequestBody UnidadeHospitalar unidadeHospitalar) {
        //Lança uma  exceção se um dos campos já estiver cadastrado no banco de dados
        if (unidadeHospitalarRepository.findOneBySiglaIgnoreCase(MadreUtil.removeCaracteresEmBranco(unidadeHospitalar.getSigla())).isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
    public ResponseEntity<UnidadeHospitalar> updateUnidadeHospitalar(
        @Valid @RequestBody UnidadeHospitalar unidadeHospitalar) throws URISyntaxException {
        try {
            log.debug("REST request to update UnidadeHospitalar : {}", unidadeHospitalar);
            if (unidadeHospitalar.getId() == null) {
                return createUnidadeHospitalar(unidadeHospitalar);
            }
            if (validaEdicao(unidadeHospitalar)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "unidadeexists", "Nome/Sigla already in use")).body(null);
            }
            UnidadeHospitalar result = unidadeHospitalarService.save(unidadeHospitalar);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidadeHospitalar.getId().toString()))
                .body(result);
        } catch (UnidadeHospitalarException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, UnidadeHospitalarException.getCodeRegistroExisteBase(), e.getMessage()))
                .body(unidadeHospitalar);
        }
    }

    private boolean validaEdicao(@Valid @RequestBody UnidadeHospitalar unidadeHospitalar) {
        if (!(unidadeHospitalarRepository.findOneByIdAndNomeIgnoreCase(unidadeHospitalar.getId(), MadreUtil.removeCaracteresEmBranco(unidadeHospitalar.getNome())).isPresent()) && (validaNome(unidadeHospitalar))) {
            return Boolean.TRUE;
        }

        if (!(unidadeHospitalarRepository.findOneByIdAndSiglaIgnoreCase(unidadeHospitalar.getId(), MadreUtil.removeCaracteresEmBranco(unidadeHospitalar.getSigla())).isPresent()) && (validaSigla(unidadeHospitalar))) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * GET  /unidade-hospitalars : get all the unidadeHospitalars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of unidadeHospitalars in body
     */
    @GetMapping("/unidade-hospitalars")
    @Timed
    public ResponseEntity<List<UnidadeHospitalar>> getAllUnidadeHospitalars(
        @RequestParam(value = "query") Optional<String> query,
        @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UnidadeHospitalars");
        Page<UnidadeHospitalar> page = unidadeHospitalarService.findAll(query, pageable);
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
     * @param query    the query of the unidadeHospitalar search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/unidade-hospitalars")
    @Timed
    public ResponseEntity<List<UnidadeHospitalar>> searchUnidadeHospitalars(
        @RequestParam(defaultValue = "*") String query, Pageable pageable,@RequestParam String order, @RequestParam int size, @RequestParam(name="page") int pageNumber,@RequestParam(defaultValue="id") String sort) {
        log.debug("REST request to search for a page of UnidadeHospitalars for query {}", query);
        Sort.Direction sortOrder = PaginationUtil.getSortDirection(order);
        Pageable newPageable = new PageRequest(pageNumber, size, sortOrder, sort);
        Page<UnidadeHospitalar> page = unidadeHospitalarService.search(query, newPageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/unidade-hospitalars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usuarios/:id : get jasper of  usuarios.
     *
     * @param tipoRelatorio
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping(value = "/unidadehospitalar/exportacao/{tipoRelatorio}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    public ResponseEntity<InputStreamResource> getRelatorioExportacao(@PathVariable String tipoRelatorio, @RequestParam(defaultValue = "*") String query) {
        try {
            return unidadeHospitalarService.gerarRelatorioExportacao(tipoRelatorio, query);
        } catch (RelatorioException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, RelatorioException.getCodeEntidade(), e.getMessage())).body(null);
        }
    }

    @PostMapping("/upload")
    public UnidadeHospitalar singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        UnidadeHospitalar unidadeHospitalar = new UnidadeHospitalar();
        unidadeHospitalar.setLogo(bytes);
        return unidadeHospitalar;
    }
}
