package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A HorarioLivre.
 */
@Entity
@Table(name = "horario_livre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-horariolivre")
public class HorarioLivre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqHorarioLivre")
    @SequenceGenerator(name = "seqHorarioLivre")
    private Long id;

    @NotNull
    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDate dataHoraInicio;

    @NotNull
    @Column(name = "data_hora_fim", nullable = false)
    private LocalDate dataHoraFim;

    @NotNull
    @Column(name = "ocupado", nullable = false)
    private Boolean ocupado;

    @ManyToOne
    @JsonIgnoreProperties(value = "horarioAgendadoLivres", allowSetters = true)
    private HorarioAgendado horarioAgendado;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataHoraInicio() {
        return dataHoraInicio;
    }

    public HorarioLivre dataHoraInicio(LocalDate dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
        return this;
    }

    public void setDataHoraInicio(LocalDate dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDate getDataHoraFim() {
        return dataHoraFim;
    }

    public HorarioLivre dataHoraFim(LocalDate dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
        return this;
    }

    public void setDataHoraFim(LocalDate dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public Boolean isOcupado() {
        return ocupado;
    }

    public HorarioLivre ocupado(Boolean ocupado) {
        this.ocupado = ocupado;
        return this;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public HorarioAgendado getHorarioAgendado() {
        return horarioAgendado;
    }

    public HorarioLivre horarioAgendado(HorarioAgendado horarioAgendado) {
        this.horarioAgendado = horarioAgendado;
        return this;
    }

    public void setHorarioAgendado(HorarioAgendado horarioAgendado) {
        this.horarioAgendado = horarioAgendado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioLivre)) {
            return false;
        }
        return id != null && id.equals(((HorarioLivre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioLivre{" +
            "id=" + getId() +
            ", dataHoraInicio='" + getDataHoraInicio() + "'" +
            ", dataHoraFim='" + getDataHoraFim() + "'" +
            ", ocupado='" + isOcupado() + "'" +
            "}";
    }
}
