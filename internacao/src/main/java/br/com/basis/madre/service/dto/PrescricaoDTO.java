package br.com.basis.madre.service.dto;

import br.com.basis.madre.domain.enumeration.UnidadeTempo;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Prescricao} entity.
 */
public class PrescricaoDTO implements Serializable {

    private Long id;

    private Instant horarioValidade;

    private Integer tempoAdiantamento;

    private UnidadeTempo unidadeTempo;

    private Integer numeroVias;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHorarioValidade() {
        return horarioValidade;
    }

    public void setHorarioValidade(Instant horarioValidade) {
        this.horarioValidade = horarioValidade;
    }

    public Integer getTempoAdiantamento() {
        return tempoAdiantamento;
    }

    public void setTempoAdiantamento(Integer tempoAdiantamento) {
        this.tempoAdiantamento = tempoAdiantamento;
    }

    public UnidadeTempo getUnidadeTempo() {
        return unidadeTempo;
    }

    public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
    }

    public Integer getNumeroVias() {
        return numeroVias;
    }

    public void setNumeroVias(Integer numeroVias) {
        this.numeroVias = numeroVias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrescricaoDTO prescricaoDTO = (PrescricaoDTO) o;
        if (prescricaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prescricaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrescricaoDTO{" +
            "id=" + getId() +
            ", horarioValidade='" + getHorarioValidade() + "'" +
            ", tempoAdiantamento=" + getTempoAdiantamento() +
            ", unidadeTempo='" + getUnidadeTempo() + "'" +
            ", numeroVias=" + getNumeroVias() +
            "}";
    }
}
