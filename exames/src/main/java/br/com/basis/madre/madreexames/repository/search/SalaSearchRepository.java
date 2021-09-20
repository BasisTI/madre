package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Sala;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Sala} entity.
 */
public interface SalaSearchRepository extends ElasticsearchRepository<Sala, Long> {
}
