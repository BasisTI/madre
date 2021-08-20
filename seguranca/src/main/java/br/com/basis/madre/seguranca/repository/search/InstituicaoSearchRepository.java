package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Instituicao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Instituicao} entity.
 */
public interface InstituicaoSearchRepository extends ElasticsearchRepository<Instituicao, Long> {
}
