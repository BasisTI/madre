package br.com.basis.madre.madreexames.service.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    private Set<DiaDTO> dias = new HashSet<>();

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
