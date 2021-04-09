package br.com.basis.madre.web.rest;

import br.com.basis.madre.service.DDDService;
import br.com.basis.madre.service.dto.DddDto;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.basis.madre.domain.DDD}.
 */
@RestController
@RequestMapping("/api")
public class DDDResource {

    private final Logger log = LoggerFactory.getLogger(DDDResource.class);

    private static final String ENTITY_NAME = "DDD";

    private final DDDService dddService;

    public DDDResource(DDDService dddService) {
        this.dddService = dddService;
    }

    /**
     * {@code POST  /ddd} : Create a new ddd.
     *
     * @param dddDTO the dddDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dddDTO, or with status {@code 400 (Bad Request)} if the ddd has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ddd")
    public ResponseEntity<DddDto> createDDD(@Valid @RequestBody DddDto dddDTO) throws URISyntaxException {
        log.debug("REST request to save DDD : {}", dddDTO);
        if (dddDTO.getId () != null) {
            throw new BadRequestAlertException("A new ddd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DddDto result = dddService.save(dddDTO);
        URI uri = new URI("/api/ddd/" + result.getId());
        return ResponseEntity.created(uri).body(result);
    }

    /**
     * {@code PUT  /ddd} : Updates an existing ddd.
     *
     * @param dddDTO the dddDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dddDTO,
     * or with status {@code 400 (Bad Request)} if the dddDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dddDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ddd")
    public ResponseEntity<DddDto> updateDDD(@Valid @RequestBody DddDto dddDTO) throws URISyntaxException {
        log.debug("REST request to update DDD : {}", dddDTO);
        if (dddDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DddDto result = dddService.save(dddDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/list/ddd")
    public ResponseEntity<List<DddDto>> getAllDDD(DddDto dddDTO, @PageableDefault(value = 100) Pageable pageable) {
        log.debug("REST request to get a page of DDD");
        Page<DddDto> page = dddService.findAll(dddDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ddd/:id} : get the "id" ddd.
     *
     * @param id the id of the dddDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dddDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ddd/{id}")
    public ResponseEntity<DddDto> getDDD(@PathVariable Long id) {
        log.debug("REST request to get DDD : {}", id);
        Optional<DddDto> dddDTO = dddService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dddDTO);
    }

}
