package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.AmostraDeMaterial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AmostraDeMaterial} entity.
 */
public interface AmostraDeMaterialSearchRepository extends ElasticsearchRepository<AmostraDeMaterial, Long> {
}
