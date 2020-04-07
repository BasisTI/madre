package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Telefone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Telefone} entity.
 */
public interface TelefoneSearchRepository extends ElasticsearchRepository<Telefone, Long> {
}
