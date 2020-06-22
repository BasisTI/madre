package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Fornecedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Fornecedor} entity.
 */
public interface FornecedorSearchRepository extends ElasticsearchRepository<Fornecedor, Long> {
}
