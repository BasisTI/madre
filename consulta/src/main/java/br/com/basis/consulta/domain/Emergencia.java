package br.com.basis.consulta.domain;

import br.com.basis.consulta.domain.enumeration.TipoPagador;
import br.com.basis.consulta.domain.enumeration.Turno;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Emergencia.
 */
@Entity
@Data
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
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private ZonedDateTime dataHoraDaConsulta;

    @Max(value = 20L)
    @Column(name = "grade")
    private Long grade;


    @Column(name = "profissional" , length = 240)
    private String profissional;

    @Column(name = "especialidade", length = 240)
    private String especialidade;

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
    public Emergencia numeroConsulta(Long numeroConsulta) {
        this.numeroConsulta = numeroConsulta;
        return this;
    }
    public Emergencia dataHoraDaConsulta(ZonedDateTime dataHoraDaConsulta) {
        this.dataHoraDaConsulta = dataHoraDaConsulta;
        return this;
    }
    public Emergencia grade(Long grade) {
        this.grade = grade;
        return this;
    }

    public Emergencia profissional(  String profissional){
        this.profissional = profissional;
        return  this;
    }

    public Emergencia especialidade(String especialidade){
        this.especialidade = especialidade;
        return this;
    }

    public Emergencia numeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
        return this;
    }
    public Emergencia turno(Turno turno) {
        this.turno = turno;
        return this;
    }
    public Emergencia tipoPagador(TipoPagador tipoPagador) {
        this.tipoPagador = tipoPagador;
        return this;
    }
    public Boolean isGradesDisponiveis() {
        return gradesDisponiveis;
    }

    public Emergencia gradesDisponiveis(Boolean gradesDisponiveis) {
        this.gradesDisponiveis = gradesDisponiveis;
        return this;
    }
    public Emergencia clinicaCentralId(Long clinicaCentralId) {
        this.clinicaCentralId = clinicaCentralId;
        return this;
    }
    public Emergencia justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }
    public Emergencia observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }
    public Emergencia pacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
        return this;
    }
    public Emergencia condicaoDeAtendimento(CondicaoDeAtendimento condicaoDeAtendimento) {
        this.condicaoDeAtendimento = condicaoDeAtendimento;
        return this;
    }
    public Emergencia formaDeAgendamento(FormaDeAgendamento formaDeAgendamento) {
        this.formaDeAgendamento = formaDeAgendamento;
        return this;
    }
}
