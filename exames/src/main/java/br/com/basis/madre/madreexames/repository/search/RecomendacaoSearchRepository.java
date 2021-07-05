package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.Recomendacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Recomendacao} entity.
 */
public interface RecomendacaoSearchRepository extends ElasticsearchRepository<Recomendacao, Long> {
}
