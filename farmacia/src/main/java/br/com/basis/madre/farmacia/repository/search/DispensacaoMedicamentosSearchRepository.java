package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.DispensacaoMedicamentos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link DispensacaoMedicamentos} entity.
 */

public interface DispensacaoMedicamentosSearchRepository extends ElasticsearchRepository<DispensacaoMedicamentos, Long> {
}
