package br.com.basis.madre.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Naturalidade} entity.
 */
public interface NaturalidadeSearchRepository extends ElasticsearchRepository<Naturalidade, Long> {
}
