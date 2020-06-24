package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class EstoqueAlmoxarifadoDTO implements Serializable {
    private Long id;

    @NotNull
    private Boolean ativo;

    @Size(min = 1, max = 8)
    private String endereco;

    private Long quantidadeLimiteArmazenamento;

    @NotNull
    @Min(value = 1L)
    private Long quantidadeEstoqueMinimo;

    @NotNull
    @Min(value = 1L)
    private Long quantidadeEstoqueMaximo;

    @NotNull
    @Min(value = 1L)
    private Long quantidadePontoPedido;

    @NotNull
    @Min(value = 1)
    private Integer tempoReposicao;

    private Long almoxarifadoId;

    private Long materialId;

    private Long fornecedorId;

    private Long solicitacaoComprasId;
}
