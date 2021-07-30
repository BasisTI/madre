package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Municipio} entity.
 */
public class MunicipioDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private String nomeDoDistrito;

    @NotNull
    private String ibge;


    private Long ufId;

    private String ufUnidadeFederativa;
    
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

    public String getUfUnidadeFederativa() {
        return ufUnidadeFederativa;
    }

    public void setUfUnidadeFederativa(String uFUnidadeFederativa) {
        this.ufUnidadeFederativa = uFUnidadeFederativa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MunicipioDTO)) {
            return false;
        }

        return id != null && id.equals(((MunicipioDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MunicipioDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeDoDistrito='" + getNomeDoDistrito() + "'" +
            ", ibge='" + getIbge() + "'" +
            ", ufId=" + getUfId() +
            ", ufUnidadeFederativa='" + getUfUnidadeFederativa() + "'" +
            "}";
    }
}
