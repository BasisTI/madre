package br.com.basis.madre.seguranca.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Usuario} entity.
 */
@Getter
@Setter
public class MensagemDeLoginDTO implements Serializable {

    private String msgDeErro;

    private Boolean autenticado;

}
