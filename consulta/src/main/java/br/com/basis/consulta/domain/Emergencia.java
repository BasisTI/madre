package br.com.basis.consulta.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import br.com.basis.consulta.domain.enumeration.Turno;

import br.com.basis.consulta.domain.enumeration.Pagador;

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

    @NotNull
    @Column(name = "data_hora_da_consulta", nullable = false)
    private LocalDate dataHoraDaConsulta;

    @Size(max = 20)
    @Column(name = "dia_da_semana", length = 20)
    private String diaDaSemana;

    @Max(value = 20L)
    @Column(name = "grade")
    private Long grade;

    @Size(max = 80)
    @Column(name = "profissional", length = 80)
    private String profissional;

    @Size(max = 20)
    @Column(name = "sala", length = 20)
    private String sala;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno")
    private Turno turno;

    @Enumerated(EnumType.STRING)
    @Column(name = "pagador")
    private Pagador pagador;

    @Column(name = "grades_disponiveis")
    private Boolean gradesDisponiveis;

    @Max(value = 100L)
    @Column(name = "central")
    private Long central;

    @Size(max = 240)
    @Column(name = "observacoes", length = 240)
    private String observacoes;

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

    public LocalDate getDataHoraDaConsulta() {
        return dataHoraDaConsulta;
    }

    public Emergencia dataHoraDaConsulta(LocalDate dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
        return this;
    }

    public void setDataHoraDaConsulta(LocalDate dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public Emergencia diaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
        return this;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
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

    public String getSala() {
        return sala;
    }

    public Emergencia sala(String sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(String sala) {
        this.sala = sala;
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

    public Pagador getPagador() {
        return pagador;
    }

    public Emergencia pagador(Pagador pagador) {
        this.pagador = pagador;
        return this;
    }

    public void setPagador(Pagador pagador) {
        this.pagador = pagador;
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

    public Long getCentral() {
        return central;
    }

    public Emergencia central(Long central) {
        this.central = central;
        return this;
    }

    public void setCentral(Long central) {
        this.central = central;
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
            "}";
    }
}
