package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiberacaoDeLeitoDTO implements Serializable {
    @NotNull
    private Long leitoId;

    @NotNull
    private LocalDate dataDoLancamento;

    private String justificativa;
}
