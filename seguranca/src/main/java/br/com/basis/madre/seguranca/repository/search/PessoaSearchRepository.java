package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.Pessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Pessoa} entity.
 */
public interface PessoaSearchRepository extends ElasticsearchRepository<Pessoa, Long> {
}
