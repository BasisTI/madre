package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.Anticoagulante;
import br.com.basis.madre.madreexames.service.dto.AnticoagulanteDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Anticoagulante} and its DTO {@link AnticoagulanteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnticoagulanteMapper extends EntityMapper<AnticoagulanteDTO, Anticoagulante> {



    default Anticoagulante fromId(Long id) {
        if (id == null) {
            return null;
        }
        Anticoagulante anticoagulante = new Anticoagulante();
        anticoagulante.setId(id);
        return anticoagulante;
    }
}
