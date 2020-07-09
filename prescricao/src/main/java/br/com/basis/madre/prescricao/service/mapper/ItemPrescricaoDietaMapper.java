package br.com.basis.madre.prescricao.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoDieta;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoDietaDTO;

/**
 * Mapper for the entity {@link ItemPrescricaoDieta} and its DTO
 * {@link ItemPrescricaoDietaDTO}.
 */
@Mapper(componentModel = "spring", uses = { TipoItemDietaMapper.class, TipoAprazamentoMapper.class,
		TipoUnidadeDietaMapper.class, })
public interface ItemPrescricaoDietaMapper extends EntityMapper<ItemPrescricaoDietaDTO, ItemPrescricaoDieta> {

	@Mapping(source = "tipoItemDieta", target = "tipoItemDieta")
	@Mapping(source = "tipoAprazamento", target = "tipoAprazamento")
	@Mapping(source = "tipoUnidadeDieta", target = "tipoUnidadeDieta")
	ItemPrescricaoDietaDTO toDto(ItemPrescricaoDieta itemPrescricaoDieta);

	@Mapping(source = "tipoItemDieta", target = "tipoItemDieta")
	@Mapping(source = "tipoAprazamento", target = "tipoAprazamento")
	@Mapping(source = "tipoUnidadeDieta", target = "tipoUnidadeDieta")
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
