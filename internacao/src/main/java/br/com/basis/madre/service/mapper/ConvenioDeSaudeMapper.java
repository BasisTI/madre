package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.ConvenioDeSaude;
import br.com.basis.madre.service.dto.ConvenioDeSaudeDTO;
import org.mapstruct.Mapper;

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
