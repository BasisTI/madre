package br.com.basis.madre.seguranca.controller.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.basis.madre.seguranca.domain.Usuario;
import br.com.basis.madre.seguranca.repository.UsuarioRepository;

@RestController
@RequestMapping("/internal-api/users")
public class UserInternalController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/getUserDetails")
    public UserDetails loadUserByUsername(String login){

        Usuario usuario = usuarioRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return User.builder()
            .username(usuario.getLogin())
            .password(usuario.getSenha())
            .roles("USER")
            .build();

    }

}
