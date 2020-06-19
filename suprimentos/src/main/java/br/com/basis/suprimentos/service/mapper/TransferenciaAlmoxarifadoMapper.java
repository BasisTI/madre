package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferenciaAlmoxarifado} and its DTO {@link TransferenciaAlmoxarifadoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlmoxarifadoMapper.class})
public interface TransferenciaAlmoxarifadoMapper extends EntityMapper<TransferenciaAlmoxarifadoDTO, TransferenciaAlmoxarifado> {

    @Mapping(source = "origem.id", target = "origemId")
    @Mapping(source = "destino.id", target = "destinoId")
    TransferenciaAlmoxarifadoDTO toDto(TransferenciaAlmoxarifado transferenciaAlmoxarifado);

    @Mapping(source = "origemId", target = "origem")
    @Mapping(source = "destinoId", target = "destino")
    TransferenciaAlmoxarifado toEntity(TransferenciaAlmoxarifadoDTO transferenciaAlmoxarifadoDTO);

    default TransferenciaAlmoxarifado fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransferenciaAlmoxarifado transferenciaAlmoxarifado = new TransferenciaAlmoxarifado();
        transferenciaAlmoxarifado.setId(id);
        return transferenciaAlmoxarifado;
    }
}
