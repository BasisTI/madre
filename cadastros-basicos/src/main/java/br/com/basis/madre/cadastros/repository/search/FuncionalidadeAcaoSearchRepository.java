package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.FuncionalidadeAcao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FuncionalidadeAcao entity.
 */
public interface FuncionalidadeAcaoSearchRepository extends ElasticsearchRepository<FuncionalidadeAcao, Long> {
}
