package br.com.basis.madre.cadastros.service.mapper;

import org.mapstruct.Mapper;

import br.com.basis.madre.cadastros.domain.Usuario;
import br.com.basis.madre.cadastros.service.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
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
