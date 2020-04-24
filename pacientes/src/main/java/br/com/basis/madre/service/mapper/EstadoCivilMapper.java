package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.EstadoCivilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadoCivil} and its DTO {@link EstadoCivilDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstadoCivilMapper extends EntityMapper<EstadoCivilDTO, EstadoCivil> {



    default EstadoCivil fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstadoCivil estadoCivil = new EstadoCivil();
        estadoCivil.setId(id);
        return estadoCivil;
    }
}
