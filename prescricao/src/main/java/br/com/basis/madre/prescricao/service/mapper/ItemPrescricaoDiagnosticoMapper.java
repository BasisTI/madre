package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDiagnosticoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemPrescricaoDiagnostico} and its DTO {@link ItemPrescricaoDiagnosticoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PrescricaoDiagnosticoMapper.class})
public interface ItemPrescricaoDiagnosticoMapper extends EntityMapper<ItemPrescricaoDiagnosticoDTO, ItemPrescricaoDiagnostico> {

    @Mapping(source = "prescricaoDiagnostico.id", target = "prescricaoDiagnosticoId")
    ItemPrescricaoDiagnosticoDTO toDto(ItemPrescricaoDiagnostico itemPrescricaoDiagnostico);

    @Mapping(source = "prescricaoDiagnosticoId", target = "prescricaoDiagnostico")
    ItemPrescricaoDiagnostico toEntity(ItemPrescricaoDiagnosticoDTO itemPrescricaoDiagnosticoDTO);

    default ItemPrescricaoDiagnostico fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPrescricaoDiagnostico itemPrescricaoDiagnostico = new ItemPrescricaoDiagnostico();
        itemPrescricaoDiagnostico.setId(id);
        return itemPrescricaoDiagnostico;
    }
}
