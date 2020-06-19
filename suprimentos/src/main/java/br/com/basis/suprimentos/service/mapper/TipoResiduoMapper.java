package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.*;
import br.com.basis.suprimentos.service.dto.TipoResiduoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoResiduo} and its DTO {@link TipoResiduoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoResiduoMapper extends EntityMapper<TipoResiduoDTO, TipoResiduo> {



    default TipoResiduo fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoResiduo tipoResiduo = new TipoResiduo();
        tipoResiduo.setId(id);
        return tipoResiduo;
    }
}
