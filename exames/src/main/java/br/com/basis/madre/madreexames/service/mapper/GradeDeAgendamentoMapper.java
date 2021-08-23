package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.GradeDeAgendamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeDeAgendamento} and its DTO {@link GradeDeAgendamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExameMapper.class, SalaMapper.class, GrupoAgendamentoExameMapper.class})
public interface GradeDeAgendamentoMapper extends EntityMapper<GradeDeAgendamentoDTO, GradeDeAgendamento> {

    @Mapping(source = "exameGrade.id", target = "exameGradeId")
    @Mapping(source = "exameGrade.nome", target = "exameGradeNome")
    @Mapping(source = "salaGrade.id", target = "salaGradeId")
    @Mapping(source = "salaGrade.identificacaoDaSala", target = "salaGradeIdentificacaoDaSala")
    @Mapping(source = "grupoAgendamentoExame.id", target = "grupoAgendamentoExameId")
    GradeDeAgendamentoDTO toDto(GradeDeAgendamento gradeDeAgendamento);

    @Mapping(target = "gradeHorarios", ignore = true)
    @Mapping(target = "removeGradeHorario", ignore = true)
    @Mapping(source = "exameGradeId", target = "exameGrade")
    @Mapping(source = "salaGradeId", target = "salaGrade")
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
