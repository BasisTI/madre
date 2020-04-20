package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Procedimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Procedimento entity.
 */
public interface ProcedimentoSearchRepository extends ElasticsearchRepository<Procedimento, Long> {
}
