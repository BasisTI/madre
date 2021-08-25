package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GradeDeAgendamento.
 */
@Entity
@Table(name = "grade_de_agendamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "gradedeagendamento")
public class GradeDeAgendamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGradeDeAgendamento")
    @SequenceGenerator(name = "seqGradeDeAgendamento")
    private Long id;

    @NotNull
    @Column(name = "unidade_executora_id", nullable = false)
    private Integer unidadeExecutoraId;

    @Column(name = "responsavel_id")
    private Integer responsavelId;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "gradeDeAgendamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<HorarioAgendado> gradeHorarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "gradeDeAgendamentos", allowSetters = true)
    private Exame exameGrade;

    @ManyToOne
    @JsonIgnoreProperties(value = "gradeDeAgendamentos", allowSetters = true)
    private GrupoAgendamentoExame grupoGrade;

    @ManyToOne
    @JsonIgnoreProperties(value = "gradeDeAgendamentos", allowSetters = true)
    private Sala salaGrade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public GradeDeAgendamento unidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
        return this;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    public Integer getResponsavelId() {
        return responsavelId;
    }

    public GradeDeAgendamento responsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
        return this;
    }

    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public GradeDeAgendamento ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<HorarioAgendado> getGradeHorarios() {
        return gradeHorarios;
    }

    public GradeDeAgendamento gradeHorarios(Set<HorarioAgendado> horarioAgendados) {
        this.gradeHorarios = horarioAgendados;
        return this;
    }

    public GradeDeAgendamento addGradeHorario(HorarioAgendado horarioAgendado) {
        this.gradeHorarios.add(horarioAgendado);
        horarioAgendado.setGradeDeAgendamento(this);
        return this;
    }

    public GradeDeAgendamento removeGradeHorario(HorarioAgendado horarioAgendado) {
        this.gradeHorarios.remove(horarioAgendado);
        horarioAgendado.setGradeDeAgendamento(null);
        return this;
    }

    public void setGradeHorarios(Set<HorarioAgendado> horarioAgendados) {
        this.gradeHorarios = horarioAgendados;
    }

    public Exame getExameGrade() {
        return exameGrade;
    }

    public GradeDeAgendamento exameGrade(Exame exame) {
        this.exameGrade = exame;
        return this;
    }

    public void setExameGrade(Exame exame) {
        this.exameGrade = exame;
    }

    public GrupoAgendamentoExame getGrupoGrade() {
        return grupoGrade;
    }

    public GradeDeAgendamento grupoGrade(GrupoAgendamentoExame grupoAgendamentoExame) {
        this.grupoGrade = grupoAgendamentoExame;
        return this;
    }

    public void setGrupoGrade(GrupoAgendamentoExame grupoAgendamentoExame) {
        this.grupoGrade = grupoAgendamentoExame;
    }

    public Sala getSalaGrade() {
        return salaGrade;
    }

    public GradeDeAgendamento salaGrade(Sala sala) {
        this.salaGrade = sala;
        return this;
    }

    public void setSalaGrade(Sala sala) {
        this.salaGrade = sala;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeDeAgendamento)) {
            return false;
        }
        return id != null && id.equals(((GradeDeAgendamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradeDeAgendamento{" +
            "id=" + getId() +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            ", responsavelId=" + getResponsavelId() +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
