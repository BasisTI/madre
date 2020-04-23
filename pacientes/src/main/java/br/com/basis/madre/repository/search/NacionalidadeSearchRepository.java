package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Nacionalidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Nacionalidade} entity.
 */
public interface NacionalidadeSearchRepository extends ElasticsearchRepository<Nacionalidade, Long> {
}
