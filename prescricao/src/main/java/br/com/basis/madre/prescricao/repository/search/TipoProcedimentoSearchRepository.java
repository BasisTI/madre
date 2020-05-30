package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.TipoProcedimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoProcedimento} entity.
 */
public interface TipoProcedimentoSearchRepository extends ElasticsearchRepository<TipoProcedimento, Long> {
}
