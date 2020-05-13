package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.EventoLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventoLeitoSearchRepository extends ElasticsearchRepository<EventoLeito, Long> {

}
