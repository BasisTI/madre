package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Exame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Exame} entity.
 */
public interface ExameSearchRepository extends ElasticsearchRepository<Exame, Long> {
}
