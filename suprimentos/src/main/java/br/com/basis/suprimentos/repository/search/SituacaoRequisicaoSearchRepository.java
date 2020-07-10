package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.SituacaoRequisicao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SituacaoRequisicao} entity.
 */
public interface SituacaoRequisicaoSearchRepository extends ElasticsearchRepository<SituacaoRequisicao, Long> {
}
