package br.com.basis.madre.madreexames.service.mapper;


import br.com.basis.madre.madreexames.domain.*;
import br.com.basis.madre.madreexames.service.dto.HorarioAgendadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HorarioAgendado} and its DTO {@link HorarioAgendadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoDeMarcacaoMapper.class, GradeDeAgendamentoMapper.class})
public interface HorarioAgendadoMapper extends EntityMapper<HorarioAgendadoDTO, HorarioAgendado> {

    @Mapping(source = "horarioAgendado.id", target = "horarioAgendadoId")
    @Mapping(source = "horarioAgendado.dia", target = "horarioAgendadoDia")
    @Mapping(source = "gradeDeAgendamento.id", target = "gradeDeAgendamentoId")
    HorarioAgendadoDTO toDto(HorarioAgendado horarioAgendado);

    @Mapping(source = "horarioAgendadoId", target = "horarioAgendado")
    @Mapping(target = "horarioAgendadoLivres", ignore = true)
    @Mapping(target = "removeHorarioAgendadoLivre", ignore = true)
    @Mapping(source = "gradeDeAgendamentoId", target = "gradeDeAgendamento")
    HorarioAgendado toEntity(HorarioAgendadoDTO horarioAgendadoDTO);

    default HorarioAgendado fromId(Long id) {
        if (id == null) {
            return null;
        }
        HorarioAgendado horarioAgendado = new HorarioAgendado();
        horarioAgendado.setId(id);
        return horarioAgendado;
    }
}
