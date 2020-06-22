package br.com.basis.consulta.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import br.com.basis.consulta.domain.enumeration.Turno;

import br.com.basis.consulta.domain.enumeration.TipoPagador;

/**
 * A Emergencia.
 */
@Entity
@Table(name = "emergencia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "emergencia")
public class Emergencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "numero_consulta")
    private Long numeroConsulta;

    @NotNull
    @Column(name = "data_hora_da_consulta", nullable = false)
    private ZonedDateTime dataHoraDaConsulta;

    @Max(value = 20L)
    @Column(name = "grade")
    private Long grade;

    @Size(max = 80)
    @Column(name = "profissional", length = 80)
    private String profissional;

    @Size(max = 20)
    @Column(name = "numero_sala", length = 20)
    private String numeroSala;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno")
    private Turno turno;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagador")
    private TipoPagador tipoPagador;

    @Column(name = "grades_disponiveis")
    private Boolean gradesDisponiveis;

    @Column(name = "clinica_central_id")
    private Long clinicaCentralId;

    @Size(max = 240)
    @Column(name = "justificativa", length = 240)
    private String justificativa;

    @Size(max = 240)
    @Column(name = "observacoes", length = 240)
    private String observacoes;

    @Column(name = "paciente_id")
    private Long pacienteId;

    @OneToOne
    @JoinColumn(unique = true)
    private CondicaoDeAtendimento condicaoDeAtendimento;

    @OneToOne
    @JoinColumn(unique = true)
    private FormaDeAgendamento formaDeAgendamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroConsulta() {
        return numeroConsulta;
    }

    public Emergencia numeroConsulta(Long numeroConsulta) {
        this.numeroConsulta = numeroConsulta;
        return this;
    }

    public void setNumeroConsulta(Long numeroConsulta) {
        this.numeroConsulta = numeroConsulta;
    }

    public ZonedDateTime getDataHoraDaConsulta() {
        return dataHoraDaConsulta;
    }

    public Emergencia dataHoraDaConsulta(ZonedDateTime dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
        return this;
    }

    public void setDataHoraDaConsulta(ZonedDateTime dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
    }

    public Long getGrade() {
        return grade;
    }

    public Emergencia grade(Long grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getProfissional() {
        return profissional;
    }

    public Emergencia profissional(String profissional) {
        this.profissional = profissional;
        return this;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getNumeroSala() {
        return numeroSala;
    }

    public Emergencia numeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
        return this;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public Turno getTurno() {
        return turno;
    }

    public Emergencia turno(Turno turno) {
        this.turno = turno;
        return this;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public TipoPagador getTipoPagador() {
        return tipoPagador;
    }

    public Emergencia tipoPagador(TipoPagador tipoPagador) {
        this.tipoPagador = tipoPagador;
        return this;
    }

    public void setTipoPagador(TipoPagador tipoPagador) {
        this.tipoPagador = tipoPagador;
    }

    public Boolean isGradesDisponiveis() {
        return gradesDisponiveis;
    }

    public Emergencia gradesDisponiveis(Boolean gradesDisponiveis) {
        this.gradesDisponiveis = gradesDisponiveis;
        return this;
    }

    public void setGradesDisponiveis(Boolean gradesDisponiveis) {
        this.gradesDisponiveis = gradesDisponiveis;
    }

    public Long getClinicaCentralId() {
        return clinicaCentralId;
    }

    public Emergencia clinicaCentralId(Long clinicaCentralId) {
        this.clinicaCentralId = clinicaCentralId;
        return this;
    }

    public void setClinicaCentralId(Long clinicaCentralId) {
        this.clinicaCentralId = clinicaCentralId;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public Emergencia justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Emergencia observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public Emergencia pacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
        return this;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public CondicaoDeAtendimento getCondicaoDeAtendimento() {
        return condicaoDeAtendimento;
    }

    public Emergencia condicaoDeAtendimento(CondicaoDeAtendimento condicaoDeAtendimento) {
        this.condicaoDeAtendimento = condicaoDeAtendimento;
        return this;
    }

    public void setCondicaoDeAtendimento(CondicaoDeAtendimento condicaoDeAtendimento) {
        this.condicaoDeAtendimento = condicaoDeAtendimento;
    }

    public FormaDeAgendamento getFormaDeAgendamento() {
        return formaDeAgendamento;
    }

    public Emergencia formaDeAgendamento(FormaDeAgendamento formaDeAgendamento) {
        this.formaDeAgendamento = formaDeAgendamento;
        return this;
    }

    public void setFormaDeAgendamento(FormaDeAgendamento formaDeAgendamento) {
        this.formaDeAgendamento = formaDeAgendamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emergencia)) {
            return false;
        }
        return id != null && id.equals(((Emergencia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Emergencia{" +
            "id=" + getId() +
            ", numeroConsulta=" + getNumeroConsulta() +
            ", dataHoraDaConsulta='" + getDataHoraDaConsulta() + "'" +
            ", grade=" + getGrade() +
            ", profissional='" + getProfissional() + "'" +
            ", numeroSala='" + getNumeroSala() + "'" +
            ", turno='" + getTurno() + "'" +
            ", tipoPagador='" + getTipoPagador() + "'" +
            ", gradesDisponiveis='" + isGradesDisponiveis() + "'" +
            ", clinicaCentralId=" + getClinicaCentralId() +
            ", justificativa='" + getJustificativa() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", pacienteId=" + getPacienteId() +
            "}";
    }
}
