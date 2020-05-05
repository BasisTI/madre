package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemPrescricaoMedicamento} entity.
 */
public interface ItemPrescricaoMedicamentoSearchRepository extends ElasticsearchRepository<ItemPrescricaoMedicamento, Long> {
}
