package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemPrescricaoDiagnostico} entity.
 */
public interface ItemPrescricaoDiagnosticoSearchRepository extends ElasticsearchRepository<ItemPrescricaoDiagnostico, Long> {
}
