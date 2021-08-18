package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeDeAgendamento} and its DTO {@link GradeDeAgendamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {SalaMapper.class})
public interface GradeDeAgendamentoMapper extends EntityMapper<GradeDeAgendamentoDTO, GradeDeAgendamento> {

    @Mapping(source = "gradeDeAgendamento.id", target = "gradeDeAgendamentoId")
    @Mapping(source = "gradeDeAgendamento.grade", target = "gradeDeAgendamentoGrade")
    GradeDeAgendamentoDTO toDto(GradeDeAgendamento gradeDeAgendamento);

    @Mapping(source = "gradeDeAgendamentoId", target = "gradeDeAgendamento")
    @Mapping(target = "gradeExames", ignore = true)
    @Mapping(target = "removeGradeExame", ignore = true)
    @Mapping(target = "gradeGrupoExames", ignore = true)
    @Mapping(target = "removeGradeGrupoExame", ignore = true)
    @Mapping(target = "gradeHorarios", ignore = true)
    @Mapping(target = "removeGradeHorario", ignore = true)
    GradeDeAgendamento toEntity(GradeDeAgendamentoDTO gradeDeAgendamentoDTO);

    default GradeDeAgendamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        GradeDeAgendamento gradeDeAgendamento = new GradeDeAgendamento();
        gradeDeAgendamento.setId(id);
        return gradeDeAgendamento;
    }
}
