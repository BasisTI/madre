package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UnidadeMedidaDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(min = 1, max = 10)
    private String sigla;

    @NotNull
    @Size(max = 120)
    private String descricao;
}
