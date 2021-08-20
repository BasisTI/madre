package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Vinculo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Vinculo} entity.
 */
public interface VinculoSearchRepository extends ElasticsearchRepository<Vinculo, Long> {
}
