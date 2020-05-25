package br.com.basis.madre.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class LiberacaoDeLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long leitoId;

    @NotNull
    private ZonedDateTime dataDoLancamento;

    private String justificativa;
}
