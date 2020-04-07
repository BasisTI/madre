package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.CartaoSUS;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CartaoSUS} entity.
 */
public interface CartaoSUSSearchRepository extends ElasticsearchRepository<CartaoSUS, Long> {
}
