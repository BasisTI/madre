package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Estorno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Estorno} entity.
 */
public interface EstornoSearchRepository extends ElasticsearchRepository<Estorno, Long> {
}
