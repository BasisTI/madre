package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Especialidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Especialidade} entity.
 */
public interface EspecialidadeSearchRepository extends ElasticsearchRepository<Especialidade, Long> {
}
