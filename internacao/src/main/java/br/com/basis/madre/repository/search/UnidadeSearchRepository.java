package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Unidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Unidade} entity.
 */
public interface UnidadeSearchRepository extends ElasticsearchRepository<Unidade, Long> {
}
