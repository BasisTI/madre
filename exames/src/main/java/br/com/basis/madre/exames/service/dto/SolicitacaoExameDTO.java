package br.com.basis.madre.exames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.exames.domain.SolicitacaoExame} entity.
 */
public class SolicitacaoExameDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String infoClinica;

    @NotNull
    private Boolean usoAntimicrobianos24h;

    @NotNull
    private Boolean pedidoPrimeiroExame;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoClinica() {
        return infoClinica;
    }

    public void setInfoClinica(String infoClinica) {
        this.infoClinica = infoClinica;
    }

    public Boolean isUsoAntimicrobianos24h() {
        return usoAntimicrobianos24h;
    }

    public void setUsoAntimicrobianos24h(Boolean usoAntimicrobianos24h) {
        this.usoAntimicrobianos24h = usoAntimicrobianos24h;
    }

    public Boolean isPedidoPrimeiroExame() {
        return pedidoPrimeiroExame;
    }

    public void setPedidoPrimeiroExame(Boolean pedidoPrimeiroExame) {
        this.pedidoPrimeiroExame = pedidoPrimeiroExame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitacaoExameDTO)) {
            return false;
        }

        return id != null && id.equals(((SolicitacaoExameDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitacaoExameDTO{" +
            "id=" + getId() +
            ", infoClinica='" + getInfoClinica() + "'" +
            ", usoAntimicrobianos24h='" + isUsoAntimicrobianos24h() + "'" +
            ", pedidoPrimeiroExame='" + isPedidoPrimeiroExame() + "'" +
            "}";
    }
}
