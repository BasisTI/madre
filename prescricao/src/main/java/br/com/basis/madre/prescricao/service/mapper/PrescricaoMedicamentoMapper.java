package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrescricaoMedicamento} and its DTO {@link PrescricaoMedicamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemPrescricaoMedicamentoMapper.class})
public interface PrescricaoMedicamentoMapper extends EntityMapper<PrescricaoMedicamentoDTO, PrescricaoMedicamento> {


	PrescricaoMedicamentoDTO toDto(PrescricaoMedicamento prescricaoMedicamento);
	
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
