package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.LocalDeAtendimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LocalDeAtendimento} entity.
 */
public interface LocalDeAtendimentoSearchRepository extends ElasticsearchRepository<LocalDeAtendimento, Long> {
}
