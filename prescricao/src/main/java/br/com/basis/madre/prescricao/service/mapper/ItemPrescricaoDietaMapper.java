package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDietaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemPrescricaoDieta} and its DTO {@link ItemPrescricaoDietaDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoItemDietaMapper.class, TipoAprazamentoMapper.class, TipoUnidadeDietaMapper.class, PrescricaoDietaMapper.class})
public interface ItemPrescricaoDietaMapper extends EntityMapper<ItemPrescricaoDietaDTO, ItemPrescricaoDieta> {

    @Mapping(source = "tipoItemDieta.id", target = "tipoItemDietaId")
    @Mapping(source = "tipoAprazamento.id", target = "tipoAprazamentoId")
    @Mapping(source = "tipoUnidadeDieta.id", target = "tipoUnidadeDietaId")
    @Mapping(source = "prescricaoDieta.id", target = "prescricaoDietaId")
    ItemPrescricaoDietaDTO toDto(ItemPrescricaoDieta itemPrescricaoDieta);

    @Mapping(source = "tipoItemDietaId", target = "tipoItemDieta")
    @Mapping(source = "tipoAprazamentoId", target = "tipoAprazamento")
    @Mapping(source = "tipoUnidadeDietaId", target = "tipoUnidadeDieta")
    @Mapping(source = "prescricaoDietaId", target = "prescricaoDieta")
    ItemPrescricaoDieta toEntity(ItemPrescricaoDietaDTO itemPrescricaoDietaDTO);

    default ItemPrescricaoDieta fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemPrescricaoDieta itemPrescricaoDieta = new ItemPrescricaoDieta();
        itemPrescricaoDieta.setId(id);
        return itemPrescricaoDieta;
    }
}
