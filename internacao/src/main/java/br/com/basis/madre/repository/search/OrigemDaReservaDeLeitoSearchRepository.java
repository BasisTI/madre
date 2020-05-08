package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.OrigemDaReservaDeLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrigemDaReservaDeLeitoSearchRepository extends
    ElasticsearchRepository<OrigemDaReservaDeLeito, Long> {

}
