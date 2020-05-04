package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.Diluente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Diluente} entity.
 */
public interface DiluenteSearchRepository extends ElasticsearchRepository<Diluente, Long> {
}
