package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Ocupacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ocupacao} entity.
 */
public interface OcupacaoSearchRepository extends ElasticsearchRepository<Ocupacao, Long> {
}
