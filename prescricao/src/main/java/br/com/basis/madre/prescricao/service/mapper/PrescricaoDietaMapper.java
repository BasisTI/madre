package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDietaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrescricaoDieta} and its DTO {@link PrescricaoDietaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrescricaoDietaMapper extends EntityMapper<PrescricaoDietaDTO, PrescricaoDieta> {


    @Mapping(target = "itemPrescricaoDietas", ignore = true)
    @Mapping(target = "removeItemPrescricaoDieta", ignore = true)
    PrescricaoDieta toEntity(PrescricaoDietaDTO prescricaoDietaDTO);

    default PrescricaoDieta fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrescricaoDieta prescricaoDieta = new PrescricaoDieta();
        prescricaoDieta.setId(id);
        return prescricaoDieta;
    }
}
