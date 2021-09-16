package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Recipiente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Recipiente} entity.
 */
public interface RecipienteSearchRepository extends ElasticsearchRepository<Recipiente, Long> {
}
