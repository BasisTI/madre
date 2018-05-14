package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.PreCadastro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PreCadastro entity.
 */
public interface PreCadastroSearchRepository extends ElasticsearchRepository<PreCadastro, Long> {
}
