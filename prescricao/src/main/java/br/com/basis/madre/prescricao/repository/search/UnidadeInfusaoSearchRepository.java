package br.com.basis.madre.prescricao.repository.search;
import br.com.basis.madre.prescricao.domain.UnidadeInfusao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UnidadeInfusao} entity.
 */
public interface UnidadeInfusaoSearchRepository extends ElasticsearchRepository<UnidadeInfusao, Long> {
}
