package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.HospitalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hospital} and its DTO {@link HospitalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HospitalMapper extends EntityMapper<HospitalDTO, Hospital> {



    default Hospital fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hospital hospital = new Hospital();
        hospital.setId(id);
        return hospital;
    }
}
