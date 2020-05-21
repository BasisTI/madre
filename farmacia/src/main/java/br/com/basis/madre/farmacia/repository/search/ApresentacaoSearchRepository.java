package br.com.basis.madre.farmacia.repository.search;
import br.com.basis.madre.farmacia.domain.Apresentacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the {@link Apresentacao} entity.
 */

public interface ApresentacaoSearchRepository extends ElasticsearchRepository<Apresentacao, Long> {
}
