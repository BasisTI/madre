package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.EstoqueAlmoxarifado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EstoqueAlmoxarifado} entity.
 */
public interface EstoqueAlmoxarifadoSearchRepository extends ElasticsearchRepository<EstoqueAlmoxarifado, Long> {
}
