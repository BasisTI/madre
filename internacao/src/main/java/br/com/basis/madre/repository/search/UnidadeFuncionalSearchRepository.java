package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.UnidadeFuncional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UnidadeFuncionalSearchRepository extends
    ElasticsearchRepository<UnidadeFuncional, Long> {

}
