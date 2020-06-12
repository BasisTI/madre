package br.com.basis.madre.farmacia.config;

import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.repository.MedicamentoRepository;
import br.com.basis.madre.farmacia.repository.search.MedicamentoSearchRepository;
import br.com.basis.madre.farmacia.service.reindex.Indexador;
import br.com.basis.madre.farmacia.service.reindex.IndexadorSemMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;


@RequiredArgsConstructor
@Configuration
public class IndexacaoConfiguration {
    private final ElasticsearchOperations elasticsearchTemplate;
    private final MedicamentoRepository medicamentoRepository;
    private final MedicamentoSearchRepository medicamentoSearchRepository;

    @Bean
    public Indexador indexadorMedicamento() {
        IndexadorSemMapper<Medicamento, Long> indexador = new IndexadorSemMapper<>(medicamentoRepository,
            medicamentoSearchRepository, elasticsearchTemplate);
        indexador.setCodigo("medicamento");
        indexador.setDescricao("Medicamento");
        return indexador;
    }


}
