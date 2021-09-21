package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.HorarioLivreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HorarioLivre} and its DTO {@link HorarioLivreDTO}.
 */
@Mapper(componentModel = "spring", uses = {HorarioAgendadoMapper.class})
public interface HorarioLivreMapper extends EntityMapper<HorarioLivreDTO, HorarioLivre> {

    @Mapping(source = "horarioAgendado.id", target = "horarioAgendadoId")
    HorarioLivreDTO toDto(HorarioLivre horarioLivre);

    @Mapping(source = "horarioAgendadoId", target = "horarioAgendado")
    HorarioLivre toEntity(HorarioLivreDTO horarioLivreDTO);

    default HorarioLivre fromId(Long id) {
        if (id == null) {
            return null;
        }
        HorarioLivre horarioLivre = new HorarioLivre();
        horarioLivre.setId(id);
        return horarioLivre;
    }
}
