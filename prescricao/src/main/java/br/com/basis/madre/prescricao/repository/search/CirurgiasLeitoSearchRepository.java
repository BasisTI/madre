package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.CirurgiasLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CirurgiasLeito} entity.
 */
public interface CirurgiasLeitoSearchRepository extends ElasticsearchRepository<CirurgiasLeito, Long> {
}
