package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Material} entity.
 */
public class MaterialDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean coletavel;

    @NotNull
    private Boolean exigeInformacao;

    @NotNull
    private Boolean urina;

    
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

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

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

        return id != null && id.equals(((MaterialDTO) o).id);
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
