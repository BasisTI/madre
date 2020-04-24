package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Municipio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Municipio} entity.
 */
public interface MunicipioSearchRepository extends ElasticsearchRepository<Municipio, Long> {
}
