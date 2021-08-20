package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Cargo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Cargo} entity.
 */
public interface CargoSearchRepository extends ElasticsearchRepository<Cargo, Long> {
}
