package br.com.basis.madre.seguranca.controller.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.basis.madre.seguranca.domain.Usuario;
import br.com.basis.madre.seguranca.dto.UserInternalDTO;
import br.com.basis.madre.seguranca.repository.UsuarioRepository;

@RestController
@RequestMapping("/internal-api/user-details")
public class UserInternalController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping()
    public UserInternalDTO loadUserByUsername(@RequestBody UserInternalDTO userInternalDTO){

        Usuario usuario = usuarioRepository.findByLogin(userInternalDTO.getLogin())
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return UserInternalDTO.builder()
            .login(usuario.getLogin())
            .senha(usuario.getSenha())
            .build();

    }

}
