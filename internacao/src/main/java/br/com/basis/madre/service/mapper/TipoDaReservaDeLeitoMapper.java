package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.TipoDaReservaDeLeito;
import br.com.basis.madre.service.dto.TipoDaReservaDeLeitoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TipoDaReservaDeLeitoMapper extends
    EntityMapper<TipoDaReservaDeLeitoDTO, TipoDaReservaDeLeito> {


    default TipoDaReservaDeLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDaReservaDeLeito tipoDaReservaDeLeito = new TipoDaReservaDeLeito();
        tipoDaReservaDeLeito.setId(id);
        return tipoDaReservaDeLeito;
    }
}
