package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.LocalDeAtendimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocalDeAtendimento} and its DTO {@link LocalDeAtendimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocalDeAtendimentoMapper extends EntityMapper<LocalDeAtendimentoDTO, LocalDeAtendimento> {



    default LocalDeAtendimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalDeAtendimento localDeAtendimento = new LocalDeAtendimento();
        localDeAtendimento.setId(id);
        return localDeAtendimento;
    }
}
