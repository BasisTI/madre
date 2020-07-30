package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InclusaoSaldoEstoqueDTO implements Serializable {
    @NotNull(message = "Esse campo é obrigatório.")
    private Long almoxarifadoId;

    @NotNull(message = "Esse campo é obrigatório.")
    private Long materialId;

    @Min(value = 1, message = "O valor mínimo é 1")
    private Integer quantidade;

    @Min(value = 0, message = "O valor mínimo é 0")
    private Double valorTotal;
}
