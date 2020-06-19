package br.com.basis.consulta.repository.search;
import br.com.basis.consulta.domain.FormaDeAgendamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FormaDeAgendamento} entity.
 */
public interface FormaDeAgendamentoSearchRepository extends ElasticsearchRepository<FormaDeAgendamento, Long> {
}
