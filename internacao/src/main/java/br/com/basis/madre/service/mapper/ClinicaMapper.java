package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.ClinicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clinica} and its DTO {@link ClinicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClinicaMapper extends EntityMapper<ClinicaDTO, Clinica> {



    default Clinica fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clinica clinica = new Clinica();
        clinica.setId(id);
        return clinica;
    }
}
