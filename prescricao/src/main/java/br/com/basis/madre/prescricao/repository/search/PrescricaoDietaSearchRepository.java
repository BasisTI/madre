package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.PrescricaoDieta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrescricaoDieta} entity.
 */
public interface PrescricaoDietaSearchRepository extends ElasticsearchRepository<PrescricaoDieta, Long> {
}
