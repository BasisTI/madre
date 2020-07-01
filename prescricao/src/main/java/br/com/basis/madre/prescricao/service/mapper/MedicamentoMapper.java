package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.Medicamento;
import br.com.basis.madre.prescricao.service.dto.MedicamentoDTO;

@Mapper(componentModel = "spring", uses = {})
public interface MedicamentoMapper extends EntityMapper<MedicamentoDTO, Medicamento> {

	@Mapping(source="id", target="id")
	@Mapping(source="nome", target="nome")
	Medicamento toEntity(MedicamentoDTO medicamentoDTO);
	
	@Mapping(source="id", target="id")
	@Mapping(source="nome", target="nome")
	MedicamentoDTO toDto(Medicamento medicamento);

    default Medicamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medicamento medicamento = new Medicamento();
        medicamento.setId(id);
        return medicamento;
    }
}
