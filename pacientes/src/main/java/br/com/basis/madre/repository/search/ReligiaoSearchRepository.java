package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Religiao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Religiao} entity.
 */
public interface ReligiaoSearchRepository extends ElasticsearchRepository<Religiao, Long> {
}
