package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.EventoLeito;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import br.com.basis.madre.service.dto.EventoLeitoDTO;
import br.com.basis.madre.service.dto.LiberacaoDeLeitoDTO;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EventoLeito} and its DTO {@link EventoLeitoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TipoDoEventoLeitoMapper.class, LeitoMapper.class,
    OrigemDaReservaDeLeitoMapper.class, TipoDaReservaDeLeitoMapper.class,
    MotivoDoBloqueioDeLeitoMapper.class})
public interface EventoLeitoMapper extends EntityMapper<EventoLeitoDTO, EventoLeito> {

    @Mapping(source = "tipoDoEvento.id", target = "tipoDoEventoId")
    @Mapping(source = "leito.id", target = "leitoId")
    @Mapping(source = "origem.id", target = "origemId")
    @Mapping(source = "tipo.id", target = "tipoId")
    @Mapping(source = "motivo.id", target = "motivoId")
    EventoLeitoDTO toDto(EventoLeito eventoLeito);

    @Mapping(source = "tipoDoEventoId", target = "tipoDoEvento")
    @Mapping(source = "leitoId", target = "leito")
    @Mapping(source = "origemId", target = "origem")
    @Mapping(source = "tipoId", target = "tipo")
    @Mapping(source = "motivoId", target = "motivo")
    EventoLeito toEntity(EventoLeitoDTO eventoLeitoDTO);

    EventoLeitoDTO toDto(ReservaDeLeitoDTO reservaDeLeitoDTO);

    EventoLeitoDTO toDto(BloqueioDeLeitoDTO bloqueioDeLeitoDTO);

    EventoLeitoDTO toDto(LiberacaoDeLeitoDTO liberacaoDeLeitoDTO);

    default EventoLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventoLeito eventoLeito = new EventoLeito();
        eventoLeito.setId(id);
        return eventoLeito;
    }
}
