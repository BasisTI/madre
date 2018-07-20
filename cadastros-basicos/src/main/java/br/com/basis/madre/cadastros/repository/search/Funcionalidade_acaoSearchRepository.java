package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Funcionalidade_acao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Funcionalidade_acao entity.
 */
public interface Funcionalidade_acaoSearchRepository extends ElasticsearchRepository<Funcionalidade_acao, Long> {
}
