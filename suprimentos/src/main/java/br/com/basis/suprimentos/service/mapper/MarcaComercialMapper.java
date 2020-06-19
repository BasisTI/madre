package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.MarcaComercialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MarcaComercial} and its DTO {@link MarcaComercialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarcaComercialMapper extends EntityMapper<MarcaComercialDTO, MarcaComercial> {



    default MarcaComercial fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarcaComercial marcaComercial = new MarcaComercial();
        marcaComercial.setId(id);
        return marcaComercial;
    }
}
