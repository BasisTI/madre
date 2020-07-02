package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.ItemTransferencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ItemTransferencia} entity.
 */
public interface ItemTransferenciaSearchRepository extends ElasticsearchRepository<ItemTransferencia, Long> {
}
