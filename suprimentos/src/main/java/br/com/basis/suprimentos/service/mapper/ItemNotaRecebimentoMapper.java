package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.ItemNotaRecebimento;
import br.com.basis.suprimentos.service.dto.ItemNotaRecebimentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RecebimentoMapper.class, MarcaComercialMapper.class, MaterialMapper.class, UnidadeMedidaMapper.class})
public interface ItemNotaRecebimentoMapper extends EntityMapper<ItemNotaRecebimentoDTO, ItemNotaRecebimento> {
    @Mapping(source = "recebimento.id", target = "recebimentoId")
    @Mapping(source = "marcaComercial.id", target = "marcaComercialId")
    @Mapping(source = "material.id", target = "materialId")
    @Mapping(source = "unidadeMedida.id", target = "unidadeMedidaId")
    ItemNotaRecebimentoDTO toDto(ItemNotaRecebimento itemNotaRecebimento);

    @Mapping(source = "recebimentoId", target = "recebimento")
    @Mapping(source = "marcaComercialId", target = "marcaComercial")
    @Mapping(source = "materialId", target = "material")
    @Mapping(source = "unidadeMedidaId", target = "unidadeMedida")
    ItemNotaRecebimento toEntity(ItemNotaRecebimentoDTO itemNotaRecebimentoDTO);

    default ItemNotaRecebimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemNotaRecebimento itemNotaRecebimento = new ItemNotaRecebimento();
        itemNotaRecebimento.setId(id);
        return itemNotaRecebimento;
    }
}
