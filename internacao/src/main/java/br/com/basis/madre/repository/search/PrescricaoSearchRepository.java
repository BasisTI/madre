package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Prescricao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Prescricao} entity.
 */
public interface PrescricaoSearchRepository extends ElasticsearchRepository<Prescricao, Long> {
}
