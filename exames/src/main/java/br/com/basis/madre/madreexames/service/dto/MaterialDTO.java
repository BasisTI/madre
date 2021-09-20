package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Material} entity.
 */
public class MaterialDTO extends DominioAtivo implements Serializable {

    @NotNull
    private Boolean coletavel;

    @NotNull
    private Boolean exigeInformacao;

    @NotNull
    private Boolean urina;

    public Boolean isColetavel() {
        return coletavel;
    }

    public void setColetavel(Boolean coletavel) {
        this.coletavel = coletavel;
    }

    public Boolean isExigeInformacao() {
        return exigeInformacao;
    }

    public void setExigeInformacao(Boolean exigeInformacao) {
        this.exigeInformacao = exigeInformacao;
    }

    public Boolean isUrina() {
        return urina;
    }

    public void setUrina(Boolean urina) {
        this.urina = urina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialDTO)) {
            return false;
        }

        return getId() != null && getId().equals(((MaterialDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", coletavel='" + isColetavel() + "'" +
            ", exigeInformacao='" + isExigeInformacao() + "'" +
            ", urina='" + isUrina() + "'" +
            "}";
    }
}
