package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Responsavel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Responsavel} entity.
 */
public interface ResponsavelSearchRepository extends ElasticsearchRepository<Responsavel, Long> {
}
