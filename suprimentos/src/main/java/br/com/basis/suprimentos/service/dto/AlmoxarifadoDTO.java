package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AlmoxarifadoDTO implements Serializable {
    private Long id;

    @NotNull
    private Long codigo;

    @NotNull
    private String descricao;

    @NotNull
    private Integer diasEstoque;

    @NotNull
    private Boolean central;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean calculaMediaPonderada;

    @NotNull
    private Boolean bloqueiaEntradaTransferencia;

    private Long centroDeAtividadeId;

    private Long caracteristicaArmazenamentoId;
}
