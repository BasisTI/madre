package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Sinonimo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Sinonimo} entity.
 */
public interface SinonimoSearchRepository extends ElasticsearchRepository<Sinonimo, Long> {
}
