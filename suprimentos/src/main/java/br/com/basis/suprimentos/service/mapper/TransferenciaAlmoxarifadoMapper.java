package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.TransferenciaAlmoxarifado;
import br.com.basis.suprimentos.service.dto.TransferenciaAlmoxarifadoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AlmoxarifadoMapper.class, InformacaoTransferenciaMapper.class, ItemTransferenciaMapper.class})
public interface TransferenciaAlmoxarifadoMapper extends EntityMapper<TransferenciaAlmoxarifadoDTO, TransferenciaAlmoxarifado> {
    @Mapping(source = "origem.id", target = "origemId")
    @Mapping(source = "destino.id", target = "destinoId")
    @Mapping(source = "informacaoTransferencia", target = "informacaoTransferencia")
    @Mapping(source = "itens", target = "itens")
    TransferenciaAlmoxarifadoDTO toDto(TransferenciaAlmoxarifado transferenciaAlmoxarifado);

    @Mapping(source = "origemId", target = "origem")
    @Mapping(source = "destinoId", target = "destino")
    @Mapping(source = "informacaoTransferencia", target = "informacaoTransferencia")
    @Mapping(source = "itens", target = "itens")
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
