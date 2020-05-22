package br.com.basis.madre.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Integer ala;

    @NotNull
    private Integer andar;

    private Long unidadeFuncionalId;

}
