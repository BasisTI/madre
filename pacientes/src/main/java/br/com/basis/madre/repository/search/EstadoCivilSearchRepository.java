package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.EstadoCivil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EstadoCivil} entity.
 */
public interface EstadoCivilSearchRepository extends ElasticsearchRepository<EstadoCivil, Long> {
}
