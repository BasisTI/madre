package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.PlanoDeSaude;
import br.com.basis.madre.service.dto.PlanoDeSaudeDTO;
import org.mapstruct.Mapper;


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
