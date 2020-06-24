package br.com.basis.consulta.service.mapper;

import br.com.basis.consulta.domain.FormaDeAgendamento;
import br.com.basis.consulta.service.dto.FormaDeAgendamentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link FormaDeAgendamento} and its DTO {@link FormaDeAgendamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormaDeAgendamentoMapper extends EntityMapper<FormaDeAgendamentoDTO, FormaDeAgendamento> {


    @Mapping(target = "emergencia", ignore = true)
    FormaDeAgendamento toEntity(FormaDeAgendamentoDTO formaDeAgendamentoDTO);

    default FormaDeAgendamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormaDeAgendamento formaDeAgendamento = new FormaDeAgendamento();
        formaDeAgendamento.setId(id);
        return formaDeAgendamento;
    }
}
