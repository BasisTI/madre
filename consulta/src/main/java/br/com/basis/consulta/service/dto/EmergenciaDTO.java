package br.com.basis.consulta.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.consulta.domain.enumeration.Turno;
import br.com.basis.consulta.domain.enumeration.Pagador;

/**
 * A DTO for the {@link br.com.basis.consulta.domain.Emergencia} entity.
 */
public class EmergenciaDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataHoraDaConsulta;

    @Size(max = 20)
    private String diaDaSemana;

    @Max(value = 20L)
    private Long grade;

    @Size(max = 80)
    private String profissional;

    @Size(max = 20)
    private String sala;

    private Turno turno;

    private Pagador pagador;

    private Boolean gradesDisponiveis;

    @Max(value = 100L)
    private Long central;

    @Size(max = 240)
    private String observacoes;


    private Long condicaoDeAtendimentoId;

    private Long formaDeAgendamentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataHoraDaConsulta() {
        return dataHoraDaConsulta;
    }

    public void setDataHoraDaConsulta(LocalDate dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Pagador getPagador() {
        return pagador;
    }

    public void setPagador(Pagador pagador) {
        this.pagador = pagador;
    }

    public Boolean isGradesDisponiveis() {
        return gradesDisponiveis;
    }

    public void setGradesDisponiveis(Boolean gradesDisponiveis) {
        this.gradesDisponiveis = gradesDisponiveis;
    }

    public Long getCentral() {
        return central;
    }

    public void setCentral(Long central) {
        this.central = central;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getCondicaoDeAtendimentoId() {
        return condicaoDeAtendimentoId;
    }

    public void setCondicaoDeAtendimentoId(Long condicaoDeAtendimentoId) {
        this.condicaoDeAtendimentoId = condicaoDeAtendimentoId;
    }

    public Long getFormaDeAgendamentoId() {
        return formaDeAgendamentoId;
    }

    public void setFormaDeAgendamentoId(Long formaDeAgendamentoId) {
        this.formaDeAgendamentoId = formaDeAgendamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmergenciaDTO emergenciaDTO = (EmergenciaDTO) o;
        if (emergenciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emergenciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmergenciaDTO{" +
            "id=" + getId() +
            ", dataHoraDaConsulta='" + getDataHoraDaConsulta() + "'" +
            ", diaDaSemana='" + getDiaDaSemana() + "'" +
            ", grade=" + getGrade() +
            ", profissional='" + getProfissional() + "'" +
            ", sala='" + getSala() + "'" +
            ", turno='" + getTurno() + "'" +
            ", pagador='" + getPagador() + "'" +
            ", gradesDisponiveis='" + isGradesDisponiveis() + "'" +
            ", central=" + getCentral() +
            ", observacoes='" + getObservacoes() + "'" +
            ", condicaoDeAtendimento=" + getCondicaoDeAtendimentoId() +
            ", formaDeAgendamento=" + getFormaDeAgendamentoId() +
            "}";
    }
}
