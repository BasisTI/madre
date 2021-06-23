package br.com.basis.madre.exames.service.mapper;


import br.com.basis.madre.exames.domain.*;
import br.com.basis.madre.exames.service.dto.TipoAmostraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoAmostra} and its DTO {@link TipoAmostraDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoAmostraMapper extends EntityMapper<TipoAmostraDTO, TipoAmostra> {



    default TipoAmostra fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoAmostra tipoAmostra = new TipoAmostra();
        tipoAmostra.setId(id);
        return tipoAmostra;
    }
}
