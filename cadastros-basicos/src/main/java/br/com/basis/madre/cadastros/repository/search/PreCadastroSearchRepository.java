package br.com.basis.madre.cadastros.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.cadastros.domain.PreCadastro;

/**
 * Spring Data Elasticsearch repository for the PreCadastro entity.
 */
public interface PreCadastroSearchRepository extends ElasticsearchRepository<PreCadastro, Long> {
}
