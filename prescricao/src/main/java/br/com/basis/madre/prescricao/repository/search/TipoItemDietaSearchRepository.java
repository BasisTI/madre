package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.TipoItemDieta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoItemDieta} entity.
 */
public interface TipoItemDietaSearchRepository extends ElasticsearchRepository<TipoItemDieta, Long> {
}
