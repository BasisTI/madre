package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Ala;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ala} entity.
 */
public interface AlaSearchRepository extends ElasticsearchRepository<Ala, Long> {
}
