package br.com.basis.madre.seguranca.repository.search;

import br.com.basis.madre.seguranca.domain.OcupacaoDeCargo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link OcupacaoDeCargo} entity.
 */
public interface OcupacaoDeCargoSearchRepository extends ElasticsearchRepository<OcupacaoDeCargo, Long> {
}
