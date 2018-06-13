package br.com.basis.madre.cadastros.repository.search;

import br.com.basis.madre.cadastros.domain.Anexo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Anexo entity.
 */
public interface AnexoSearchRepository extends ElasticsearchRepository<Anexo, Long> {
}
