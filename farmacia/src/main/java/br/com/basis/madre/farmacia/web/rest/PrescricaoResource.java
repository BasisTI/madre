package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.repository.search.PrescricaoSearchRepository;
import br.com.basis.madre.farmacia.service.projection.PrescricaoLocal;
import com.github.javafaker.Faker;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import joptsimple.internal.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PrescricaoResource {

    private static final String ENTITY_NAME = "prescricaoPrescricao";
    @Autowired
    private PrescricaoSearchRepository prescricaoRepositorySearch;

    private final Logger log = LoggerFactory.getLogger(PrescricaoSearchRepository.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private Faker faker = new Faker(new Locale("pt-BR"));

    @GetMapping("/prescricao")
    public Page<Prescricao> listarFarmacia(String nome, String dataInicio, Pageable pageable) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        filtraNome(queryBuilder, nome);
        filtraDataInicio(queryBuilder, dataInicio);

        SearchQuery query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withPageable(pageable)
            .build();

        return prescricaoRepositorySearch.search(query);

    }

    private void filtraNome(BoolQueryBuilder queryBuilder, String nome) {
        if(!Strings.isNullOrEmpty(nome)) {
            queryBuilder.must(QueryBuilders.matchQuery("nome", nome));
        }
    }

    private void filtraDataInicio(BoolQueryBuilder queryBuilder, String dataInicio) {
        if(!Strings.isNullOrEmpty(dataInicio)) {
            queryBuilder.must(QueryBuilders.matchQuery("dataInicio", dataInicio));
        }
    }

    @GetMapping("/prescricao-local")
    public Page<PrescricaoLocal> listarUnidades(String local, Pageable pageable) {
        if (Strings.isNullOrEmpty(local)) {
            Page<PrescricaoLocal> prescricaoPage = prescricaoRepositorySearch.findAllPrescricaoLocalBy(pageable);
            return prescricaoPage;
        }

        Page<PrescricaoLocal> prescricaoPage = prescricaoRepositorySearch.findAllByLocal(local, pageable);

        return prescricaoPage;
    }

    @GetMapping("/prescricao/{id}")
    public ResponseEntity<Prescricao> getPrescricao(@PathVariable Long id) {
        log.debug("REST request to get Prescrição : {}", id);
        Optional<Prescricao> prescricaoDTO = prescricaoRepositorySearch.findById(id);
        return ResponseUtil.wrapOrNotFound(prescricaoDTO);
    }

    @DeleteMapping("/prescricao/{id}")
    public ResponseEntity<Void> deletePrescricao(@PathVariable Long id) {
        log.debug("REST request to delete Prescricao : {}", id);
        prescricaoRepositorySearch.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

}
