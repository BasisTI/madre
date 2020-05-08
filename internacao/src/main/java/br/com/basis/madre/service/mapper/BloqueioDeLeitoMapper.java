package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.BloqueioDeLeito;
import br.com.basis.madre.service.dto.BloqueioDeLeitoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LeitoMapper.class, MotivoDoBloqueioDeLeitoMapper.class})
public interface BloqueioDeLeitoMapper extends EntityMapper<BloqueioDeLeitoDTO, BloqueioDeLeito> {

    @Mapping(source = "leito.id", target = "leitoId")
    @Mapping(source = "motivo.id", target = "motivoId")
    BloqueioDeLeitoDTO toDto(BloqueioDeLeito bloqueioDeLeito);

    @Mapping(source = "leitoId", target = "leito")
    @Mapping(source = "motivoId", target = "motivo")
    BloqueioDeLeito toEntity(BloqueioDeLeitoDTO bloqueioDeLeitoDTO);

    default BloqueioDeLeito fromId(Long id) {
        if (id == null) {
            return null;
        }
        BloqueioDeLeito bloqueioDeLeito = new BloqueioDeLeito();
        bloqueioDeLeito.setId(id);
        return bloqueioDeLeito;
    }
}
