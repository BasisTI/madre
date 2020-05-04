package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.UnidadeDose;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UnidadeDose} entity.
 */
public interface UnidadeDoseSearchRepository extends ElasticsearchRepository<UnidadeDose, Long> {
}
