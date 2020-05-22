package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.PrescricaoDieta;
import br.com.basis.madre.prescricao.service.dto.PrescricaoDietaDTO;

/**
 * Mapper for the entity {@link PrescricaoDieta} and its DTO {@link PrescricaoDietaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemPrescricaoDietaMapper.class})
public interface PrescricaoDietaMapper extends EntityMapper<PrescricaoDietaDTO, PrescricaoDieta> {

	@Mapping(source = "itemPrescricaoDietas", target = "itemPrescricaoDietaDTO")
	PrescricaoDietaDTO toDto(PrescricaoDieta prescricaoDieta);
	
	@Mapping(source = "itemPrescricaoDietaDTO", target = "itemPrescricaoDietas")
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
