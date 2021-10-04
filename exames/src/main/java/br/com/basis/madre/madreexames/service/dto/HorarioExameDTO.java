package br.com.basis.madre.madreexames.service.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.HorarioExame} entity.
 */
@EqualsAndHashCode
@ToString
public class HorarioExameDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant horaInicio;

    private Instant horaFim;

    @NotNull
    private Boolean livre;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean exclusivo;


    private Long tipoDeMarcacaoId;

    private Long gradeAgendamentoExameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Boolean isLivre() {
        return livre;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isExclusivo() {
        return exclusivo;
    }

    public void setExclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
    }

    public Long getTipoDeMarcacaoId() {
        return tipoDeMarcacaoId;
    }

    public void setTipoDeMarcacaoId(Long tipoDeMarcacaoId) {
        this.tipoDeMarcacaoId = tipoDeMarcacaoId;
    }

    public Long getGradeAgendamentoExameId() {
        return gradeAgendamentoExameId;
    }

    public void setGradeAgendamentoExameId(Long gradeAgendamentoExameId) {
        this.gradeAgendamentoExameId = gradeAgendamentoExameId;
    }

    @AssertTrue(message = "Hora fim deve ser depois de hora início")
    private boolean isHoraInicioAntesDeHoraFim() {
        return getHoraInicio().isBefore(getHoraFim());
    }

}
