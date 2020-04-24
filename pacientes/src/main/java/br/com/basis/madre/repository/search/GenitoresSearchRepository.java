package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Genitores;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Genitores} entity.
 */
public interface GenitoresSearchRepository extends ElasticsearchRepository<Genitores, Long> {
}
