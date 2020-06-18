package br.com.basis.suprimentos.repository.search;
import br.com.basis.suprimentos.domain.GrupoMaterial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link GrupoMaterial} entity.
 */
public interface GrupoMaterialSearchRepository extends ElasticsearchRepository<GrupoMaterial, Long> {
}
