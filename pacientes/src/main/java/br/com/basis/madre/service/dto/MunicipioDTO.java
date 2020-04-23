package br.com.basis.madre.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Municipio} entity.
 */
public class MunicipioDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private String nomeDoDistrito;

    @NotNull
    private String ibge;


    private Long ufId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeDoDistrito() {
        return nomeDoDistrito;
    }

    public void setNomeDoDistrito(String nomeDoDistrito) {
        this.nomeDoDistrito = nomeDoDistrito;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public Long getUfId() {
        return ufId;
    }

    public void setUfId(Long uFId) {
        this.ufId = uFId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MunicipioDTO municipioDTO = (MunicipioDTO) o;
        if (municipioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), municipioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MunicipioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeDoDistrito='" + getNomeDoDistrito() + "'" +
            ", ibge='" + getIbge() + "'" +
            ", ufId=" + getUfId() +
            "}";
    }
}
