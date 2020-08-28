package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.MedicamentoCIDDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicamentoCID} and its DTO {@link MedicamentoCIDDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicamentoCIDMapper extends EntityMapper<MedicamentoCIDDTO, MedicamentoCID> {



    default MedicamentoCID fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicamentoCID medicamentoCID = new MedicamentoCID();
        medicamentoCID.setId(id);
        return medicamentoCID;
    }
}
