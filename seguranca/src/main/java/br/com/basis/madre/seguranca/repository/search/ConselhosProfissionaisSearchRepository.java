package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.ConselhosProfissionais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ConselhosProfissionais} entity.
 */
public interface ConselhosProfissionaisSearchRepository extends ElasticsearchRepository<ConselhosProfissionais, Long> {
}
