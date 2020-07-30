package br.com.basis.madre.prescricao.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicaDTO;

public interface PrescricaoMedicaDTOSearchRepository extends ElasticsearchRepository<PrescricaoMedicaDTO, Long>{
	
	Page<PrescricaoMedicaDTO> findByIdPaciente(Long id, Pageable pageable);
}
