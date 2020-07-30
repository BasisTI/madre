package br.com.basis.madre.prescricao.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoDTO;

public interface PrescricaoProcedimentoDTOSearchRepository extends ElasticsearchRepository<PrescricaoProcedimentoDTO, Long>{

}
