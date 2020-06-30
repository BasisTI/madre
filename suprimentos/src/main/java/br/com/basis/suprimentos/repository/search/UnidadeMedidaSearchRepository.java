package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.UnidadeMedida;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UnidadeMedida} entity.
 */
public interface UnidadeMedidaSearchRepository extends ElasticsearchRepository<UnidadeMedida, Long> {
}
