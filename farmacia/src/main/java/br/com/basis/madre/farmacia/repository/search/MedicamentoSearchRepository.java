package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Medicamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link Medicamento} entity.
 */

public interface MedicamentoSearchRepository extends ElasticsearchRepository<Medicamento, Long> {
}
