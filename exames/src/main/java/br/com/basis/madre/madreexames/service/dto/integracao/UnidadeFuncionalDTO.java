package br.com.basis.madre.madreexames.service.dto.integracao;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UnidadeFuncionalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;
}
