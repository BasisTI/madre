package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.PlanoDeSaude;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PlanoDeSaude} entity.
 */
public interface PlanoDeSaudeSearchRepository extends ElasticsearchRepository<PlanoDeSaude, Long> {
}
