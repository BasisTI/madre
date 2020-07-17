package br.com.basis.consulta.repository.search;
import br.com.basis.consulta.domain.CondicaoDeAtendimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CondicaoDeAtendimento} entity.
 */
public interface CondicaoDeAtendimentoSearchRepository extends ElasticsearchRepository<CondicaoDeAtendimento, Long> {
}
