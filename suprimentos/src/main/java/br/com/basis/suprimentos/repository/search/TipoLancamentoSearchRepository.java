package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.TipoLancamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TipoLancamento} entity.
 */
public interface TipoLancamentoSearchRepository extends ElasticsearchRepository<TipoLancamento, Long> {
}
