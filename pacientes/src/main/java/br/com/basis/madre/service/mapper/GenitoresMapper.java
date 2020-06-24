package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Genitores;
import br.com.basis.madre.service.dto.GenitoresDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Genitores} and its DTO {@link GenitoresDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenitoresMapper extends EntityMapper<GenitoresDTO, Genitores> {



    default Genitores fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genitores genitores = new Genitores();
        genitores.setId(id);
        return genitores;
    }
}
