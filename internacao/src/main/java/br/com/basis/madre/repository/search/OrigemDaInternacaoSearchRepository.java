package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.OrigemDaInternacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrigemDaInternacao} entity.
 */
public interface OrigemDaInternacaoSearchRepository extends ElasticsearchRepository<OrigemDaInternacao, Long> {
}
