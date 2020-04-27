package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.PlanoDeSaudeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoDeSaude} and its DTO {@link PlanoDeSaudeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanoDeSaudeMapper extends EntityMapper<PlanoDeSaudeDTO, PlanoDeSaude> {



    default PlanoDeSaude fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoDeSaude planoDeSaude = new PlanoDeSaude();
        planoDeSaude.setId(id);
        return planoDeSaude;
    }
}
