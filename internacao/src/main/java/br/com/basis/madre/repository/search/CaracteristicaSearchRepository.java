package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Caracteristica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Caracteristica} entity.
 */
public interface CaracteristicaSearchRepository extends ElasticsearchRepository<Caracteristica, Long> {
}
