package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.TipoUnidadeDieta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoUnidadeDieta} entity.
 */
public interface TipoUnidadeDietaSearchRepository extends ElasticsearchRepository<TipoUnidadeDieta, Long> {
}
