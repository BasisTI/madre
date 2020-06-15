package br.com.basis.suprimentos.repository.search;

import br.com.basis.suprimentos.domain.Composicao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ComposicaoSearchRepository extends ElasticsearchRepository<Composicao, Long> {
}
