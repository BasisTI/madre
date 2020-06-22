package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Recebimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Recebimento} entity.
 */
public interface RecebimentoSearchRepository extends ElasticsearchRepository<Recebimento, Long> {
}
