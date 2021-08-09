package br.com.basis.madre.seguranca.service.mapper;


import br.com.basis.madre.seguranca.domain.*;
import br.com.basis.madre.seguranca.service.dto.UFDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UF} and its DTO {@link UFDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UFMapper extends EntityMapper<UFDTO, UF> {



    default UF fromId(Long id) {
        if (id == null) {
            return null;
        }
        UF uF = new UF();
        uF.setId(id);
        return uF;
    }
}
