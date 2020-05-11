package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.TipoDoEventoLeitoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoDoEventoLeito} and its DTO {@link TipoDoEventoLeitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDoEventoLeitoMapper extends EntityMapper<TipoDoEventoLeitoDTO, TipoDoEventoLeito> {



    default TipoDoEventoLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDoEventoLeito tipoDoEventoLeito = new TipoDoEventoLeito();
        tipoDoEventoLeito.setId(id);
        return tipoDoEventoLeito;
    }
}
