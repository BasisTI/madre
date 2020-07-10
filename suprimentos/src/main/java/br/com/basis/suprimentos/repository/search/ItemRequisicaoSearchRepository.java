package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.ItemRequisicao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ItemRequisicao} entity.
 */
public interface ItemRequisicaoSearchRepository extends ElasticsearchRepository<ItemRequisicao, Long> {
}
