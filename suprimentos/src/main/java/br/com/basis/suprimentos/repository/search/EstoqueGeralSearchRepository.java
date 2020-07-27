package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.EstoqueGeral;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link EstoqueGeral} entity.
 */
public interface EstoqueGeralSearchRepository extends ElasticsearchRepository<EstoqueGeral, Long> {
}
