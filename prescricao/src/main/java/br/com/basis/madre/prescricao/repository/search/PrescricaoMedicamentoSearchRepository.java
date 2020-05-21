package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrescricaoMedicamento} entity.
 */
public interface PrescricaoMedicamentoSearchRepository extends ElasticsearchRepository<PrescricaoMedicamento, Long> {
}
