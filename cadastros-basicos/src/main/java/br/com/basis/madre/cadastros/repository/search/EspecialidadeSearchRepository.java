package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Especialidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Especialidade entity.
 */
public interface EspecialidadeSearchRepository extends ElasticsearchRepository<Especialidade, Long> {
}
