package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.SolicitacaoCompras;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SolicitacaoCompras} entity.
 */
public interface SolicitacaoComprasSearchRepository extends ElasticsearchRepository<SolicitacaoCompras, Long> {
}
