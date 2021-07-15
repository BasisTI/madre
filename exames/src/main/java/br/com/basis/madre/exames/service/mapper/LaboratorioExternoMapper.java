package br.com.basis.madre.exames.service.mapper;


import br.com.basis.madre.exames.domain.*;
import br.com.basis.madre.exames.service.dto.LaboratorioExternoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LaboratorioExterno} and its DTO {@link LaboratorioExternoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LaboratorioExternoMapper extends EntityMapper<LaboratorioExternoDTO, LaboratorioExterno> {



    default LaboratorioExterno fromId(Long id) {
        if (id == null) {
            return null;
        }
        LaboratorioExterno laboratorioExterno = new LaboratorioExterno();
        laboratorioExterno.setId(id);
        return laboratorioExterno;
    }
}
