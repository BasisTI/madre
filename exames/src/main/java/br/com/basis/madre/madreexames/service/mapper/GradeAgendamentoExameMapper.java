package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.GradeAgendamentoExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeAgendamentoExame} and its DTO {@link GradeAgendamentoExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExameMapper.class, SalaMapper.class})
public interface GradeAgendamentoExameMapper extends EntityMapper<GradeAgendamentoExameDTO, GradeAgendamentoExame> {

    @Mapping(source = "exame.id", target = "exameId")
    @Mapping(source = "sala.id", target = "salaId")
    GradeAgendamentoExameDTO toDto(GradeAgendamentoExame gradeAgendamentoExame);

    @Mapping(target = "horarioDaGrades", ignore = true)
    @Mapping(target = "removeHorarioDaGrade", ignore = true)
    @Mapping(source = "exameId", target = "exame")
    @Mapping(source = "salaId", target = "sala")
    GradeAgendamentoExame toEntity(GradeAgendamentoExameDTO gradeAgendamentoExameDTO);

    default GradeAgendamentoExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        GradeAgendamentoExame gradeAgendamentoExame = new GradeAgendamentoExame();
        gradeAgendamentoExame.setId(id);
        return gradeAgendamentoExame;
    }
}
