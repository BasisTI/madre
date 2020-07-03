package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.InformacaoTransferencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link InformacaoTransferencia} entity.
 */
public interface InformacaoTransferenciaSearchRepository extends ElasticsearchRepository<InformacaoTransferencia, Long> {
}
