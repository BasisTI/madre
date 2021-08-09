package br.com.basis.madre.seguranca.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.UF} entity.
 */
public class UFDTO implements Serializable {
    
    private Long id;

    private String unidadeFederativa;

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
        if (!(o instanceof UFDTO)) {
            return false;
        }

        return id != null && id.equals(((UFDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UFDTO{" +
            "id=" + getId() +
            ", unidadeFederativa='" + getUnidadeFederativa() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
