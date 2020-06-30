package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.prescricao.domain.Medicamento;
import br.com.basis.madre.prescricao.service.dto.MedicamentoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface MedicamentoMapper extends EntityMapper<MedicamentoDTO, Medicamento> {

	Medicamento toEntity(MedicamentoDTO medicamentoDTO);

    default Medicamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medicamento medicamento = new Medicamento();
        medicamento.setId(id);
        return medicamento;
    }
}
