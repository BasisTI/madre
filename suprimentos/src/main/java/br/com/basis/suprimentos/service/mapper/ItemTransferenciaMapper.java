package br.com.basis.suprimentos.service.mapper;


import br.com.basis.suprimentos.domain.ItemTransferencia;
import br.com.basis.suprimentos.service.dto.ItemTransferenciaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ItemTransferenciaMapper extends EntityMapper<ItemTransferenciaDTO, ItemTransferencia> {
    default ItemTransferencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemTransferencia itemTransferencia = new ItemTransferencia();
        itemTransferencia.setId(id);
        return itemTransferencia;
    }
}
