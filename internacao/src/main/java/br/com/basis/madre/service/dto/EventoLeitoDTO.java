package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventoLeitoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataDoLancamento;

    private String justificativa;

    private Long tipoDoEventoId;

    private Long leitoId;

    private Long origemId;

    private Long tipoId;

    private Long motivoId;

}
