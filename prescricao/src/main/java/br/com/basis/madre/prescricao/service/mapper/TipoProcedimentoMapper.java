package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.TipoProcedimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoProcedimento} and its DTO {@link TipoProcedimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoProcedimentoMapper extends EntityMapper<TipoProcedimentoDTO, TipoProcedimento> {



    default TipoProcedimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoProcedimento tipoProcedimento = new TipoProcedimento();
        tipoProcedimento.setId(id);
        return tipoProcedimento;
    }
}
