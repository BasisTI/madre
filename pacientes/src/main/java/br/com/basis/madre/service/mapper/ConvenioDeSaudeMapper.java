package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.ConvenioDeSaudeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConvenioDeSaude} and its DTO {@link ConvenioDeSaudeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConvenioDeSaudeMapper extends EntityMapper<ConvenioDeSaudeDTO, ConvenioDeSaude> {
    default ConvenioDeSaude fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConvenioDeSaude convenioDeSaude = new ConvenioDeSaude();
        convenioDeSaude.setId(id);
        return convenioDeSaude;
    }
}
