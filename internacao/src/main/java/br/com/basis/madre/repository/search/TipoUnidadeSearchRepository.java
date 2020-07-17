package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.TipoUnidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoUnidade} entity.
 */
public interface TipoUnidadeSearchRepository extends ElasticsearchRepository<TipoUnidade, Long> {
}
