package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.SolicitacaoDeInternacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SolicitacaoDeInternacao} entity.
 */
public interface SolicitacaoDeInternacaoSearchRepository extends ElasticsearchRepository<SolicitacaoDeInternacao, Long> {
}
