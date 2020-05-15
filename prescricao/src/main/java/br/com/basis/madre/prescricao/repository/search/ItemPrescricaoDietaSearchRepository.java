package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemPrescricaoDieta} entity.
 */
public interface ItemPrescricaoDietaSearchRepository extends ElasticsearchRepository<ItemPrescricaoDieta, Long> {
}
