package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.TipoDaReservaDeLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface TipoDaReservaDeLeitoSearchRepository extends
    ElasticsearchRepository<TipoDaReservaDeLeito, Long> {

}
