package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Paciente} entity.
 */
public interface PacienteSearchRepository extends ElasticsearchRepository<Paciente, Long> {
//          Page<Paciente> search(SearchQuery searchQuery, Pageable pageable);
}
