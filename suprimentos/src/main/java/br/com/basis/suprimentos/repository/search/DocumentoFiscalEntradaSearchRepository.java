package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.DocumentoFiscalEntrada;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DocumentoFiscalEntrada} entity.
 */
public interface DocumentoFiscalEntradaSearchRepository extends ElasticsearchRepository<DocumentoFiscalEntrada, Long> {
}
