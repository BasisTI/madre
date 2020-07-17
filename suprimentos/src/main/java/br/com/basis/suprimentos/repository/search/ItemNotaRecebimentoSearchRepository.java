package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.ItemNotaRecebimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemNotaRecebimento} entity.
 */
public interface ItemNotaRecebimentoSearchRepository extends ElasticsearchRepository<ItemNotaRecebimento, Long> {
}
