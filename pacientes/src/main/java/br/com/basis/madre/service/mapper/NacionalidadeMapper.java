package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.NacionalidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nacionalidade} and its DTO {@link NacionalidadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NacionalidadeMapper extends EntityMapper<NacionalidadeDTO, Nacionalidade> {



    default Nacionalidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nacionalidade nacionalidade = new Nacionalidade();
        nacionalidade.setId(id);
        return nacionalidade;
    }
}
