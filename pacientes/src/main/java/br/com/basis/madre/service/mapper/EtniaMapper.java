package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.EtniaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Etnia} and its DTO {@link EtniaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtniaMapper extends EntityMapper<EtniaDTO, Etnia> {



    default Etnia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etnia etnia = new Etnia();
        etnia.setId(id);
        return etnia;
    }
}
