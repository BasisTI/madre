package br.com.basis.consulta.repository.search;
import br.com.basis.consulta.domain.Emergencia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Emergencia} entity.
 */
public interface EmergenciaSearchRepository extends ElasticsearchRepository<Emergencia, Long> {
}
