package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Documentos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Documentos} entity.
 */
public interface DocumentosSearchRepository extends ElasticsearchRepository<Documentos, Long> {
}
