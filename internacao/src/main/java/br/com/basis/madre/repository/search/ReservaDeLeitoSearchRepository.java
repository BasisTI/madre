package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.ReservaDeLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReservaDeLeitoSearchRepository extends
    ElasticsearchRepository<ReservaDeLeito, Long> {

}
