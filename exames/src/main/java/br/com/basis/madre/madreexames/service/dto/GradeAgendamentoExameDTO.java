package br.com.basis.madre.madreexames.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.GradeAgendamentoExame} entity.
 */
@Data
public class GradeAgendamentoExameDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    @NotNull
    private Instant horaInicio;

    private Instant horaFim;

    @NotNull
    private String dia;

    @Min(1)
    private Integer numeroDeHorarios;

    @NotNull
    private Duration duracao;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Integer unidadeExecutoraId;

    @NotNull
    private Integer responsavelId;


    private Long exameId;

    private Long salaId;

    @AssertTrue(message = "Hora fim deve ser depois de hora início")
    private boolean isHoraInicioAntesDeHoraFim() {
        return getHoraInicio().isBefore(getHoraFim());
    }

    @AssertTrue(message = "Data fim deve ser depois de data início")
    private boolean isDataInicioAntesDeDataFim() {
        return getDataInicio().isBefore(getDataFim());
    }

}
