package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.TipoMedicamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoMedicamento} entity.
 */

public interface TipoMedicamentoSearchRepository extends ElasticsearchRepository<TipoMedicamento, Long> {
}
