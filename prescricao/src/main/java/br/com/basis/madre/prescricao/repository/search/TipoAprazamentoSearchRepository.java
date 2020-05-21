package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.TipoAprazamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoAprazamento} entity.
 */
public interface TipoAprazamentoSearchRepository extends ElasticsearchRepository<TipoAprazamento, Long> {
}
