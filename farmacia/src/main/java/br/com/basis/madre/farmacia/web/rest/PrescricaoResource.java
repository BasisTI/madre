package br.com.basis.madre.farmacia.web.rest;

import br.com.basis.madre.farmacia.domain.Prescricao;
import br.com.basis.madre.farmacia.repository.search.PrescricaoSerchRepository;
import br.com.basis.madre.farmacia.service.dto.ApresentacaoDTO;
import br.com.basis.madre.farmacia.service.projection.PrescricaoLocal;
import br.com.basis.madre.farmacia.web.rest.errors.BadRequestAlertException;
import com.github.javafaker.Faker;
import io.github.jhipster.web.util.HeaderUtil;
import joptsimple.internal.Strings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@RestController
@RequestMapping("/api")
public class PrescricaoResource {

    @Autowired
    private PrescricaoSerchRepository prescricaoRepositorySearch;

    private static final String ENTITY_NAME = "prescricao";
    private String applicationName;

    private Faker faker = new Faker(new Locale("pt-BR"));



    @GetMapping("/prescricao")
    public Page<Prescricao> listarFarmacia(String nome, String dataInicio, String local, Pageable pageable) throws ParseException {



        if (Strings.isNullOrEmpty(nome) && Strings.isNullOrEmpty(local) && Strings.isNullOrEmpty(dataInicio)) {
            Page<Prescricao> search = prescricaoRepositorySearch.search(new NativeSearchQueryBuilder()
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("id", "nome", "dataInicio", "local").build())
                .withPageable(pageable)
                .build());
            return search;
        }
        if (!Strings.isNullOrEmpty(dataInicio)) {
            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()

                .withQuery(matchQuery("dataInicio", dataInicio))
                .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("id", "nome", "dataInicio", "local"
                ).build())
                .withPageable(pageable)
                .build();
            Page<Prescricao> query = prescricaoRepositorySearch.search(
                nativeSearchQuery);
            return query;
        }

        NativeSearchQuery nativeSearchQueryFuzzy = new NativeSearchQueryBuilder()

            .withQuery(QueryBuilders.multiMatchQuery(local + nome, "local", "nome")
                .field("nome").field("local").operator(Operator.AND).fuzziness(Fuzziness.ONE).prefixLength(5))
            .withSourceFilter(new FetchSourceFilterBuilder().withIncludes("id", "nome", "dataInicio", "local"
            ).build())
            .withPageable(pageable)
            .build();
        Page<Prescricao> queryFuzzy = prescricaoRepositorySearch.search(
            nativeSearchQueryFuzzy);

        return queryFuzzy;

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


    @GetMapping("/fillData")
    public String fillDatabase() {

        prescricaoRepositorySearch.deleteAll();
        return "OK";

    }
    @GetMapping("/prescricao/{id}")
    public Optional<Prescricao> getPorId( @PathVariable Long id){

        return  prescricaoRepositorySearch.findById(id);
    }

    @PutMapping("/prescricao")
    public ResponseEntity<Prescricao> putPrescricao(Prescricao prescricao) {
        if (prescricao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", IdentifierGenerator.ENTITY_NAME, "idnull");
        }
        Prescricao result = prescricaoRepositorySearch.save(prescricao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prescricao.getId().toString()))
            .body(result);
    }
    }


