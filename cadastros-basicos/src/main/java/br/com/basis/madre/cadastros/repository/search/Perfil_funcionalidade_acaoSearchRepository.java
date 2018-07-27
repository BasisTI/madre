package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Perfil_funcionalidade_acao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Perfil_funcionalidade_acao entity.
 */
public interface Perfil_funcionalidade_acaoSearchRepository extends ElasticsearchRepository<Perfil_funcionalidade_acao, Long> {
}
