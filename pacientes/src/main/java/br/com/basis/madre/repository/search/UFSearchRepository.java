package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.UF;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UF} entity.
 */
public interface UFSearchRepository extends ElasticsearchRepository<UF, Long> {
}
