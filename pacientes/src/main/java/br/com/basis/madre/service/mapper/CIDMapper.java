package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.CIDDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CID and its DTO CIDDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CIDMapper extends EntityMapper<CIDDTO, CID> {



    default CID fromId(Long id) {
        if (id == null) {
            return null;
        }
        CID cID = new CID();
        cID.setId(id);
        return cID;
    }
}
