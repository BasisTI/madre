package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.UnidadeMedida;
import br.com.basis.suprimentos.service.dto.UnidadeMedidaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UnidadeMedidaMapper extends EntityMapper<UnidadeMedidaDTO, UnidadeMedida> {
    default UnidadeMedida fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeMedida unidadeMedida = new UnidadeMedida();
        unidadeMedida.setId(id);
        return unidadeMedida;
    }
}
