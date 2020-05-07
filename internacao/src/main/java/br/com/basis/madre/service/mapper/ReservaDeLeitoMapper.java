package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.ReservaDeLeito;
import br.com.basis.madre.service.dto.ReservaDeLeitoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LeitoMapper.class, OrigemDaReservaDeLeitoMapper.class,
    TipoDaReservaDeLeitoMapper.class})
public interface ReservaDeLeitoMapper extends EntityMapper<ReservaDeLeitoDTO, ReservaDeLeito> {

    @Mapping(source = "leito.id", target = "leitoId")
    @Mapping(source = "origem.id", target = "origemId")
    @Mapping(source = "tipo.id", target = "tipoId")
    ReservaDeLeitoDTO toDto(ReservaDeLeito reservaDeLeito);

    @Mapping(source = "leitoId", target = "leito")
    @Mapping(source = "origemId", target = "origem")
    @Mapping(source = "tipoId", target = "tipo")
    ReservaDeLeito toEntity(ReservaDeLeitoDTO reservaDeLeitoDTO);

    default ReservaDeLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReservaDeLeito reservaDeLeito = new ReservaDeLeito();
        reservaDeLeito.setId(id);
        return reservaDeLeito;
    }
}
