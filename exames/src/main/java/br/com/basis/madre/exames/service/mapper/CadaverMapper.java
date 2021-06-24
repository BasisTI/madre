package br.com.basis.madre.exames.service.mapper;


import br.com.basis.madre.exames.domain.*;
import br.com.basis.madre.exames.service.dto.CadaverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cadaver} and its DTO {@link CadaverDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CadaverMapper extends EntityMapper<CadaverDTO, Cadaver> {



    default Cadaver fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cadaver cadaver = new Cadaver();
        cadaver.setId(id);
        return cadaver;
    }
}
