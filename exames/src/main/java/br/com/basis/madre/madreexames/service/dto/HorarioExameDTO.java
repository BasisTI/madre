package br.com.basis.madre.madreexames.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.HorarioExame} entity.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioExameDTO)) {
            return false;
        }

        return id != null && id.equals(((HorarioExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioExameDTO{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", livre='" + isLivre() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", exclusivo='" + isExclusivo() + "'" +
            ", tipoDeMarcacaoId=" + getTipoDeMarcacaoId() +
            ", gradeAgendamentoExameId=" + getGradeAgendamentoExameId() +
            "}";
    }
}
