package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.HorarioExameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HorarioExame} and its DTO {@link HorarioExameDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoDeMarcacaoMapper.class, GradeAgendamentoExameMapper.class})
public interface HorarioExameMapper extends EntityMapper<HorarioExameDTO, HorarioExame> {

    @Mapping(source = "tipoDeMarcacao.id", target = "tipoDeMarcacaoId")
    @Mapping(source = "gradeAgendamentoExame.id", target = "gradeAgendamentoExameId")
    HorarioExameDTO toDto(HorarioExame horarioExame);

    @Mapping(source = "tipoDeMarcacaoId", target = "tipoDeMarcacao")
    @Mapping(source = "gradeAgendamentoExameId", target = "gradeAgendamentoExame")
    HorarioExame toEntity(HorarioExameDTO horarioExameDTO);

    default HorarioExame fromId(Long id) {
        if (id == null) {
            return null;
        }
        HorarioExame horarioExame = new HorarioExame();
        horarioExame.setId(id);
        return horarioExame;
    }
}
