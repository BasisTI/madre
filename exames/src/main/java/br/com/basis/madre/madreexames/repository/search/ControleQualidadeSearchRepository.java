package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.ControleQualidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ControleQualidade} entity.
 */
public interface ControleQualidadeSearchRepository extends ElasticsearchRepository<ControleQualidade, Long> {
}
