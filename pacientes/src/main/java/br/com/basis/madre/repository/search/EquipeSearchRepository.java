package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Equipe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Equipe entity.
 */
public interface EquipeSearchRepository extends ElasticsearchRepository<Equipe, Long> {
}
