package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.FuncionalidadeAcao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FuncionalidadeAcao entity.
 */
public interface Funcionalidade_acaoSearchRepository extends ElasticsearchRepository<FuncionalidadeAcao, Long> {
}
