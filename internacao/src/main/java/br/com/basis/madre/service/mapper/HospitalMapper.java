package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Hospital;
import br.com.basis.madre.service.dto.HospitalDTO;
import org.mapstruct.Mapper;

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
