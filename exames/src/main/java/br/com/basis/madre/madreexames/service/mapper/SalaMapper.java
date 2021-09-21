package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.SalaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sala} and its DTO {@link SalaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalaMapper extends EntityMapper<SalaDTO, Sala> {



    default Sala fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sala sala = new Sala();
        sala.setId(id);
        return sala;
    }
}
