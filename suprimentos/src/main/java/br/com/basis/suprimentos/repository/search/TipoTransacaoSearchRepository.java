package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.TipoTransacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TipoTransacao} entity.
 */
public interface TipoTransacaoSearchRepository extends ElasticsearchRepository<TipoTransacao, Long> {
}
