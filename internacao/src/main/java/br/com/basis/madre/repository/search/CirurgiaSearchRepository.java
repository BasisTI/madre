package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Cirurgia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cirurgia} entity.
 */
public interface CirurgiaSearchRepository extends ElasticsearchRepository<Cirurgia, Long> {
}
