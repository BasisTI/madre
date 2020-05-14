package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.MotivoDoBloqueioDeLeito;
import br.com.basis.madre.service.dto.MotivoDoBloqueioDeLeitoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface MotivoDoBloqueioDeLeitoMapper extends
    EntityMapper<MotivoDoBloqueioDeLeitoDTO, MotivoDoBloqueioDeLeito> {


    default MotivoDoBloqueioDeLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        MotivoDoBloqueioDeLeito motivoDoBloqueioDeLeito = new MotivoDoBloqueioDeLeito();
        motivoDoBloqueioDeLeito.setId(id);
        return motivoDoBloqueioDeLeito;
    }
}
