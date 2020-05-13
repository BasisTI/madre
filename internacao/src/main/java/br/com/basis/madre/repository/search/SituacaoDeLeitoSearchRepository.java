package br.com.basis.madre.repository.search;

import br.com.basis.madre.domain.SituacaoDeLeito;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SituacaoDeLeitoSearchRepository extends
    ElasticsearchRepository<SituacaoDeLeito, Long> {

}
