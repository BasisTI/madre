package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.CaraterDaInternacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CaraterDaInternacao} entity.
 */
public interface CaraterDaInternacaoSearchRepository extends ElasticsearchRepository<CaraterDaInternacao, Long> {
}
