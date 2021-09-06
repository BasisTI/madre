package br.com.basis.madre.seguranca.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.GrupoFuncional} entity.
 */
public class GrupoFuncionalDTO extends DominioComDescricao implements Serializable {

    private Long servidorId;

    public Long getServidorId() {
        return servidorId;
    }

    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoFuncionalDTO)) {
            return false;
        }

        return id != null && id.equals(((GrupoFuncionalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoFuncionalDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", descricao='" + getDescricao() + "'" +
            ", servidorId=" + getServidorId() +
            "}";
    }
}
