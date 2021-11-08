package br.com.basis.madre.seguranca.dto;

import br.com.basis.madre.seguranca.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInternalDTO {

    private Long id;

    private Integer codigo;

    private String login;

    private String email;

    private Boolean ativo;

    private String senha;

    public static UserInternalDTO from(Usuario usuario){

        return UserInternalDTO.builder()
            .id(usuario.getId())
            .codigo(usuario.getCodigo())
            .login(usuario.getLogin())
            .email(usuario.getEmail())
            .ativo(usuario.isAtivo())
            .senha(usuario.getSenha())
            .build();
    }
    
}
