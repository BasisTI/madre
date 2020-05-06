package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Internacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Internacao} entity.
 */
public interface InternacaoSearchRepository extends ElasticsearchRepository<Internacao, Long> {
}
