package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.ConvenioDeSaude;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ConvenioDeSaude} entity.
 */
public interface ConvenioDeSaudeSearchRepository extends ElasticsearchRepository<ConvenioDeSaude, Long> {
}
