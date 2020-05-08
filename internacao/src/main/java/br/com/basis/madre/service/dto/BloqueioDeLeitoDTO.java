package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BloqueioDeLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataDoLancamento;

    private String justificativa;


    private Long leitoId;

    private Long motivoId;
}
