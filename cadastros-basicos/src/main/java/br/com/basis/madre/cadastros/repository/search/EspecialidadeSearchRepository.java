package br.com.basis.madre.cadastros.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.cadastros.domain.Especialidade;

/**
 * Spring Data Elasticsearch repository for the Especialidade entity.
 */
public interface EspecialidadeSearchRepository extends ElasticsearchRepository<Especialidade, Long> {
}
