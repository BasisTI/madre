package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.Clinica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Clinica} entity.
 */
public interface ClinicaSearchRepository extends ElasticsearchRepository<Clinica, Long> {
}
