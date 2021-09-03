package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.OcupacaoDeCargo} entity.
 */
public class OcupacaoDeCargoDTO extends DTOgenericoComDescricao implements Serializable {

    @NotNull
    private Boolean situacao;

    private Boolean informarCbo;

    private Boolean informarCns;

    private Long cargoId;

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Boolean isInformarCbo() {
        return informarCbo;
    }

    public void setInformarCbo(Boolean informarCbo) {
        this.informarCbo = informarCbo;
    }

    public Boolean isInformarCns() {
        return informarCns;
    }

    public void setInformarCns(Boolean informarCns) {
        this.informarCns = informarCns;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OcupacaoDeCargoDTO)) {
            return false;
        }

        return id != null && id.equals(((OcupacaoDeCargoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OcupacaoDeCargoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", situacao='" + isSituacao() + "'" +
            ", informarCbo='" + isInformarCbo() + "'" +
            ", informarCns='" + isInformarCns() + "'" +
            ", cargoId=" + getCargoId() +
            "}";
    }
}
