package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoEspecialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrescricaoProcedimentoEspecial} and its DTO {@link PrescricaoProcedimentoEspecialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrescricaoProcedimentoEspecialMapper extends EntityMapper<PrescricaoProcedimentoEspecialDTO, PrescricaoProcedimentoEspecial> {


    @Mapping(target = "itemPrescricaoProcedimentoEspecials", ignore = true)
    @Mapping(target = "removeItemPrescricaoProcedimentoEspecial", ignore = true)
    PrescricaoProcedimentoEspecial toEntity(PrescricaoProcedimentoEspecialDTO prescricaoProcedimentoEspecialDTO);

    default PrescricaoProcedimentoEspecial fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial = new PrescricaoProcedimentoEspecial();
        prescricaoProcedimentoEspecial.setId(id);
        return prescricaoProcedimentoEspecial;
    }
}
