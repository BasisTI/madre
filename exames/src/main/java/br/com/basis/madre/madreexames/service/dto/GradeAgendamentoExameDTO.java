package br.com.basis.madre.madreexames.service.dto;

import br.com.basis.madre.madreexames.domain.Dia;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
@NoArgsConstructor
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-gradeagendamentoexame")
public class GradeAgendamentoExameDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    @NotNull
    private Instant horaInicio;

    @NotNull
    private Instant horaFim;

    @Min(1)
    private Integer numeroDeHorarios;

    @NotNull
    private Duration duracao;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Integer unidadeExecutoraId;

    private String unidadeNome;

    @NotNull
    private Integer responsavelId;

    private String responsavelNome;

    private Set<Dia> dias = new HashSet<>();

    private Long exameId;

    private String exameNome;

    private Long salaId;

    private String salaNome;

    public GradeAgendamentoExameDTO(Long id, Integer unidadeExecutoraId, Integer responsavelId, String exameNome, String salaNome) {
        this.id = id;
        this.unidadeExecutoraId = unidadeExecutoraId;
        this.responsavelId = responsavelId;
        this.exameNome = exameNome;
        this.salaNome = salaNome;
    }

    @AssertTrue(message = "Hora fim deve ser depois de hora início")
    private boolean isHoraInicioAntesDeHoraFim() {
        return getHoraInicio().isBefore(getHoraFim());
    }

    @AssertTrue(message = "Data fim deve ser depois de data início")
    private boolean isDataInicioAntesDeDataFim() {
        return getDataInicio().isBefore(getDataFim());
    }

}
