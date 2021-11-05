package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.HorarioExame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link HorarioExame} entity.
 */
public interface HorarioExameSearchRepository extends ElasticsearchRepository<HorarioExame, Long> {
}
