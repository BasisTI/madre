package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.NaturalidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Naturalidade} and its DTO {@link NaturalidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NaturalidadeMapper extends EntityMapper<NaturalidadeDTO, Naturalidade> {



    default Naturalidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Naturalidade naturalidade = new Naturalidade();
        naturalidade.setId(id);
        return naturalidade;
    }
}
