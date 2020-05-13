package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.OrigemDaReservaDeLeito;
import br.com.basis.madre.service.dto.OrigemDaReservaDeLeitoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrigemDaReservaDeLeitoMapper extends
    EntityMapper<OrigemDaReservaDeLeitoDTO, OrigemDaReservaDeLeito> {


    default OrigemDaReservaDeLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrigemDaReservaDeLeito origemDaReservaDeLeito = new OrigemDaReservaDeLeito();
        origemDaReservaDeLeito.setId(id);
        return origemDaReservaDeLeito;
    }
}
