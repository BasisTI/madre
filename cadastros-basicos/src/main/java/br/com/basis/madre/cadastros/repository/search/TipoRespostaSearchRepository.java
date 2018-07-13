package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.TipoResposta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoResposta entity.
 */
public interface TipoRespostaSearchRepository extends ElasticsearchRepository<TipoResposta, Long> {
}
