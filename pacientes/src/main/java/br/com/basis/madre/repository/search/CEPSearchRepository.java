package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.CEP;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CEP} entity.
 */
public interface CEPSearchRepository extends ElasticsearchRepository<CEP, Long> {
}
