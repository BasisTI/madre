package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Lancamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Lancamento} entity.
 */
public interface LancamentoSearchRepository extends ElasticsearchRepository<Lancamento, Long> {
}
