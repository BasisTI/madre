package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.PrescricaoDiagnostico;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDiagnosticoDTO;

/**
 * Mapper for the entity {@link PrescricaoDiagnostico} and its DTO {@link PrescricaoDiagnosticoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemPrescricaoDiagnosticoMapper.class})
public interface PrescricaoDiagnosticoMapper extends EntityMapper<PrescricaoDiagnosticoDTO, PrescricaoDiagnostico> {


	@Mapping(source = "itemPrescricaoDiagnosticos", target = "itens")
	PrescricaoDiagnosticoDTO toDto(PrescricaoDiagnostico prescricaoDiagnostico);
	
	@Mapping(source = "itens", target = "itemPrescricaoDiagnosticos")
	PrescricaoDiagnostico toEntity(PrescricaoDiagnosticoDTO prescricaoDiagnosticoDTO);

    default PrescricaoDiagnostico fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrescricaoDiagnostico prescricaoDiagnostico = new PrescricaoDiagnostico();
        prescricaoDiagnostico.setId(id);
        return prescricaoDiagnostico;
    }
}
