package br.com.basis.madre.repository.search;
import br.com.basis.madre.domain.BloqueioDeLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link BloqueioDeLeito} entity.
 */
public interface BloqueioDeLeitoSearchRepository extends ElasticsearchRepository<BloqueioDeLeito, Long> {
}
