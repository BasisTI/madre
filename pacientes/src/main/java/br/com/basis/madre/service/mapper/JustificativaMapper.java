package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Justificativa;
import br.com.basis.madre.service.dto.JustificativaDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Justificativa} and its DTO {@link JustificativaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JustificativaMapper extends EntityMapper<JustificativaDTO, Justificativa> {



    default Justificativa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Justificativa justificativa = new Justificativa();
        justificativa.setId(id);
        return justificativa;
    }
}
