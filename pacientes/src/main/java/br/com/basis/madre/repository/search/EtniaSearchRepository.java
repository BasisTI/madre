package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Etnia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Etnia} entity.
 */
public interface EtniaSearchRepository extends ElasticsearchRepository<Etnia, Long> {
}
