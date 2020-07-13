package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDiagnosticoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrescricaoDiagnostico} and its DTO {@link PrescricaoDiagnosticoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrescricaoDiagnosticoMapper extends EntityMapper<PrescricaoDiagnosticoDTO, PrescricaoDiagnostico> {


    @Mapping(target = "itemPrescricaoDiagnosticos", ignore = true)
    @Mapping(target = "removeItemPrescricaoDiagnostico", ignore = true)
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
