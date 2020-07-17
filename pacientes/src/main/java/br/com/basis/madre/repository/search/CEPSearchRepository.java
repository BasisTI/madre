package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.EnderecoCEP;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EnderecoCEP} entity.
 */
public interface CEPSearchRepository extends ElasticsearchRepository<EnderecoCEP, Long> {
}
