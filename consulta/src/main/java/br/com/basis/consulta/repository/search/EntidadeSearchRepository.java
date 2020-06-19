package br.com.basis.consulta.repository.search;
import br.com.basis.consulta.domain.Entidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Entidade} entity.
 */
public interface EntidadeSearchRepository extends ElasticsearchRepository<Entidade, Long> {
}
