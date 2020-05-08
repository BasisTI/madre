package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MotivoDoBloqueioDeLeito} entity.
 */
public interface MotivoDoBloqueioDeLeitoSearchRepository extends ElasticsearchRepository<MotivoDoBloqueioDeLeito, Long> {
}
