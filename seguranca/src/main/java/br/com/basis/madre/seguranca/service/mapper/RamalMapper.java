package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.Ramal;
import br.com.basis.madre.seguranca.service.dto.RamalDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Ramal} and its DTO {@link RamalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RamalMapper extends EntityMapper<RamalDTO, Ramal> {


    @Mapping(target = "servidorRamals", ignore = true)
    @Mapping(target = "removeServidorRamal", ignore = true)
    Ramal toEntity(RamalDTO ramalDTO);

    default Ramal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ramal ramal = new Ramal();
        ramal.setId(id);
        return ramal;
    }
}
