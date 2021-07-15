package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.SolicitacaoExame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link SolicitacaoExame} entity.
 */
public interface SolicitacaoExameSearchRepository extends ElasticsearchRepository<SolicitacaoExame, Long> {
}
