package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoEspecialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrescricaoProcedimentoEspecial} and its DTO {@link PrescricaoProcedimentoEspecialDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemPrescricaoProcedimentoEspecialMapper.class})
public interface PrescricaoProcedimentoEspecialMapper extends EntityMapper<PrescricaoProcedimentoEspecialDTO, PrescricaoProcedimentoEspecial> {


	@Mapping(source = "itemPrescricaoProcedimentoEspecials", target = "itemPrescricaoProcedimentoEspecialDTO")
	PrescricaoProcedimentoEspecialDTO toDto(PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial);
	
	@Mapping(source = "itemPrescricaoProcedimentoEspecialDTO", target = "itemPrescricaoProcedimentoEspecials")
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
