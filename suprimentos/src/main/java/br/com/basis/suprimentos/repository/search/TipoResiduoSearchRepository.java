package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.TipoResiduo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoResiduo} entity.
 */
public interface TipoResiduoSearchRepository extends ElasticsearchRepository<TipoResiduo, Long> {
}
