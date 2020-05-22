package br.com.basis.madre.prescricao.service.mapper;

import br.com.basis.madre.prescricao.domain.*;
import br.com.basis.madre.prescricao.service.dto.UnidadeDoseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadeDose} and its DTO {@link UnidadeDoseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadeDoseMapper extends EntityMapper<UnidadeDoseDTO, UnidadeDose> {


    UnidadeDose toEntity(UnidadeDoseDTO unidadeDoseDTO);

    default UnidadeDose fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeDose unidadeDose = new UnidadeDose();
        unidadeDose.setId(id);
        return unidadeDose;
    }
}
