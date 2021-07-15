package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.ItemSolicitacaoExame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ItemSolicitacaoExame} entity.
 */
public interface ItemSolicitacaoExameSearchRepository extends ElasticsearchRepository<ItemSolicitacaoExame, Long> {
}
