package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Dispensacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link Dispensacao} entity.
 */

public interface DispensacaoSearchRepository extends ElasticsearchRepository<Dispensacao, Long> {
}
