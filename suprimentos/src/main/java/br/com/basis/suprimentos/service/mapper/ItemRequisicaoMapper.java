package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.ItemRequisicao;
import br.com.basis.suprimentos.service.dto.ItemRequisicaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ItemRequisicaoMapper extends EntityMapper<ItemRequisicaoDTO, ItemRequisicao> {
    @Mapping(source = "material.id", target = "materialId")
    ItemRequisicaoDTO toDto(ItemRequisicao itemRequisicao);

    @Mapping(source = "materialId", target = "material.id")
    ItemRequisicao toEntity(ItemRequisicaoDTO itemRequisicaoDTO);

    Set<ItemRequisicao> toEntity(Set<ItemRequisicaoDTO> itens);

    Set<ItemRequisicaoDTO> toDto(Set<ItemRequisicao> itens);

    default ItemRequisicao fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemRequisicao itemRequisicao = new ItemRequisicao();
        itemRequisicao.setId(id);
        return itemRequisicao;
    }
}
