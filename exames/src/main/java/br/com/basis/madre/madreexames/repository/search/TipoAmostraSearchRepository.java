package br.com.basis.madre.madreexames.repository.search;

import br.com.basis.madre.madreexames.domain.TipoAmostra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TipoAmostra} entity.
 */
public interface TipoAmostraSearchRepository extends ElasticsearchRepository<TipoAmostra, Long> {
}
