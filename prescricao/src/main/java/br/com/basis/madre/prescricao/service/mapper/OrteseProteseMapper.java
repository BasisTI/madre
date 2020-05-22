package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.OrteseProteseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrteseProtese} and its DTO {@link OrteseProteseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrteseProteseMapper extends EntityMapper<OrteseProteseDTO, OrteseProtese> {



    default OrteseProtese fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrteseProtese orteseProtese = new OrteseProtese();
        orteseProtese.setId(id);
        return orteseProtese;
    }
}
