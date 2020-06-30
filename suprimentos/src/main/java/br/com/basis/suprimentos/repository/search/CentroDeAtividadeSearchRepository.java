package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.CentroDeAtividade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CentroDeAtividade} entity.
 */
public interface CentroDeAtividadeSearchRepository extends ElasticsearchRepository<CentroDeAtividade, Long> {
}
