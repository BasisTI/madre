package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.UnidadeFuncional;
import br.com.basis.madre.service.dto.UnidadeFuncionalDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UnidadeFuncionalMapper extends
    EntityMapper<UnidadeFuncionalDTO, UnidadeFuncional> {


    default UnidadeFuncional fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeFuncional unidadeFuncional = new UnidadeFuncional();
        unidadeFuncional.setId(id);
        return unidadeFuncional;
    }
}
