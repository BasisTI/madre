package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.DiluenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Diluente} and its DTO {@link DiluenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiluenteMapper extends EntityMapper<DiluenteDTO, Diluente> {


    Diluente toEntity(DiluenteDTO diluenteDTO);

    default Diluente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Diluente diluente = new Diluente();
        diluente.setId(id);
        return diluente;
    }
}
