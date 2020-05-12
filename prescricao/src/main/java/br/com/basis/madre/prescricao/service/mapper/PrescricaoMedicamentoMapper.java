package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicamentoDTO;

/**
 * Mapper for the entity {@link PrescricaoMedicamento} and its DTO {@link PrescricaoMedicamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemPrescricaoMedicamentoMapper.class})
public interface PrescricaoMedicamentoMapper extends EntityMapper<PrescricaoMedicamentoDTO, PrescricaoMedicamento> {


	
    PrescricaoMedicamento toEntity(PrescricaoMedicamentoDTO prescricaoMedicamentoDTO);

    default PrescricaoMedicamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrescricaoMedicamento prescricaoMedicamento = new PrescricaoMedicamento();
        prescricaoMedicamento.setId(id);
        return prescricaoMedicamento;
    }
    
}
