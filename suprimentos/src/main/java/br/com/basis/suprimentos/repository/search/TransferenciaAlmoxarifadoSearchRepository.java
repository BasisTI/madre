package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TransferenciaAlmoxarifadoSearchRepository extends ElasticsearchRepository<TransferenciaAlmoxarifado, Long> {
}
