package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Documento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Documento} entity.
 */
public interface DocumentoSearchRepository extends ElasticsearchRepository<Documento, Long> {
}
