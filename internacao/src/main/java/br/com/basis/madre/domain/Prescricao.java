package br.com.basis.madre.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import br.com.basis.madre.domain.enumeration.UnidadeTempo;

/**
 * A Prescricao.
 */
@Entity
@Table(name = "prescricao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prescricao")
public class Prescricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "horario_validade")
    private Instant horarioValidade;

    @Column(name = "tempo_adiantamento")
    private Integer tempoAdiantamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_tempo")
    private UnidadeTempo unidadeTempo;

    @Column(name = "numero_vias")
    private Integer numeroVias;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHorarioValidade() {
        return horarioValidade;
    }

    public Prescricao horarioValidade(Instant horarioValidade) {
        this.horarioValidade = horarioValidade;
        return this;
    }

    public void setHorarioValidade(Instant horarioValidade) {
        this.horarioValidade = horarioValidade;
    }

    public Integer getTempoAdiantamento() {
        return tempoAdiantamento;
    }

    public Prescricao tempoAdiantamento(Integer tempoAdiantamento) {
        this.tempoAdiantamento = tempoAdiantamento;
        return this;
    }

    public void setTempoAdiantamento(Integer tempoAdiantamento) {
        this.tempoAdiantamento = tempoAdiantamento;
    }

    public UnidadeTempo getUnidadeTempo() {
        return unidadeTempo;
    }

    public Prescricao unidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
        return this;
    }

    public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
    }

    public Integer getNumeroVias() {
        return numeroVias;
    }

    public Prescricao numeroVias(Integer numeroVias) {
        this.numeroVias = numeroVias;
        return this;
    }

    public void setNumeroVias(Integer numeroVias) {
        this.numeroVias = numeroVias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prescricao)) {
            return false;
        }
        return id != null && id.equals(((Prescricao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prescricao{" +
            "id=" + getId() +
            ", horarioValidade='" + getHorarioValidade() + "'" +
            ", tempoAdiantamento=" + getTempoAdiantamento() +
            ", unidadeTempo='" + getUnidadeTempo() + "'" +
            ", numeroVias=" + getNumeroVias() +
            "}";
    }
}
