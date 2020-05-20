package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.TipoMedicamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoMedicamento} entity.
 */
public interface TipoMedicamentoSearchRepository extends ElasticsearchRepository<TipoMedicamento, Long> {
}
