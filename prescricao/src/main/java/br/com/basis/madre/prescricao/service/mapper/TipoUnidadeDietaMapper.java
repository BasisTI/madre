package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.TipoUnidadeDietaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoUnidadeDieta} and its DTO {@link TipoUnidadeDietaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoUnidadeDietaMapper extends EntityMapper<TipoUnidadeDietaDTO, TipoUnidadeDieta> {


    TipoUnidadeDieta toEntity(TipoUnidadeDietaDTO tipoUnidadeDietaDTO);

    default TipoUnidadeDieta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoUnidadeDieta tipoUnidadeDieta = new TipoUnidadeDieta();
        tipoUnidadeDieta.setId(id);
        return tipoUnidadeDieta;
    }
}
