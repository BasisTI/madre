package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Municipio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Municipio} entity.
 */
public interface MunicipioSearchRepository extends ElasticsearchRepository<Municipio, Long> {
}
