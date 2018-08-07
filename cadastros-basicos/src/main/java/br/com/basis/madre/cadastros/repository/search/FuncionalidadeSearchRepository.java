package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Funcionalidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Funcionalidade entity.
 */
public interface FuncionalidadeSearchRepository extends ElasticsearchRepository<Funcionalidade, Long> {
}
