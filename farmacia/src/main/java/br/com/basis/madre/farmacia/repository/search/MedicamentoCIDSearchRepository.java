package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.MedicamentoCID;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MedicamentoCID} entity.
 */
public interface MedicamentoCIDSearchRepository extends ElasticsearchRepository<MedicamentoCID, Long> {
}
