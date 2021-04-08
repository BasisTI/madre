package br.com.basis.madre.web.rest;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.repository.TriagemRepository;
import br.com.basis.madre.repository.search.TriagemSearchRepository;
import br.com.basis.madre.service.TriagemService;
import br.com.basis.madre.service.dto.TriagemDTO;
import br.com.basis.madre.service.projection.TriagemProjection;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@RestController
@RequestMapping("/api")
public class TriagemResource {

    private final Logger log = LoggerFactory.getLogger(TriagemResource.class);

    private static final String ENTITY_NAME = "pacientesTriagem";

    private final TriagemRepository triagemRepository;

    private final TriagemSearchRepository triagemSearchRepository;
    private String applicationName;

    private final TriagemService triagemService;


    public TriagemResource(TriagemRepository triagemRepository, TriagemSearchRepository triagemSearchRepository, TriagemService triagemService) {
        this.triagemRepository = triagemRepository;
        this.triagemSearchRepository = triagemSearchRepository;
        this.triagemService = triagemService;
    }

    @PostMapping("/triagens")
    @Timed
    public ResponseEntity<TriagemDTO> createTriagem(@Valid @RequestBody TriagemDTO triagemDTO)
        throws URISyntaxException {
        log.debug("REST request to save Triagem : {}", triagemDTO);
        if (triagemDTO.getId() != null) {
            throw new BadRequestAlertException("A new triagem cannot already have an ID", ENTITY_NAME,
                "idexists");
        }
        TriagemDTO result = triagemService.save(triagemDTO);
        return ResponseEntity.created(new URI("/api/triagens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
                result.getId().toString()))
        .body(result);
    }

    @PutMapping("/triagens")
    @Timed
    public ResponseEntity<TriagemDTO> updateTriagem(@Valid @RequestBody TriagemDTO triagemDTO) {
        log.debug("REST request to update Triagem : {}", triagemDTO);
        if (triagemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TriagemDTO result = triagemService.save(triagemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                triagemDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/triagens/{id}")
    @Timed
    public ResponseEntity<Void> deleteTriagem(@PathVariable Long id) {
        log.debug("REST request to delete Triagem : {}", id);

        triagemRepository.deleteById(id);
        triagemSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(
            applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/triagens/{id}")
    public ResponseEntity<Triagem> getTriagem(@PathVariable Long id) {
        log.debug("REST request to get Triagem : {}", id);
        Optional<Triagem> triagem = triagemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(triagem);
    }

    @GetMapping("/triagens/listagem")
    @Timed
    public ResponseEntity<Page<TriagemProjection>> buscarResumoTriagem(Pageable pageable) {
        log.debug("REST request to get all Triagens");
        return ResponseEntity.ok(triagemService.buscarResumoTriagem(pageable));
    }

    @GetMapping("/_search/triagens")
    @Timed
    public List<Triagem> searchTriagens(@RequestParam String query) {
        log.debug("REST request to search Triagens for query {}", query);
        return StreamSupport
            .stream(triagemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
