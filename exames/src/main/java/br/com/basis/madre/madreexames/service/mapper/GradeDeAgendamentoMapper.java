package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeDeAgendamento} and its DTO {@link GradeDeAgendamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {SalaMapper.class, ExameMapper.class, GrupoAgendamentoExameMapper.class})
public interface GradeDeAgendamentoMapper extends EntityMapper<GradeDeAgendamentoDTO, GradeDeAgendamento> {

    @Mapping(source = "sala.id", target = "salaId")
    @Mapping(source = "exame.id", target = "exameId")
    @Mapping(source = "grupoAgendamentoExame.id", target = "grupoAgendamentoExameId")
    GradeDeAgendamentoDTO toDto(GradeDeAgendamento gradeDeAgendamento);

    @Mapping(target = "gradeHorarios", ignore = true)
    @Mapping(target = "removeGradeHorario", ignore = true)
    @Mapping(source = "salaId", target = "sala")
    @Mapping(source = "exameId", target = "exame")
    @Mapping(source = "grupoAgendamentoExameId", target = "grupoAgendamentoExame")
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
