package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.Material;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Material} entity.
 */
public interface MaterialSearchRepository extends ElasticsearchRepository<Material, Long> {
}
