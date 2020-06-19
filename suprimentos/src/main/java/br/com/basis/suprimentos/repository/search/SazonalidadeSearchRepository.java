package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.Sazonalidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Sazonalidade} entity.
 */
public interface SazonalidadeSearchRepository extends ElasticsearchRepository<Sazonalidade, Long> {
}
