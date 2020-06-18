package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.Composicao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Composicao} entity.
 */
public interface ComposicaoSearchRepository extends ElasticsearchRepository<Composicao, Long> {
}
