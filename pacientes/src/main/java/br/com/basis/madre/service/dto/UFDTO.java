package br.com.basis.madre.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.UF} entity.
 */
public class UFDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String unidadeFederativa;

    @NotNull
    private String sigla;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnidadeFederativa() {
        return unidadeFederativa;
    }

    public void setUnidadeFederativa(String unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UFDTO uFDTO = (UFDTO) o;
        if (uFDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uFDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UFDTO{" +
            "id=" + getId() +
            ", unidadeFederativa='" + getUnidadeFederativa() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
