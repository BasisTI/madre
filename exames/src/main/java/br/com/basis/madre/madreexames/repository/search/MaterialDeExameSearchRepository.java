package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.MaterialDeExame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link MaterialDeExame} entity.
 */
public interface MaterialDeExameSearchRepository extends ElasticsearchRepository<MaterialDeExame, Long> {
}
