package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A HorarioExame.
 */
@Entity
@Table(name = "horario_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-horarioexame")
public class HorarioExame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqHorarioExame")
    @SequenceGenerator(name = "seqHorarioExame")
    private Long id;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @NotNull
    @Column(name = "livre", nullable = false)
    private Boolean livre;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "exclusivo", nullable = false)
    private Boolean exclusivo;

    @ManyToOne
    @JsonIgnoreProperties(value = "horarioMarcados", allowSetters = true)
    private TipoDeMarcacao tipoDeMarcacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "horarioDaGrades", allowSetters = true)
    private GradeAgendamentoExame gradeAgendamentoExame;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public HorarioExame horaInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return horaFim;
    }

    public HorarioExame horaFim(Instant horaFim) {
        this.horaFim = horaFim;
        return this;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Boolean isLivre() {
        return livre;
    }

    public HorarioExame livre(Boolean livre) {
        this.livre = livre;
        return this;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public HorarioExame ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isExclusivo() {
        return exclusivo;
    }

    public HorarioExame exclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
        return this;
    }

    public void setExclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
    }

    public TipoDeMarcacao getTipoDeMarcacao() {
        return tipoDeMarcacao;
    }

    public HorarioExame tipoDeMarcacao(TipoDeMarcacao tipoDeMarcacao) {
        this.tipoDeMarcacao = tipoDeMarcacao;
        return this;
    }

    public void setTipoDeMarcacao(TipoDeMarcacao tipoDeMarcacao) {
        this.tipoDeMarcacao = tipoDeMarcacao;
    }

    public GradeAgendamentoExame getGradeAgendamentoExame() {
        return gradeAgendamentoExame;
    }

    public HorarioExame gradeAgendamentoExame(GradeAgendamentoExame gradeAgendamentoExame) {
        this.gradeAgendamentoExame = gradeAgendamentoExame;
        return this;
    }

    public void setGradeAgendamentoExame(GradeAgendamentoExame gradeAgendamentoExame) {
        this.gradeAgendamentoExame = gradeAgendamentoExame;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioExame)) {
            return false;
        }
        return id != null && id.equals(((HorarioExame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioExame{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", livre='" + isLivre() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", exclusivo='" + isExclusivo() + "'" +
            "}";
    }
}
