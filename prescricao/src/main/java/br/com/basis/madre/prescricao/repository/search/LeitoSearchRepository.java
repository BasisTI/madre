package br.com.basis.madre.prescricao.repository.search;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.domain.Leito;

public interface LeitoSearchRepository extends ElasticsearchRepository<Leito, Long>{

}
