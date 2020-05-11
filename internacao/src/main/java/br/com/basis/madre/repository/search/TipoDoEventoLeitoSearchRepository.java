package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.TipoDoEventoLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface TipoDoEventoLeitoSearchRepository extends
    ElasticsearchRepository<TipoDoEventoLeito, Long> {

}
