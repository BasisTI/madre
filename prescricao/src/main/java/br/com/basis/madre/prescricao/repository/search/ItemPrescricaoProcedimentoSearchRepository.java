package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemPrescricaoProcedimento} entity.
 */
public interface ItemPrescricaoProcedimentoSearchRepository extends ElasticsearchRepository<ItemPrescricaoProcedimento, Long> {
}
