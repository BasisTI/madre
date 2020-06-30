package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AlmoxarifadoDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 120)
    private String descricao;

    @Min(value = 0)
    @Max(value = 255)
    private Integer diasEstoque;

    private Boolean central;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean calculaMediaPonderada;

    private Boolean bloqueiaEntradaTransferencia;

    private Long centroDeAtividadeId;

    private Long caracteristicaArmazenamentoId;
}
