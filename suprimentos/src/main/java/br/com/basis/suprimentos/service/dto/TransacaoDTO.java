package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TransacaoDTO implements Serializable {
    private Long id;

    @NotNull
    private Long quantidadeItens;

    private Long tipoTransacaoId;

    private Long lancamentoId;

    private Long materialId;

    private Long almoxarifadoId;
}
