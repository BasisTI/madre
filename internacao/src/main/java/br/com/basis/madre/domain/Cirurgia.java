package br.com.basis.madre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Cirurgia.
 */
@Entity
@Table(name = "cirurgia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cirurgia")
public class Cirurgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "tempo_max")
    private Instant tempoMax;

    @Column(name = "tempo_min")
    private Instant tempoMin;

    @Column(name = "limite_dias")
    private Integer limiteDias;

    @Column(name = "limte_dias_convenios")
    private Integer limteDiasConvenios;

    @Column(name = "intervalocirurgia")
    private Integer intervalocirurgia;

    @Column(name = "intervalo_procedimento")
    private Integer intervaloProcedimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTempoMax() {
        return tempoMax;
    }

    public Cirurgia tempoMax(Instant tempoMax) {
        this.tempoMax = tempoMax;
        return this;
    }

    public void setTempoMax(Instant tempoMax) {
        this.tempoMax = tempoMax;
    }

    public Instant getTempoMin() {
        return tempoMin;
    }

    public Cirurgia tempoMin(Instant tempoMin) {
        this.tempoMin = tempoMin;
        return this;
    }

    public void setTempoMin(Instant tempoMin) {
        this.tempoMin = tempoMin;
    }

    public Integer getLimiteDias() {
        return limiteDias;
    }

    public Cirurgia limiteDias(Integer limiteDias) {
        this.limiteDias = limiteDias;
        return this;
    }

    public void setLimiteDias(Integer limiteDias) {
        this.limiteDias = limiteDias;
    }

    public Integer getLimteDiasConvenios() {
        return limteDiasConvenios;
    }

    public Cirurgia limteDiasConvenios(Integer limteDiasConvenios) {
        this.limteDiasConvenios = limteDiasConvenios;
        return this;
    }

    public void setLimteDiasConvenios(Integer limteDiasConvenios) {
        this.limteDiasConvenios = limteDiasConvenios;
    }

    public Integer getIntervalocirurgia() {
        return intervalocirurgia;
    }

    public Cirurgia intervalocirurgia(Integer intervalocirurgia) {
        this.intervalocirurgia = intervalocirurgia;
        return this;
    }

    public void setIntervalocirurgia(Integer intervalocirurgia) {
        this.intervalocirurgia = intervalocirurgia;
    }

    public Integer getIntervaloProcedimento() {
        return intervaloProcedimento;
    }

    public Cirurgia intervaloProcedimento(Integer intervaloProcedimento) {
        this.intervaloProcedimento = intervaloProcedimento;
        return this;
    }

    public void setIntervaloProcedimento(Integer intervaloProcedimento) {
        this.intervaloProcedimento = intervaloProcedimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cirurgia)) {
            return false;
        }
        return id != null && id.equals(((Cirurgia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cirurgia{" +
            "id=" + getId() +
            ", tempoMax='" + getTempoMax() + "'" +
            ", tempoMin='" + getTempoMin() + "'" +
            ", limiteDias=" + getLimiteDias() +
            ", limteDiasConvenios=" + getLimteDiasConvenios() +
            ", intervalocirurgia=" + getIntervalocirurgia() +
            ", intervaloProcedimento=" + getIntervaloProcedimento() +
            "}";
    }
}
