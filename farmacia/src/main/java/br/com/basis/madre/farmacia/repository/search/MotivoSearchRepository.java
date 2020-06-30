package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Motivo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Motivo} entity.
 */
public interface MotivoSearchRepository extends ElasticsearchRepository<Motivo, Long> {
}
