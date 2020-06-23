package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Cirurgia} entity.
 */
public class CirurgiaDTO implements Serializable {

    private Long id;

    private Instant tempoMax;

    private Instant tempoMin;

    private Integer limiteDias;

    private Integer limteDiasConvenios;

    private Integer intervalocirurgia;

    private Integer intervaloProcedimento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTempoMax() {
        return tempoMax;
    }

    public void setTempoMax(Instant tempoMax) {
        this.tempoMax = tempoMax;
    }

    public Instant getTempoMin() {
        return tempoMin;
    }

    public void setTempoMin(Instant tempoMin) {
        this.tempoMin = tempoMin;
    }

    public Integer getLimiteDias() {
        return limiteDias;
    }

    public void setLimiteDias(Integer limiteDias) {
        this.limiteDias = limiteDias;
    }

    public Integer getLimteDiasConvenios() {
        return limteDiasConvenios;
    }

    public void setLimteDiasConvenios(Integer limteDiasConvenios) {
        this.limteDiasConvenios = limteDiasConvenios;
    }

    public Integer getIntervalocirurgia() {
        return intervalocirurgia;
    }

    public void setIntervalocirurgia(Integer intervalocirurgia) {
        this.intervalocirurgia = intervalocirurgia;
    }

    public Integer getIntervaloProcedimento() {
        return intervaloProcedimento;
    }

    public void setIntervaloProcedimento(Integer intervaloProcedimento) {
        this.intervaloProcedimento = intervaloProcedimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CirurgiaDTO cirurgiaDTO = (CirurgiaDTO) o;
        if (cirurgiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cirurgiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CirurgiaDTO{" +
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
