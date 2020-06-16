package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.MotivoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Motivo} and its DTO {@link MotivoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MotivoMapper extends EntityMapper<MotivoDTO, Motivo> {



    default Motivo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Motivo motivo = new Motivo();
        motivo.setId(id);
        return motivo;
    }
}
