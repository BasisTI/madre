package br.com.basis.madre.prescricao.repository.search;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicaDTO;

public interface PrescricaoMedicaDTOSearchRepository extends ElasticsearchRepository<PrescricaoMedicaDTO, Long>{
	
	List<PrescricaoMedicaDTO> findByIdPaciente(Long id);
}
