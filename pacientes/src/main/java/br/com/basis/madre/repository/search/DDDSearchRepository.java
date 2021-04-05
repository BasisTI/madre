package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.DDD;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DDDSearchRepository extends ElasticsearchRepository<DDD, Long> {
}
