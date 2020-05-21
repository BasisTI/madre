package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.TipoItemDietaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoItemDieta} and its DTO {@link TipoItemDietaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoItemDietaMapper extends EntityMapper<TipoItemDietaDTO, TipoItemDieta> {


    @Mapping(target = "itemPrescricaoDietas", ignore = true)
    @Mapping(target = "removeItemPrescricaoDieta", ignore = true)
    TipoItemDieta toEntity(TipoItemDietaDTO tipoItemDietaDTO);

    default TipoItemDieta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoItemDieta tipoItemDieta = new TipoItemDieta();
        tipoItemDieta.setId(id);
        return tipoItemDieta;
    }
}
