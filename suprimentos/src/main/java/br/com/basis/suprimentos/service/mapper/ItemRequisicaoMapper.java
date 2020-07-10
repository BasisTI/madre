package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.ItemRequisicao;
import br.com.basis.suprimentos.service.dto.ItemRequisicaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RequisicaoMaterialMapper.class})
public interface ItemRequisicaoMapper extends EntityMapper<ItemRequisicaoDTO, ItemRequisicao> {
    @Mapping(source = "requisicao.id", target = "requisicaoId")
    @Mapping(source = "almoxarifado.id", target = "almoxarifadoId")
    @Mapping(source = "material.id", target = "materialId")
    ItemRequisicaoDTO toDto(ItemRequisicao itemRequisicao);

    @Mapping(source = "requisicaoId", target = "requisicao")
    @Mapping(source = "materialId", target = "material.id")
    @Mapping(source = "almoxarifadoId", target = "almoxarifado.id")
    ItemRequisicao toEntity(ItemRequisicaoDTO itemRequisicaoDTO);

    default ItemRequisicao fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemRequisicao itemRequisicao = new ItemRequisicao();
        itemRequisicao.setId(id);
        return itemRequisicao;
    }
}
