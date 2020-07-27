package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Transacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Transacao} entity.
 */
public interface TransacaoSearchRepository extends ElasticsearchRepository<Transacao, Long> {
}
