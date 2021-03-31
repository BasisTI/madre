package br.com.basis.consulta.service.dto;

import br.com.basis.consulta.domain.enumeration.TipoPagador;
import br.com.basis.consulta.domain.enumeration.Turno;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.consulta.domain.Emergencia} entity.
 */
@NoArgsConstructor
public class EmergenciaDTO implements Serializable {

    private Long id;

    private Long numeroConsulta;

    @NotNull
    private ZonedDateTime dataHoraDaConsulta;

    @Max(value = 20L)
    private Long grade;

    private String profissional;

    private String especialidade;

    @Size(max = 20)
    private String numeroSala;

    private Turno turno;

    private TipoPagador tipoPagador;

    private Boolean gradesDisponiveis;

    private Long clinicaCentralId;

    @Size(max = 240)
    private String justificativa;

    @Size(max = 240)
    private String observacoes;

    private Long pacienteId;


    private Long condicaoDeAtendimentoId;

    private Long formaDeAgendamentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroConsulta() {
        return numeroConsulta;
    }

    public void setNumeroConsulta(Long numeroConsulta) {
        this.numeroConsulta = numeroConsulta;
    }

    public ZonedDateTime getDataHoraDaConsulta() {
        return dataHoraDaConsulta;
    }

    public void setDataHoraDaConsulta(ZonedDateTime dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getProfissional() { return profissional; }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }


        public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }


    public String getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public TipoPagador getTipoPagador() {
        return tipoPagador;
    }

    public void setTipoPagador(TipoPagador tipoPagador) {
        this.tipoPagador = tipoPagador;
    }

    public Boolean isGradesDisponiveis() {
        return gradesDisponiveis;
    }

    public void setGradesDisponiveis(Boolean gradesDisponiveis) {
        this.gradesDisponiveis = gradesDisponiveis;
    }

    public Long getClinicaCentralId() {
        return clinicaCentralId;
    }

    public void setClinicaCentralId(Long clinicaCentralId) {
        this.clinicaCentralId = clinicaCentralId;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
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
            ", numeroConsulta=" + getNumeroConsulta() +
            ", dataHoraDaConsulta='" + getDataHoraDaConsulta() + "'" +
            ", grade=" + getGrade() +
            ", especialidade=" + getEspecialidade() +
            ", profissional=" + getProfissional()+
            ", numeroSala='" + getNumeroSala() + "'" +
            ", turno='" + getTurno() + "'" +
            ", tipoPagador='" + getTipoPagador() + "'" +
            ", gradesDisponiveis='" + isGradesDisponiveis() + "'" +
            ", clinicaCentralId=" + getClinicaCentralId() +
            ", justificativa='" + getJustificativa() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", pacienteId=" + getPacienteId() +
            ", condicaoDeAtendimento=" + getCondicaoDeAtendimentoId() +
            ", formaDeAgendamento=" + getFormaDeAgendamentoId() +
            "}";
    }
}
