package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.ControleQualidade} entity.
 */
public class ControleQualidadeDTO extends DominioConvenioPlano implements Serializable {

    @NotNull
    private String material;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControleQualidadeDTO)) {
            return false;
        }

        return getId() != null && getId().equals(((ControleQualidadeDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControleQualidadeDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", material='" + getMaterial() + "'" +
            ", codigoConvenio='" + getCodigoConvenio() + "'" +
            ", codigoPlano='" + getCodigoPlano() + "'" +
            ", convenioPlano='" + getConvenioPlano() + "'" +
            "}";
    }
}
