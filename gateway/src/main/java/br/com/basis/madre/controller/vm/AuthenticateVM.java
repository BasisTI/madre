package br.com.basis.madre.controller.vm;

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
public class AuthenticateVM {

    private String login;

    private String senha;

    private Boolean rememberMe;
    
}
