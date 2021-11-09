package br.com.basis.madre.dto;

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
    
}
