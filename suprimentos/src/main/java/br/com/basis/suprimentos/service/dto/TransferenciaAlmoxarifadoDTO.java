package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TransferenciaAlmoxarifadoDTO implements Serializable {
    private Long id;

    @NotNull
    private Boolean ativo;

    private Long origemId;

    private Long destinoId;
}
