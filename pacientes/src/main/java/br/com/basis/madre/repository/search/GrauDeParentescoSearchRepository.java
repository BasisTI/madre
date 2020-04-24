package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.GrauDeParentesco;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GrauDeParentesco} entity.
 */
public interface GrauDeParentescoSearchRepository extends ElasticsearchRepository<GrauDeParentesco, Long> {
}
