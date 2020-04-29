package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.CID;
import br.com.basis.madre.service.dto.CidDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CIDMapper extends EntityMapper<CidDTO, CID> {

    default CID fromId(Long id) {
        if (id == null) {
            return null;
        }
        CID cID = new CID();
        cID.setId(id);
        return cID;
    }

}
