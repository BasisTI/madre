package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.GrupoAgendamentoExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GrupoAgendamentoExame} and its DTO {@link GrupoAgendamentoExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExameMapper.class, GradeDeAgendamentoMapper.class})
public interface GrupoAgendamentoExameMapper extends EntityMapper<GrupoAgendamentoExameDTO, GrupoAgendamentoExame> {

    @Mapping(source = "gradeDeAgendamento.id", target = "gradeDeAgendamentoId")
    GrupoAgendamentoExameDTO toDto(GrupoAgendamentoExame grupoAgendamentoExame);

    @Mapping(target = "removeExame", ignore = true)
    @Mapping(source = "gradeDeAgendamentoId", target = "gradeDeAgendamento")
    GrupoAgendamentoExame toEntity(GrupoAgendamentoExameDTO grupoAgendamentoExameDTO);

    default GrupoAgendamentoExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        GrupoAgendamentoExame grupoAgendamentoExame = new GrupoAgendamentoExame();
        grupoAgendamentoExame.setId(id);
        return grupoAgendamentoExame;
    }
}
