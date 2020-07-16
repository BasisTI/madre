package br.com.basis.consulta.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
public class CondicaoDeAtendimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String sigla;

    @NotNull
    private String especialidade;
}
