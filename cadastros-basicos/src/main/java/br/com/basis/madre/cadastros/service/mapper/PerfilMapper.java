package br.com.basis.madre.cadastros.service.mapper;

import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.service.dto.PerfilDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Perfil and its DTO PerfilDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PerfilMapper extends EntityMapper<PerfilDTO, Perfil> {

    default Perfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        Perfil perfil = new Perfil();
        perfil.setId(id);
        return perfil;
    }
}
