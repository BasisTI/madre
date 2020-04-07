package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Justificativa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Justificativa} entity.
 */
public interface JustificativaSearchRepository extends ElasticsearchRepository<Justificativa, Long> {
}
