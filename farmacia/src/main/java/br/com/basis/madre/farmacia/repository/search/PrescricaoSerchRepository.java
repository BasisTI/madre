package br.com.basis.madre.farmacia.repository.search;

import br.com.basis.madre.farmacia.domain.Prescricao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


public interface PrescricaoSerchRepository extends ElasticsearchRepository <Prescricao, Long>{


}
