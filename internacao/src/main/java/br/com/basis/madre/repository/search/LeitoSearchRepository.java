package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.Leito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LeitoSearchRepository extends ElasticsearchRepository<Leito, Long> {

}
