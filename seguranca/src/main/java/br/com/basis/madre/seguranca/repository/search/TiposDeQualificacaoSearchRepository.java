package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.TiposDeQualificacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TiposDeQualificacao} entity.
 */
public interface TiposDeQualificacaoSearchRepository extends ElasticsearchRepository<TiposDeQualificacao, Long> {
}
