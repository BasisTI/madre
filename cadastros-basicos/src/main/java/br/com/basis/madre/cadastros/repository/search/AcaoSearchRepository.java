package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Acao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Acao entity.
 */
public interface AcaoSearchRepository extends ElasticsearchRepository<Acao, Long> {
}
