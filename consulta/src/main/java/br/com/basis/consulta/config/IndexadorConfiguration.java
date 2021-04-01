package br.com.basis.consulta.config;

import br.com.basis.consulta.domain.Emergencia;
import br.com.basis.consulta.repository.EmergenciaRepository;
import br.com.basis.consulta.repository.search.EmergenciaSearchRepository;
import br.gov.nuvem.comum.microsservico.service.reindex.Indexador;
import br.gov.nuvem.comum.microsservico.service.reindex.IndexadorSemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;


@Configuration
public class IndexadorConfiguration {

    private final ElasticsearchOperations elasticsearchOperations;

    private final EmergenciaRepository emergenciaRepository;
    private  final EmergenciaSearchRepository emergenciaSearchRepository;

    public IndexadorConfiguration(ElasticsearchOperations elasticsearchOperations, EmergenciaRepository emergenciaRepository, EmergenciaSearchRepository emergenciaSearchRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.emergenciaRepository = emergenciaRepository;
        this.emergenciaSearchRepository = emergenciaSearchRepository;
    }

    @Bean
    public Indexador indexadorEmergencia() {
        return IndexadorSemMapper.<Emergencia, Long>builder()
            .codigo("emergencia")
            .descricao("Emergencia")
            .jpaRepository(emergenciaRepository)
            .elasticsearchClassRepository(emergenciaSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }



}
