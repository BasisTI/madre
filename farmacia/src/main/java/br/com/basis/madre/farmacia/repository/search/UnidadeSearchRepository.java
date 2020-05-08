package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Unidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link Unidade} entity.
 */

public interface UnidadeSearchRepository extends ElasticsearchRepository<Unidade, Long> {
}
