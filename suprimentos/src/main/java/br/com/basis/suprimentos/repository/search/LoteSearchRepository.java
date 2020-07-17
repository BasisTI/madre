package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Lote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Lote} entity.
 */
public interface LoteSearchRepository extends ElasticsearchRepository<Lote, Long> {
}
