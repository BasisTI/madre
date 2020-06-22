package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.AutorizacaoFornecimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AutorizacaoFornecimento} entity.
 */
public interface AutorizacaoFornecimentoSearchRepository extends ElasticsearchRepository<AutorizacaoFornecimento, Long> {
}
