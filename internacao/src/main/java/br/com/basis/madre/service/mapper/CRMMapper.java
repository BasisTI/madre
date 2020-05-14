package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.CRM;
import br.com.basis.madre.service.dto.CrmDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CRMMapper extends EntityMapper<CrmDTO, CRM> {

    default CRM fromId(Long id) {
        if (id == null) {
            return null;
        }
        CRM cRM = new CRM();
        cRM.setId(id);
        return cRM;
    }

}
