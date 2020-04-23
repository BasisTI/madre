package br.com.basis.madre.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Responsavel} entity.
 */
public class ResponsavelDTO implements Serializable {
    
    private Long id;

    private String nomeDoResponsavel;


    private Long telefoneId;

    private Long grauDeParentescoId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoResponsavel() {
        return nomeDoResponsavel;
    }

    public void setNomeDoResponsavel(String nomeDoResponsavel) {
        this.nomeDoResponsavel = nomeDoResponsavel;
    }

    public Long getTelefoneId() {
        return telefoneId;
    }

    public void setTelefoneId(Long telefoneId) {
        this.telefoneId = telefoneId;
    }

    public Long getGrauDeParentescoId() {
        return grauDeParentescoId;
    }

    public void setGrauDeParentescoId(Long grauDeParentescoId) {
        this.grauDeParentescoId = grauDeParentescoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResponsavelDTO responsavelDTO = (ResponsavelDTO) o;
        if (responsavelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), responsavelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResponsavelDTO{" +
            "id=" + getId() +
            ", nomeDoResponsavel='" + getNomeDoResponsavel() + "'" +
            ", telefoneId=" + getTelefoneId() +
            ", grauDeParentescoId=" + getGrauDeParentescoId() +
            "}";
    }
}
