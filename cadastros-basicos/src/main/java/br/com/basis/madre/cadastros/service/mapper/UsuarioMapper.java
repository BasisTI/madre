package br.com.basis.madre.cadastros.service.mapper;

import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.service.dto.UsuarioDTO;
import org.mapstruct.Mapper;

import org.mapstruct.*;

/**
 * Mapper for the entity Usuario and its DTO UsuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {



    default Usuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
