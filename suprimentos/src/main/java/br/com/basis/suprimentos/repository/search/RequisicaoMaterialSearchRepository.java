package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.RequisicaoMaterial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link RequisicaoMaterial} entity.
 */
public interface RequisicaoMaterialSearchRepository extends ElasticsearchRepository<RequisicaoMaterial, Long> {
}
