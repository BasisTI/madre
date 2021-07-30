package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.GrupoFuncional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GrupoFuncional} entity.
 */
public interface GrupoFuncionalSearchRepository extends ElasticsearchRepository<GrupoFuncional, Long> {
}
