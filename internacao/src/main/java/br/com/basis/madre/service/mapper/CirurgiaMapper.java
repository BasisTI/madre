package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Cirurgia;
import br.com.basis.madre.service.dto.CirurgiaDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Cirurgia} and its DTO {@link CirurgiaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CirurgiaMapper extends EntityMapper<CirurgiaDTO, Cirurgia> {



    default Cirurgia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cirurgia cirurgia = new Cirurgia();
        cirurgia.setId(id);
        return cirurgia;
    }
}
