package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.EspeciaisDiversosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EspeciaisDiversos} and its DTO {@link EspeciaisDiversosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EspeciaisDiversosMapper extends EntityMapper<EspeciaisDiversosDTO, EspeciaisDiversos> {



    default EspeciaisDiversos fromId(Long id) {
        if (id == null) {
            return null;
        }
        EspeciaisDiversos especiaisDiversos = new EspeciaisDiversos();
        especiaisDiversos.setId(id);
        return especiaisDiversos;
    }
}
