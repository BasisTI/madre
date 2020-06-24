package br.com.basis.consulta.service.mapper;

import br.com.basis.consulta.domain.Emergencia;
import br.com.basis.consulta.service.dto.EmergenciaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Emergencia} and its DTO {@link EmergenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {CondicaoDeAtendimentoMapper.class, FormaDeAgendamentoMapper.class})
public interface EmergenciaMapper extends EntityMapper<EmergenciaDTO, Emergencia> {

    @Mapping(source = "condicaoDeAtendimento.id", target = "condicaoDeAtendimentoId")
    @Mapping(source = "formaDeAgendamento.id", target = "formaDeAgendamentoId")
    EmergenciaDTO toDto(Emergencia emergencia);

    @Mapping(source = "condicaoDeAtendimentoId", target = "condicaoDeAtendimento")
    @Mapping(source = "formaDeAgendamentoId", target = "formaDeAgendamento")
    Emergencia toEntity(EmergenciaDTO emergenciaDTO);

    default Emergencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Emergencia emergencia = new Emergencia();
        emergencia.setId(id);
        return emergencia;
    }
}
