package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Servidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Servidor} entity.
 */
public interface ServidorSearchRepository extends ElasticsearchRepository<Servidor, Long> {
}
