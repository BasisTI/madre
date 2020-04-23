package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Raca;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Raca} entity.
 */
public interface RacaSearchRepository extends ElasticsearchRepository<Raca, Long> {
}
