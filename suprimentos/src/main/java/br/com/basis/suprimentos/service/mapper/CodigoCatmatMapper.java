package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.CodigoCatmatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CodigoCatmat} and its DTO {@link CodigoCatmatDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CodigoCatmatMapper extends EntityMapper<CodigoCatmatDTO, CodigoCatmat> {



    default CodigoCatmat fromId(Long id) {
        if (id == null) {
            return null;
        }
        CodigoCatmat codigoCatmat = new CodigoCatmat();
        codigoCatmat.setId(id);
        return codigoCatmat;
    }
}
