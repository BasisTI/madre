package br.com.basis.madre.cadastros.service.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Perfil entity.
 */
public class PerfilDTO implements Serializable {

    private Long id;

    private Integer nmPerfil;

    private String dsPerfil;

    private Boolean stExcluido;

    private Boolean stAtivo;

    @NotNull
    private Integer idFuncionalidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNmPerfil() {
        return nmPerfil;
    }

    public void setNmPerfil(Integer nmPerfil) {
        this.nmPerfil = nmPerfil;
    }

    public String getDsPerfil() {
        return dsPerfil;
    }

    public void setDsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
    }

    public Boolean isStExcluido() {
        return stExcluido;
    }

    public void setStExcluido(Boolean stExcluido) {
        this.stExcluido = stExcluido;
    }

    public Boolean isStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(Boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

    public Integer getIdFuncionalidade() {
        return idFuncionalidade;
    }

    public void setIdFuncionalidade(Integer idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PerfilDTO perfilDTO = (PerfilDTO) o;
        if(perfilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilDTO{" +
            "id=" + getId() +
            ", nmPerfil=" + getNmPerfil() +
            ", dsPerfil='" + getDsPerfil() + "'" +
            ", stExcluido='" + isStExcluido() + "'" +
            ", stAtivo='" + isStAtivo() + "'" +
            ", idFuncionalidade=" + getIdFuncionalidade() +
            "}";
    }
}
