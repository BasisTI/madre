package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.DiaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dia} and its DTO {@link DiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DiaMapper extends EntityMapper<DiaDTO, Dia> {


    @Mapping(target = "gradeAgendamentoExames", ignore = true)
    @Mapping(target = "removeGradeAgendamentoExame", ignore = true)
    Dia toEntity(DiaDTO diaDTO);

    default Dia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dia dia = new Dia();
        dia.setId(id);
        return dia;
    }
}
