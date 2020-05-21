package br.com.basis.madre.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class BloqueioDeLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dataDoLancamento;

    private String justificativa;

    private ZonedDateTime dataInicio;

    private ZonedDateTime dataFim;

    private Long leitoId;

    private Long motivoId;
}
