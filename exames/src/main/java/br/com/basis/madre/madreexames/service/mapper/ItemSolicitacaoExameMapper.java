package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.ItemSolicitacaoExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemSolicitacaoExame} and its DTO {@link ItemSolicitacaoExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExameMapper.class, SolicitacaoExameMapper.class})
public interface ItemSolicitacaoExameMapper extends EntityMapper<ItemSolicitacaoExameDTO, ItemSolicitacaoExame> {

    @Mapping(source = "itemSolicitacaoExame.id", target = "itemSolicitacaoExameId")
    @Mapping(source = "itemSolicitacaoExame.situacao", target = "itemSolicitacaoExameSituacao")
    @Mapping(source = "solicitacaoExame.id", target = "solicitacaoExameId")
    ItemSolicitacaoExameDTO toDto(ItemSolicitacaoExame itemSolicitacaoExame);

    @Mapping(source = "itemSolicitacaoExameId", target = "itemSolicitacaoExame")
    @Mapping(source = "solicitacaoExameId", target = "solicitacaoExame")
    ItemSolicitacaoExame toEntity(ItemSolicitacaoExameDTO itemSolicitacaoExameDTO);

    default ItemSolicitacaoExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        ItemSolicitacaoExame itemSolicitacaoExame = new ItemSolicitacaoExame();
        itemSolicitacaoExame.setId(id);
        return itemSolicitacaoExame;
    }
}
