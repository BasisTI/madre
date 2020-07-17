package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.OrigemParecerTecnico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrigemParecerTecnico} entity.
 */
public interface OrigemParecerTecnicoSearchRepository extends ElasticsearchRepository<OrigemParecerTecnico, Long> {
}
