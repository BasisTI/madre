package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * A GradeAgendamentoExame.
 */
@Entity
@Table(name = "grade_agendamento_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "gradeagendamentoexame")
public class GradeAgendamentoExame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGradeAgendamentoExame")
    @SequenceGenerator(name = "seqGradeAgendamentoExame")
    private Long id;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @Column(name = "numero_de_horarios")
    private Integer numeroDeHorarios;

    @NotNull
    @Column(name = "duracao", nullable = false)
    private Duration duracao;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "unidade_executora_id", nullable = false)
    private Integer unidadeExecutoraId;

    @NotNull
    @Column(name = "responsavel_id", nullable = false)
    private Integer responsavelId;

    @OneToMany(mappedBy = "gradeAgendamentoExame")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<HorarioExame> horarioDaGrades = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "grade_agendamento_exame_dia",
               joinColumns = @JoinColumn(name = "grade_agendamento_exame_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dia_id", referencedColumnName = "id"))
    private Set<Dia> dias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "gradeDoExames", allowSetters = true)
    private Exame exame;

    @ManyToOne
    @JsonIgnoreProperties(value = "gradeDaSalas", allowSetters = true)
    private Sala sala;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public GradeAgendamentoExame dataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public GradeAgendamentoExame dataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public GradeAgendamentoExame horaInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return horaFim;
    }

    public GradeAgendamentoExame horaFim(Instant horaFim) {
        this.horaFim = horaFim;
        return this;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getNumeroDeHorarios() {
        return numeroDeHorarios;
    }

    public GradeAgendamentoExame numeroDeHorarios(Integer numeroDeHorarios) {
        this.numeroDeHorarios = numeroDeHorarios;
        return this;
    }

    public void setNumeroDeHorarios(Integer numeroDeHorarios) {
        this.numeroDeHorarios = numeroDeHorarios;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public GradeAgendamentoExame duracao(Duration duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public GradeAgendamentoExame ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public GradeAgendamentoExame unidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
        return this;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    public Integer getResponsavelId() {
        return responsavelId;
    }

    public GradeAgendamentoExame responsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
        return this;
    }

    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }

    public Set<HorarioExame> getHorarioDaGrades() {
        return horarioDaGrades;
    }

    public GradeAgendamentoExame horarioDaGrades(Set<HorarioExame> horarioExames) {
        this.horarioDaGrades = horarioExames;
        return this;
    }

    public GradeAgendamentoExame addHorarioDaGrade(HorarioExame horarioExame) {
        this.horarioDaGrades.add(horarioExame);
        horarioExame.setGradeAgendamentoExame(this);
        return this;
    }

    public GradeAgendamentoExame removeHorarioDaGrade(HorarioExame horarioExame) {
        this.horarioDaGrades.remove(horarioExame);
        horarioExame.setGradeAgendamentoExame(null);
        return this;
    }

    public void setHorarioDaGrades(Set<HorarioExame> horarioExames) {
        this.horarioDaGrades = horarioExames;
    }

    public Set<Dia> getDias() {
        return dias;
    }

    public GradeAgendamentoExame dias(Set<Dia> dias) {
        this.dias = dias;
        return this;
    }

    public GradeAgendamentoExame addDia(Dia dia) {
        this.dias.add(dia);
        dia.getGradeAgendamentoExames().add(this);
        return this;
    }

    public GradeAgendamentoExame removeDia(Dia dia) {
        this.dias.remove(dia);
        dia.getGradeAgendamentoExames().remove(this);
        return this;
    }

    public void setDias(Set<Dia> dias) {
        this.dias = dias;
    }

    public Exame getExame() {
        return exame;
    }

    public GradeAgendamentoExame exame(Exame exame) {
        this.exame = exame;
        return this;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Sala getSala() {
        return sala;
    }

    public GradeAgendamentoExame sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeAgendamentoExame)) {
            return false;
        }
        return id != null && id.equals(((GradeAgendamentoExame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradeAgendamentoExame{" +
            "id=" + getId() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", numeroDeHorarios=" + getNumeroDeHorarios() +
            ", duracao='" + getDuracao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            ", responsavelId=" + getResponsavelId() +
            "}";
    }
}
