package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Hospital;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Hospital} entity.
 */
public interface HospitalSearchRepository extends ElasticsearchRepository<Hospital, Long> {
}
