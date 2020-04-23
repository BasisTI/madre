package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.ProcedimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Procedimento and its DTO ProcedimentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcedimentoMapper extends EntityMapper<ProcedimentoDTO, Procedimento> {



    default Procedimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Procedimento procedimento = new Procedimento();
        procedimento.setId(id);
        return procedimento;
    }
}
