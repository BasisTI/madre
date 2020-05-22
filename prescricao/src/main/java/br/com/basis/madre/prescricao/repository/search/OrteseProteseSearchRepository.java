package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.OrteseProtese;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrteseProtese} entity.
 */
public interface OrteseProteseSearchRepository extends ElasticsearchRepository<OrteseProtese, Long> {
}
