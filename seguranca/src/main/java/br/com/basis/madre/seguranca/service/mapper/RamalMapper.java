package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.RamalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ramal} and its DTO {@link RamalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RamalMapper extends EntityMapper<RamalDTO, Ramal> {



    default Ramal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ramal ramal = new Ramal();
        ramal.setId(id);
        return ramal;
    }
}
