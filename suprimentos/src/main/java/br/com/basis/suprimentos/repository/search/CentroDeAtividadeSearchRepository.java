package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.CentroDeAtividade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CentroDeAtividadeSearchRepository extends ElasticsearchRepository<CentroDeAtividade, Long> {
}
