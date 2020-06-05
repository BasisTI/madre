package br.com.basis.madre.farmacia.config;

import br.com.basis.madre.farmacia.domain.Medicamento;
import br.com.basis.madre.farmacia.repository.MedicamentoRepository;
import br.com.basis.madre.farmacia.repository.search.MedicamentoSearchRepository;
import br.com.basis.madre.farmacia.service.reindex.Indexador;
import br.com.basis.madre.farmacia.service.reindex.IndexadorSemMapper;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.mapper.DefaultJestResultsMapper;
import io.searchbox.client.JestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;


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
