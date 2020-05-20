package br.com.basis.madre.farmacia.service.mapper;

import br.com.basis.madre.farmacia.domain.*;
import br.com.basis.madre.farmacia.service.dto.TipoMedicamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoMedicamento} and its DTO {@link TipoMedicamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoMedicamentoMapper extends EntityMapper<TipoMedicamentoDTO, TipoMedicamento> {



    default TipoMedicamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoMedicamento tipoMedicamento = new TipoMedicamento();
        tipoMedicamento.setId(id);
        return tipoMedicamento;
    }
}
