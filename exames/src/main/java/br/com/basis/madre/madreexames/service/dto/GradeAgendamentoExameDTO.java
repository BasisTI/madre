package br.com.basis.madre.madreexames.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.GradeAgendamentoExame} entity.
 */
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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getNumeroDeHorarios() {
        return numeroDeHorarios;
    }

    public void setNumeroDeHorarios(Integer numeroDeHorarios) {
        this.numeroDeHorarios = numeroDeHorarios;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    public Integer getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }

    public Long getExameId() {
        return exameId;
    }

    public void setExameId(Long exameId) {
        this.exameId = exameId;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeAgendamentoExameDTO)) {
            return false;
        }

        return id != null && id.equals(((GradeAgendamentoExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradeAgendamentoExameDTO{" +
            "id=" + getId() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", dia='" + getDia() + "'" +
            ", numeroDeHorarios=" + getNumeroDeHorarios() +
            ", duracao='" + getDuracao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            ", responsavelId=" + getResponsavelId() +
            ", exameId=" + getExameId() +
            ", salaId=" + getSalaId() +
            "}";
    }
}
