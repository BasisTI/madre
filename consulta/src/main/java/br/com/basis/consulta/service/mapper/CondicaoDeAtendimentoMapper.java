package br.com.basis.consulta.service.mapper;

import br.com.basis.consulta.domain.*;
import br.com.basis.consulta.service.dto.CondicaoDeAtendimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CondicaoDeAtendimento} and its DTO {@link CondicaoDeAtendimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CondicaoDeAtendimentoMapper extends EntityMapper<CondicaoDeAtendimentoDTO, CondicaoDeAtendimento> {


    @Mapping(target = "emergencia", ignore = true)
    CondicaoDeAtendimento toEntity(CondicaoDeAtendimentoDTO condicaoDeAtendimentoDTO);

    default CondicaoDeAtendimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        CondicaoDeAtendimento condicaoDeAtendimento = new CondicaoDeAtendimento();
        condicaoDeAtendimento.setId(id);
        return condicaoDeAtendimento;
    }
}
