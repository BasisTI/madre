package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.MotivoDoCadastro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MotivoDoCadastro} entity.
 */
public interface MotivoDoCadastroSearchRepository extends ElasticsearchRepository<MotivoDoCadastro, Long> {
}
