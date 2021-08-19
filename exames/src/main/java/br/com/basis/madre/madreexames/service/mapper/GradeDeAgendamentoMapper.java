package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeDeAgendamento} and its DTO {@link GradeDeAgendamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {SalaMapper.class})
public interface GradeDeAgendamentoMapper extends EntityMapper<GradeDeAgendamentoDTO, GradeDeAgendamento> {

    @Mapping(source = "sala.id", target = "salaId")
    GradeDeAgendamentoDTO toDto(GradeDeAgendamento gradeDeAgendamento);

    @Mapping(target = "gradeExames", ignore = true)
    @Mapping(target = "removeGradeExame", ignore = true)
    @Mapping(target = "gradeGrupoExames", ignore = true)
    @Mapping(target = "removeGradeGrupoExame", ignore = true)
    @Mapping(target = "gradeHorarios", ignore = true)
    @Mapping(target = "removeGradeHorario", ignore = true)
    @Mapping(source = "salaId", target = "sala")
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
