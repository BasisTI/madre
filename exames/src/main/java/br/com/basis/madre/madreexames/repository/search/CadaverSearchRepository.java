package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Cadaver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Cadaver} entity.
 */
public interface CadaverSearchRepository extends ElasticsearchRepository<Cadaver, Long> {
}
