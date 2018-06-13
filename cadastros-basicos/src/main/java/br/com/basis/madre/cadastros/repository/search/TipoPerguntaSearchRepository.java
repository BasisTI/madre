package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.TipoPergunta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoPergunta entity.
 */
public interface TipoPerguntaSearchRepository extends ElasticsearchRepository<TipoPergunta, Long> {
}
