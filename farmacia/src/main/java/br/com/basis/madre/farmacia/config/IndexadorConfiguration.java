package br.com.basis.madre.farmacia.config;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.repository.*;
import br.com.basis.madre.farmacia.repository.search.*;
import br.gov.nuvem.comum.microsservico.service.reindex.Indexador;
import br.gov.nuvem.comum.microsservico.service.reindex.IndexadorSemMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@AllArgsConstructor
@Configuration
public class IndexadorConfiguration {

    private final ElasticsearchOperations elasticsearchOperations;

    private final ApresentacaoRepository apresentacaoRepository;
    private final ApresentacaoSearchRepository apresentacaoSearchRepository;

    private final DispensacaoRepository dispensacaoRepository;
    private final DispensacaoSearchRepository dispensacaoSearchRepository;

    private final DispensacaoMedicamentosRepository dispensacaoMedicamentosRepository;
    private final DispensacaoMedicamentosSearchRepository dispensacaoMedicamentosSearchRepository;

    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoSearchRepository medicamentoSearchRepository;

    private final TipoMedicamentoRepository tipoMedicamentoRepository;
    private final TipoMedicamentoSearchRepository tipoMedicamentoSearchRepository;

    private final UnidadeRepository unidadeRepository;
    private final UnidadeSearchRepository unidadeSearchRepository;

    @Bean
    public Indexador indexadorApresentacao() {
        return IndexadorSemMapper.<Apresentacao, Long>builder()
            .codigo("apresentacao")
            .descricao("Apresentação")
            .jpaRepository(apresentacaoRepository)
            .elasticsearchClassRepository(apresentacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorDispensacao() {
        return IndexadorSemMapper.<Dispensacao, Long>builder()
            .codigo("dispensacao")
            .descricao("Dispensação")
            .jpaRepository(dispensacaoRepository)
            .elasticsearchClassRepository(dispensacaoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorDispensacaoMedicamentos() {
        return IndexadorSemMapper.<DispensacaoMedicamentos, Long>builder()
            .codigo("dispensacaomedicamentos")
            .descricao("Dispensação Medicamentos")
            .jpaRepository(dispensacaoMedicamentosRepository)
            .elasticsearchClassRepository(dispensacaoMedicamentosSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorMedicamento() {
        return IndexadorSemMapper.<Medicamento, Long>builder()
            .codigo("medicamento")
            .descricao("Medicamento")
            .jpaRepository(medicamentoRepository)
            .elasticsearchClassRepository(medicamentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorTipoMedicamento() {
        return IndexadorSemMapper.<TipoMedicamento, Long>builder()
            .codigo("tipomedicamento")
            .descricao("Tipo Medicamento")
            .jpaRepository(tipoMedicamentoRepository)
            .elasticsearchClassRepository(tipoMedicamentoSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

    @Bean
    public Indexador indexadorUnidade() {
        return IndexadorSemMapper.<Unidade, Long>builder()
            .codigo("unidade")
            .descricao("Unidade")
            .jpaRepository(unidadeRepository)
            .elasticsearchClassRepository(unidadeSearchRepository)
            .elasticsearchOperations(elasticsearchOperations)
            .build();
    }

}
