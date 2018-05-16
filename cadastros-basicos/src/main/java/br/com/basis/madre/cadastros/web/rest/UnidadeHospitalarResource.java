package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.UnidadeHospitalar;
import br.com.basis.madre.cadastros.domain.UploadFile;
import br.com.basis.madre.cadastros.repository.UnidadeHospitalarRepository;
import br.com.basis.madre.cadastros.repository.UploadedFilesRepository;
import br.com.basis.madre.cadastros.service.UnidadeHospitalarService;
import br.com.basis.madre.cadastros.service.dto.UnidadeHospitalarDTO;
import br.com.basis.madre.cadastros.service.exception.RelatorioException;
import br.com.basis.madre.cadastros.service.exception.UnidadeHospitalarException;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.errors.UploadException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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
     * @param unidadeHospitalarDTO the unidadeHospitalar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidadeHospitalar, or with status 400 (Bad Request) if the unidadeHospitalar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidade-hospitalars")
    @Timed
    public ResponseEntity<UnidadeHospitalarDTO> createUnidadeHospitalar(
        @Valid @RequestBody UnidadeHospitalarDTO unidadeHospitalarDTO) throws URISyntaxException {
        try {
            log.debug("REST request to save UnidadeHospitalar : {}", unidadeHospitalarDTO);
            if (validaDTO(unidadeHospitalarDTO)) {
                return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "dataexists", "Data already in use"))
                    .body(null);
            }
            UnidadeHospitalarDTO result = unidadeHospitalarService.save(unidadeHospitalarDTO);
            return ResponseEntity.created(new URI("/api/unidade-hospitalars/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (UnidadeHospitalarException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, UnidadeHospitalarException.getCodeRegistroExisteBase(), e.getMessage()))
                .body(unidadeHospitalarDTO);
        }

    }

    private boolean validaDTO(@Valid @RequestBody UnidadeHospitalarDTO unidadeHospitalarDTO) {
        if (unidadeHospitalarDTO.getId() != null) {
            throw new BadRequestAlertException("A new parecerPadrao cannot already have an ID", ENTITY_NAME, "idexists");
        }

        //Lança uma  exceção se um dos campos já estiver cadastrado no banco de dados
        if (unidadeHospitalarRepository.findOneByCnpj(unidadeHospitalarDTO.getCnpj()).isPresent() ||
            unidadeHospitalarRepository.findOneByNomeIgnoreCase(unidadeHospitalarDTO.getNome()).isPresent() ||
            unidadeHospitalarRepository.findOneBySiglaIgnoreCase(unidadeHospitalarDTO.getSigla()).isPresent() ||
            unidadeHospitalarRepository.findOneByEnderecoIgnoreCase(unidadeHospitalarDTO.getEndereco()).isPresent()
            ) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * PUT  /unidade-hospitalars : Updates an existing unidadeHospitalar.
     *
     * @param unidadeHospitalarDTO the unidadeHospitalar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidadeHospitalar,
     * or with status 400 (Bad Request) if the unidadeHospitalar is not valid,
     * or with status 500 (Internal Server Error) if the unidadeHospitalar couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidade-hospitalars")
    @Timed
    public ResponseEntity<UnidadeHospitalarDTO> updateUnidadeHospitalar(
        @Valid @RequestBody UnidadeHospitalarDTO unidadeHospitalarDTO) throws URISyntaxException {
        try {
            log.debug("REST request to update UnidadeHospitalar : {}", unidadeHospitalarDTO);
            if (unidadeHospitalarDTO.getId() == null) {
                return createUnidadeHospitalar(unidadeHospitalarDTO);
            }
            if (validaCnpjSigla(unidadeHospitalarDTO)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "dataexists", "Data already in use")).body(null);
            }
            UnidadeHospitalarDTO result = unidadeHospitalarService.save(unidadeHospitalarDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidadeHospitalarDTO.getId().toString()))
                .body(result);
        } catch (UnidadeHospitalarException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, UnidadeHospitalarException.getCodeRegistroExisteBase(), e.getMessage()))
                .body(unidadeHospitalarDTO);
        }
    }

    private boolean validaCnpjSigla(@Valid @RequestBody UnidadeHospitalarDTO unidadeHospitalarDTO) {
        if (unidadeHospitalarRepository.findOneByCnpjAndSiglaIgnoreCase(unidadeHospitalarDTO.getCnpj(), unidadeHospitalarDTO.getSigla()).isPresent()) {
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
    public ResponseEntity<List<UnidadeHospitalarDTO>> getAllUnidadeHospitalars(
        @RequestParam(value = "query") Optional<String> query,
        @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UnidadeHospitalars");
        Page<UnidadeHospitalarDTO> page = unidadeHospitalarService.findAll(query, pageable);
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
    public ResponseEntity<UnidadeHospitalarDTO> getUnidadeHospitalar(@PathVariable Long id) {
        log.debug("REST request to get UnidadeHospitalar : {}", id);
        UnidadeHospitalarDTO unidadeHospitalarDTO = unidadeHospitalarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(unidadeHospitalarDTO));
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
    public ResponseEntity<List<UnidadeHospitalar>> searchUnidadeHospitalars(
        @RequestParam(defaultValue = "*") String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeHospitalars for query {}", query);
        Page<UnidadeHospitalar> page = unidadeHospitalarService.search(query, pageable);
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
            return unidadeHospitalarService.gerarRelatorioExportacao(tipoRelatorio,query);
        } catch (RelatorioException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, RelatorioException.getCodeEntidade(), e.getMessage())).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadFile> singleFileUpload(@RequestParam("file") MultipartFile file,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes) {

        UploadFile uploadFile = new UploadFile();
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            String classPathString = this.getClass().getClassLoader().getResource("").toString();
            Path classPath = Paths.get(classPathString).toAbsolutePath();
            String folderPathString = classPath.toString();

            File directory = new File(folderPathString);
            if (!directory.exists()) { directory.mkdirs(); }

            byte[] bytesFileName = (file.getOriginalFilename() + String.valueOf(System.currentTimeMillis())).getBytes("UTF-8");
            String filename = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(bytesFileName));
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            filename += "." + ext;
            Path path = Paths.get(folderPathString + "/" + filename);
            System.out.println(path);
            Files.write(path, bytes);

            setUploadFile(uploadFile, file,filename, bytes);

        } catch (IOException | NoSuchAlgorithmException e) { throw new UploadException("Erro ao efetuar o upload do arquivo", e); }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uploadFile));
    }
    public void setUploadFile(UploadFile uploadFile, MultipartFile file, String filename,  byte[] bytes){
        uploadFile.setDateOf(new Date());
        uploadFile.setOriginalName(file.getOriginalFilename());
        uploadFile.setFilename(filename);
        uploadFile.setSizeOf(bytes.length);
        filesRepository.save(uploadFile);
    }

}
