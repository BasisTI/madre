package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ItemRequisicaoDTO implements Serializable {
    private Long id;

    @NotNull
    private Integer quantidade;

    private Long materialId;

    private Long almoxarifadoId;

    private Long requisicaoId;
}
