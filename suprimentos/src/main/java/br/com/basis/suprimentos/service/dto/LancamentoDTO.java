package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class LancamentoDTO implements Serializable {
    private Long id;

    @NotNull
    private ZonedDateTime lancadoEm;

    @NotNull
    private String lancadoPor;

    private Long tipoLancamentoId;
}
