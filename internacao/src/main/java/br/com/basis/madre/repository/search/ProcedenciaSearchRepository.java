package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Procedencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Procedencia} entity.
 */
public interface ProcedenciaSearchRepository extends ElasticsearchRepository<Procedencia, Long> {
}
