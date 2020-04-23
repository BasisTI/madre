package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.CRM;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CRM entity.
 */
public interface CRMSearchRepository extends ElasticsearchRepository<CRM, Long> {
}
