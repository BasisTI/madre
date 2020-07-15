package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoDiagnostico;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDiagnosticoDTO;

/**
 * Mapper for the entity {@link ItemPrescricaoDiagnostico} and its DTO {@link ItemPrescricaoDiagnosticoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CIDMapper.class})
public interface ItemPrescricaoDiagnosticoMapper extends EntityMapper<ItemPrescricaoDiagnosticoDTO, ItemPrescricaoDiagnostico> {

	@Mapping(source = "cid", target="cid")
    ItemPrescricaoDiagnosticoDTO toDto(ItemPrescricaoDiagnostico itemPrescricaoDiagnostico);

	@Mapping(source = "cid", target="cid")
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
