package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.ClassificacaoMaterial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ClassificacaoMaterial} entity.
 */
public interface ClassificacaoMaterialSearchRepository extends ElasticsearchRepository<ClassificacaoMaterial, Long> {
}
