package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Dia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Dia} entity.
 */
public interface DiaSearchRepository extends ElasticsearchRepository<Dia, Long> {
}
