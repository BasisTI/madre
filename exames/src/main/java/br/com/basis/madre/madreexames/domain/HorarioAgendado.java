package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import br.com.basis.madre.madreexames.domain.enumeration.Dia;

/**
 * A HorarioAgendado.
 */
@Entity
@Table(name = "horario_agendado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "horarioagendado")
public class HorarioAgendado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqHorarioAgendado")
    @SequenceGenerator(name = "seqHorarioAgendado")
    private Long id;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @Column(name = "numero_de_horarios")
    private Integer numeroDeHorarios;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false)
    private Dia dia;

    @NotNull
    @Column(name = "duracao", nullable = false)
    private Duration duracao;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "exclusivo", nullable = false)
    private Boolean exclusivo;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoDeMarcacao horarioAgendado;

    @OneToMany(mappedBy = "horarioAgendado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<HorarioLivre> horarioAgendadoLivres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "gradeHorarios", allowSetters = true)
    private GradeDeAgendamento gradeDeAgendamento;

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

    public HorarioAgendado horaInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return horaFim;
    }

    public HorarioAgendado horaFim(Instant horaFim) {
        this.horaFim = horaFim;
        return this;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getNumeroDeHorarios() {
        return numeroDeHorarios;
    }

    public HorarioAgendado numeroDeHorarios(Integer numeroDeHorarios) {
        this.numeroDeHorarios = numeroDeHorarios;
        return this;
    }

    public void setNumeroDeHorarios(Integer numeroDeHorarios) {
        this.numeroDeHorarios = numeroDeHorarios;
    }

    public Dia getDia() {
        return dia;
    }

    public HorarioAgendado dia(Dia dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public HorarioAgendado duracao(Duration duracao) {
        this.duracao = duracao;
        return this;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public HorarioAgendado ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isExclusivo() {
        return exclusivo;
    }

    public HorarioAgendado exclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
        return this;
    }

    public void setExclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
    }

    public TipoDeMarcacao getHorarioAgendado() {
        return horarioAgendado;
    }

    public HorarioAgendado horarioAgendado(TipoDeMarcacao tipoDeMarcacao) {
        this.horarioAgendado = tipoDeMarcacao;
        return this;
    }

    public void setHorarioAgendado(TipoDeMarcacao tipoDeMarcacao) {
        this.horarioAgendado = tipoDeMarcacao;
    }

    public Set<HorarioLivre> getHorarioAgendadoLivres() {
        return horarioAgendadoLivres;
    }

    public HorarioAgendado horarioAgendadoLivres(Set<HorarioLivre> horarioLivres) {
        this.horarioAgendadoLivres = horarioLivres;
        return this;
    }

    public HorarioAgendado addHorarioAgendadoLivre(HorarioLivre horarioLivre) {
        this.horarioAgendadoLivres.add(horarioLivre);
        horarioLivre.setHorarioAgendado(this);
        return this;
    }

    public HorarioAgendado removeHorarioAgendadoLivre(HorarioLivre horarioLivre) {
        this.horarioAgendadoLivres.remove(horarioLivre);
        horarioLivre.setHorarioAgendado(null);
        return this;
    }

    public void setHorarioAgendadoLivres(Set<HorarioLivre> horarioLivres) {
        this.horarioAgendadoLivres = horarioLivres;
    }

    public GradeDeAgendamento getGradeDeAgendamento() {
        return gradeDeAgendamento;
    }

    public HorarioAgendado gradeDeAgendamento(GradeDeAgendamento gradeDeAgendamento) {
        this.gradeDeAgendamento = gradeDeAgendamento;
        return this;
    }

    public void setGradeDeAgendamento(GradeDeAgendamento gradeDeAgendamento) {
        this.gradeDeAgendamento = gradeDeAgendamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioAgendado)) {
            return false;
        }
        return id != null && id.equals(((HorarioAgendado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioAgendado{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", numeroDeHorarios=" + getNumeroDeHorarios() +
            ", dia='" + getDia() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", exclusivo='" + isExclusivo() + "'" +
            "}";
    }
}
