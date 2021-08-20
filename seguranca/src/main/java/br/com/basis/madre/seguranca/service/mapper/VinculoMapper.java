package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.VinculoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vinculo} and its DTO {@link VinculoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VinculoMapper extends EntityMapper<VinculoDTO, Vinculo> {



    default Vinculo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vinculo vinculo = new Vinculo();
        vinculo.setId(id);
        return vinculo;
    }
}
