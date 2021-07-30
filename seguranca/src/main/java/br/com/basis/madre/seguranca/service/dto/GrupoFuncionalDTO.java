package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.GrupoFuncional} entity.
 */
public class GrupoFuncionalDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer codigo;

    @NotNull
    private String descricao;


    private Long servidorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

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
