package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.PrescricaoProcedimento;
import br.com.basis.madre.prescricao.service.dto.PrescricaoProcedimentoDTO;

/**
 * Mapper for the entity {@link PrescricaoProcedimento} and its DTO {@link PrescricaoProcedimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemPrescricaoProcedimentoMapper.class})
public interface PrescricaoProcedimentoMapper extends EntityMapper<PrescricaoProcedimentoDTO, PrescricaoProcedimento> {


	@Mapping(source = "itemPrescricaoProcedimentos", target = "itemPrescricaoProcedimentoDTO")
	PrescricaoProcedimentoDTO toDto(PrescricaoProcedimento prescricaoProcedimento);
	
	@Mapping(source = "itemPrescricaoProcedimentoDTO", target = "itemPrescricaoProcedimentos")
	PrescricaoProcedimento toEntity(PrescricaoProcedimentoDTO prescricaoProcedimentoDTO);

    default PrescricaoProcedimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrescricaoProcedimento prescricaoProcedimento = new PrescricaoProcedimento();
        prescricaoProcedimento.setId(id);
        return prescricaoProcedimento;
    }
}
