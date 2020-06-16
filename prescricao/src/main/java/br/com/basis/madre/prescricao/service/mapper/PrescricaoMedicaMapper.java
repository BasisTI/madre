package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.PrescricaoMedicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrescricaoMedica} and its DTO {@link PrescricaoMedicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrescricaoMedicaMapper extends EntityMapper<PrescricaoMedicaDTO, PrescricaoMedica> {



    default PrescricaoMedica fromId(Long id) {
        if (id == null) {
            return null;
        }
        PrescricaoMedica prescricaoMedica = new PrescricaoMedica();
        prescricaoMedica.setId(id);
        return prescricaoMedica;
    }
}
