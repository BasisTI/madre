package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Ramal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Ramal} entity.
 */
public interface RamalSearchRepository extends ElasticsearchRepository<Ramal, Long> {
}
