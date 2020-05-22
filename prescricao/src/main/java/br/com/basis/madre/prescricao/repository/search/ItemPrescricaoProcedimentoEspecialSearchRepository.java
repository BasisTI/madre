package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoProcedimentoEspecial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemPrescricaoProcedimentoEspecial} entity.
 */
public interface ItemPrescricaoProcedimentoEspecialSearchRepository extends ElasticsearchRepository<ItemPrescricaoProcedimentoEspecial, Long> {
}
