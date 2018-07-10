package br.com.basis.madre.pacientes.repository.search;

import br.com.basis.madre.pacientes.domain.Triagem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Triagem entity.
 */
public interface TriagemSearchRepository extends ElasticsearchRepository<Triagem, Long> {
}
